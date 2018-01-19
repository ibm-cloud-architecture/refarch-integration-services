# Customer Database in DB2
This quick note to summarize what was done on the DB2 side to configure the DB and load the data.

## Server Configuration
We are using the DB2 setting as presented in [this note](https://github.com/ibm-cloud-architecture/refarch-integration-inventory-db2#db2-server-installation)

## Scripts
The `db-scripts` folder includes the DDL to create the tables, and the shell scripts `createDB.sh` should help to create the database instance and tables.

## Working from Eclipse during development
Using the Eclipse `Database Development Perspective` we can access to the database and can alter the schema or data. The data base connection is as below:

![](db-connection.png)
```
Database: CUSTDB
Host: server IP address
Port: 50000
```

If you want to execute SQL commands you can use the updateTable.sql scrapbook in Eclipse to execute them. The figure below illustrates the parameters to be set up and example of commands:

![](updateTable.png)

It is a very nice feature to fine tune the model and database, load sample data, or verify data loaded from other scripts.

## Automatic creation of the database schema
The JPA implementation we are using, supports the creation of the database schema dynamically from the JPA annotation. We added in the persistence.xml file the `openjpa.jdbc.SynchronizeMapping` to `buildSchema`.
```xml
<properties>
  <property name="javax.persistence.jdbc.url" value="jdbc:db2://..:50000/CUSTDB:retrieveMessagesFromServerOnGetMessage=true;"/>
  <property name="javax.persistence.jdbc.driver" value="com.ibm.db2.jcc.DB2Driver"/>
<property name="openjpa.Log" value="SQL=TRACE" />
<property name="openjpa.jdbc.SynchronizeMapping" value="buildSchema"/>    
```

so there is no need to run DDL scripts, on development and test servers. Running tests like the ones in `src/test/ibmc.caseserv.itests` folder will create the tables.

## Loading Data
Two Java classes are added to add products and two basic customers in the folder `src/test/ibmc.caseserv.itests`. To be executed in this order inside Eclipse as Junit tests:
* AddProductsToBackend: create basic products to be use as smartphone
* AddNeededCustomersToBackend: add Eddie and Bob for demonstration purpose.

We also added a shell script to load the first 10 records while your docker image runs locally and you have direct access to the DB2 server: the Liberty has JDBC connection to the DB2 server.
```
# start docker containers
> docker run -d -p 9080:9080 ibmcase/customerms
> ./load10Customers.sh
```

We can assess the customer loaded by using the URL: http://customer.green.case/caseserv/api/v1/customers/ when deployed to IBM Cloud private or http://localhost:9080/caseserv/api/v1/customers/ when running locally.
