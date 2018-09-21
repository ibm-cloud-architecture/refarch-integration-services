package ibm.ra.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ibm.ra.customer.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import po.dto.model.CustomerAccount;
import po.model.Customer;


@Path("/customers")
@Api("Customer management micro service API")
public class CustomerResource {
	 Logger logger = Logger.getLogger(CustomerResource.class.getName());
	 CustomerService customerService;


	 public CustomerResource(){
		 customerService= new CustomerService();

	 }

	@GET
	@Path("/version")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value="Get version of the API", notes="")
	@ApiResponses({ @ApiResponse(code = 200, message = "version v0.0.9", response = String.class) })
	public Response getVersion(){
		return Response.ok().entity(new String("version v0.0.9")).build();
	}

	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Create a new customer")
	@ApiResponses({ @ApiResponse(code = 201, message = "Customer created", response = String.class) })
	public Response newCustomer(@ApiParam(required = true) CustomerAccount ca) throws DALException {
		logger.log(Level.INFO,ca.getLastName()+" received in customer resource");
		customerService.newCustomer(ca);
		return Response.status(Status.CREATED).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Retrieve all customers",responseContainer = "array", response = CustomerAccount.class)
	public Collection<CustomerAccount>  getCustomers() throws DALException{
		logger.warning((new Date()).toString()+" Get all Customers");
		Collection<CustomerAccount> cal= new ArrayList<CustomerAccount>();
		for (Customer c: customerService.getCustomers()) {
			cal.add(new CustomerAccount(c));
		}
 		return cal;
	}

	@GET
    @Path("/{id}")
	@ApiOperation(value = "Get customer and his/her account with ID")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses({ @ApiResponse(code = 200, message = "Customer retrieved", response = CustomerAccount.class),
	@ApiResponse(code = 404, message = "Customer not found") })
	public Response getCustomerById(@PathParam("id")String id) throws DALException{
		logger.warning((new Date()).toString()+" Get Customer "+id);
		Customer c = customerService.getCustomerById(Long.parseLong(id));
		if (c != null) {
			return Response.ok().entity(new CustomerAccount(c)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
    }

	@GET
	@Path(value="/email/{email}")
	@ApiOperation(value = "Get customer and his/her account using the customer's email")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses({ @ApiResponse(code = 200, message = "Customer retrieved", response = CustomerAccount.class),
	@ApiResponse(code = 404, message = "Customer not found") })
	public Response getCustomerByEmail(@PathParam("email")String email) throws DALException{
		logger.warning("Get customer:"+email);
		Customer c = customerService.getCustomerByEmail(email);
		if (c != null) {
			return Response.ok().entity(new CustomerAccount(c)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
    }


	@PUT
	@Path("/{id}")
	@ApiOperation(value = "Update customer with ID")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@ApiResponses({ @ApiResponse(code = 200, message = "Customer updated") })
	public Response updateCustomer(@ApiParam(required = true) CustomerAccount ca) throws DALException {
		customerService.updateCustomer(ca.toCustomer());
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Delete customer with ID")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiResponses({ @ApiResponse(code = 200, message = "Customer delete"),
		@ApiResponse(code = 404, message = "Customer not found") })
	public Response deleteProject(@PathParam("id")String sid) throws DALException {
		long id = Long.parseLong(sid);
		try {
			String status= customerService.deleteCustomer(id);
			if (CustomerService.SUCCESS.equals(status) ) {
				return Response.ok().build();
			}
		} catch(DALException de) {
			if (de.getFaultInfo().getCode().equals("")) {
				return Response.status(Status.NOT_FOUND).build();
			}
			throw new DALException("Server error","Error on delete customer");
		}
		return Response.serverError().build();
	}


}
