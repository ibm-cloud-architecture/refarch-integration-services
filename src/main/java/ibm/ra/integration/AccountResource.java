package ibm.ra.integration;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import po.dto.model.CustomerAccount;
import po.model.Account;

@Path("/accounts")
@Api("Account management micro service API")
public class AccountResource {
	 Logger logger = Logger.getLogger(AccountResource.class.getName());
	 AccountDAO  accountDAO;
	 
	 public AccountResource(){
		 accountDAO = new AccountDAOImpl();
	 }
	
	 
	 @GET
	 @Path(value="/{accountNumber}")
	 @ApiOperation(value = "Get account by account number")
	 @ApiResponses({ @ApiResponse(code = 200, message = "Customer retrieved", response = CustomerAccount.class),
	 @ApiResponse(code = 404, message = "Customer not found") })
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response getAccountByAccountNumber(@PathParam("accountNumber")String accountNumber) throws DALException {	
		logger.info("Get Account By AccountNumber:"+accountNumber);
		Account a=accountDAO.getAccountByAccountNumber(accountNumber);
		if ( a != null) {
			return Response.ok().entity(a).build();
		} else {
			 return Response.status(Status.NOT_FOUND).build();
		}
	}
	 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Retrieve all Accounts",responseContainer = "array", response = Account.class)
	public Collection<Account>  getAccounts() throws DALException{
		logger.warning((new Date()).toString()+" Get all Accounts");
 		return accountDAO.getAccounts();
	}
}
