package ibm.ra.integration;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
	 @Produces(MediaType.APPLICATION_JSON)
	 public Account getAccountByAccountNumber(@PathParam("accountNumber")String accountNumber) throws DALException {	
		logger.info("Get Account By AccountNumber:"+accountNumber);
		return accountDAO.getAccountByAccountNumber(accountNumber);
	}
	 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Retrieve all Accounts",responseContainer = "array", response = Account.class)
	public Collection<Account>  getAccounts() throws DALException{
		logger.warning((new Date()).toString()+" Get all Accounts");
 		return accountDAO.getAccounts();
	}
}
