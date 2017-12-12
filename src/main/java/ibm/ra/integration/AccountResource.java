package ibm.ra.integration;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import po.model.Account;

@Path("/accounts")
public class AccountResource {
	 Logger logger = Logger.getLogger(AccountResource.class.getName());
	 AccountDAO  accountDAO;
	 
	 public AccountResource(){
		 accountDAO = new AccountDAOImpl();
	 }
	
	 
	 @GET
	 @Path(value="/ac/{accountNumber}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Account getAccountByAccountNumber(@PathParam("accountNumber")String accountNumber) throws DALException {	
		logger.info("Get Account By AccountNumber:"+accountNumber);
		return accountDAO.getAccountByAccountNumber(accountNumber);
	}
}
