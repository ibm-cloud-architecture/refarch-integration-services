# Set of RESTful services to manage customer and purchase order
This project is part of the 'IBM Data Analytics Reference Architecture' solution, available at [https://github.com/ibm-cloud-architecture/refarch-analytics](https://github.com/ibm-cloud-architecture/refarch-analytics).

Updated 12/15/2017.

The goal of this project is to implement a set of RESTful services to manage customer and purchase order.

## Table of Contents
* [Code explanation](#code-explanation)
* [API definition](#api-definition)
* [Build and deploy](#build-and-deploy)
* [Install on ICP](#ibm-cloud-private-deployment)
* [TDD](#test-driven-development)


## Code Explanation
This micro services is using JAXRS to expose RESTful APIs. There are a lot of articles on how to develop a RESTful application using jaxrs, we will not rewrite everything, but we still want to preset the steps we followed, it can be used as a cheat sheet.
* Created a Customer resource class and add annotations to define the URL paths and the swagger documentation:
 ```java
  	@Path("/customers")
	@Api("Customer management micro service API")
	public class CustomerResource {
	   //...
	   @GET
		@Path(value="/{id}")
		@ApiOperation(value = "Get customer and his/her account with ID")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiResponses({ @ApiResponse(code = 200, message = "Customer retrieved", response = CustomerAccount.class),
			@ApiResponse(code = 404, message = "Customer not found") })
		public Response getCustomerById(@PathParam("id")long id) throws DALException{
			logger.warning((new Date()).toString()+" Get Customer "+id);
			Customer c = customerDAO.getCustomerById(id);
			if (c != null) {
				return Response.ok().entity(new CustomerAccount(c)).build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
    }
	}
  ```
 The app will be packaged and exposed with a web context set to `caseserv`. The web.xml map the URL: `/api` to the servlet managing the resources.  Therefore the customer resource will be at the url: `http://hostname:port/caseserv/api/customers`.
 The above annotations exposes the method getCustomerById to a HTTP GET on the above URL with the customer id as suffix. The chosen implementation is to use the Response object to control return code and payload. We could have returned the Customer data too.

 Also we are not exposing the Customer object as is: the Customer is a JPA entity mapped to a Table in the database, we want to build a view of the data needed by the consumers of the service. The CustomerAccount is a Data Transfer Object. It may not be the best view but we may refactor it later when requirements will add up.

 The entity data model to support is presented in the figure below. It is similar to the tables in backend database. The java classes have JPA annotations to control the persistence and management of the relationship, persistence strategy and loading strategy.

 ![](docs/DomainModel.png)

 The CustomerAccount data transfert object is a view of the persistence model. It does not present a best practice, but it is good enough from now.

 ![](docs/CustomerDTO.png)

* Develop the application definition with the class RAIntegrationApplication which declares the exposed resources:

 ```java
 	import javax.ws.rs.core.Application;
	import io.swagger.annotations.SwaggerDefinition;
	import io.swagger.annotations.Tag;

	@ApplicationPath("/caseserv")
	@SwaggerDefinition(tags= {@Tag(name = "Customer API",description=" JAXRS API for customer management micro service")})
	public class RAIntegrationApplication extends Application {
			@Override
			public Set<Class<?>> getClasses() {
				Set<Class<?>> classes = new HashSet<Class<?>>();
				classes.add(CustomerResource.class);
			}
			// ..
    }
  ```

* Specify the url mapping to the pre-defined servlet serving REST resources in the web.xml:
 ```xml
 <servlet>
   <description>
   Main servlet entry point for the REST resource/application</description>
   <servlet-name>JAX-RS Servlet</servlet-name>
   <servlet-class>com.ibm.websphere.jaxrs.server.IBMRestServlet</servlet-class>
   <init-param>
     <param-name>javax.ws.rs.Application</param-name>
     <param-value>ibm.ra.integration.RAIntegrationApplication</param-value>
   </init-param>
   <load-on-startup>1</load-on-startup>
   <enabled>true</enabled>
   <async-supported>false</async-supported>
 </servlet>
 <servlet-mapping>
   <servlet-name>JAX-RS Servlet</servlet-name>
   <url-pattern>/api/*</url-pattern>
 </servlet-mapping>
 ```
* Implement the different DAOs to access the database. We are using a pure Java Persistence API implementation. We defined a BaseDAO for generic persistence operations like save, update, delete... Each DAO supports a specific entity and its CRUD operations.
The following code example illustrates  typical JPA pattern of using predefined query defined at the entity level. n the customer class
```
@Entity(name="Customer")
@Table(name="CUSTOMERS")
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer extends Party{
```
In the DAO implementation for the get the list of customers method uses the JPA entity manager and creates an instance of the query:

  ```
  @Override
  public Collection<Customer> getCustomers() throws DALException {
    EntityManager em = getEntityManager();
    List<Customer> results = new ArrayList<Customer>();
    try{
      Query query =em.createNamedQuery("Customer.findAll");
      results = query.getResultList ();

    } finally {
      em.close();
    }
    return results;
  }
  ```
* Unit test the DAO
* In the resource delegate calls to the DAO. Implement any business logic in the service. We did not decouple the API class from the service where the business logic can be done and tested in isolation. It is recommended to do this refactoring in the future.


## API definition
With Liberty it is possible to visualize the API definition for a deployed JAXRS resource. The product documentation is here, but to summarize we did two things:
* define a yaml file for the swagger and save it to webapp/META-INF/stub folder.
* modify the server.xml to add api discovery feature.

Once the service is deployed using the URL http://localhost:9080/api/explorer/#/Customer_management_micro_service_API will display the API as you can see below:

![](docs/customer-api.png)

## Build and Deploy
The project was developed with [Eclipse Neon](http://www.eclipse.org/neon) with the following plugins added to the base eclipse:
* Websphere Developer Tool for Liberty: using the Marketplace and searching WebSphere developer, then use the Eclipse way to install stuff.
* Gradle eclipse plugin

Install gradle CLI on your computer so you can build, unit test and assemble war.  For that see the installation instructions at [gradle](http://gradle.org)

To build the code you can use maven `nvm install` or gradle: `./gradlew build`. It should compile, unit tests and build a war under build/libs.

Then use the `docker build -t ibmcase/customerms .` command to build a docker image including WebSphere liberty, the server configuration, and the war file deployed.

You can test locally using `docker run -p 9080:9080 ibmcase/customerms` and then points your webbrowser to `http://localhost:9080/caseserv/index.html`. If the front end page is loaded your configuration works!.

We also added some integration test under the package
## IBM Cloud Private deployment
We are following the same approach as the other micro service deployment, for example as describe in the Case web app or the Data Access layer project.
* dockerize the application with Liberty
* define helm charts with deployment configuration
* use `kubectl`  and `helm` CLI to deploy the helm chart and work on the deployed pod.

## Test Driven Development
To implement the DAO we start by specifying the DAO interface  and then implemented the unit tests or each method, before coding the JPA code. The junit tests are in the package `dao.jpa.ut`.

The test has a specific pesistence configuration that uses derby embedded so it is easy to delete the DB after the tests. The persistence.xml is under `src/test/resources/META-INF` folder.
```xml
<persistence-unit name="customer" transaction-type="RESOURCE_LOCAL">
 <provider>org.apache.openjpa.persistence.PersistenceProviderImpl</provider>
   <class>po.model.Party</class>
   <class>po.model.Customer</class>
   <class>po.model.Account</class>
 <properties>
 <property name="javax.persistence.jdbc.driver" value="org.apache.derby.jdbc.EmbeddedDriver"/>
```
The `BaseTest` has an AfterClass method to delete the database.

When running the test in Eclipse be sure to add to the vm arg of each test, the following `-javaagent:./lib/openjpa-all-2.4.2.jar` to get the entities JPA enhanced.
