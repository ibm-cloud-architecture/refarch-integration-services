package ibm.ra.integration;

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

import po.dto.model.CustomerAccount;
import po.model.Customer;

@Path("/customers")
public class CustomerResource {
	 Logger logger = Logger.getLogger(CustomerResource.class.getName());
	 CustomerDAO customerDAO;
	 AccountDAO  accountDAO;
	 
	 public CustomerResource(){
		 customerDAO= new CustomerDAOImpl();
		 accountDAO = new AccountDAOImpl();
	 }
	 
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerAccount newCustomer(CustomerAccount ca) throws DALException {
		logger.log(Level.INFO,ca.getLastName()+" received in customer resource");
		//p.setCreationDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		Customer c = CustomerAccount.toCustomer(ca);
		c.setCreationDate(new Date());
		c.setStatus("New");
		c=customerDAO.saveCustomer(c);
		return new CustomerAccount(c);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<CustomerAccount>  getCustomers() throws DALException{
		logger.warning((new Date()).toString()+" Get all Customers");
		Collection<CustomerAccount> cal= new ArrayList<CustomerAccount>();

		for (Customer c: customerDAO.getCustomers()) {
			cal.add(new CustomerAccount(c));
		}
 		return cal;
	}
	
	@GET
	@Path(value="/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerAccount getCustomerById(@PathParam("id")long id) throws DALException{
		logger.warning((new Date()).toString()+" Get Customer "+id);
		Customer c = customerDAO.getCustomerById(id);
		if (c != null) return new CustomerAccount(c);
		return new CustomerAccount();
    }
	
	@GET
	@Path(value="/name/{pname}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerAccount getCustomerByName(@PathParam("pname")String pname) throws DALException{
		logger.warning("Get customer:"+pname);
		Customer c = customerDAO.getCustomerByName(pname);
		if (c != null) return new CustomerAccount(c);
		return new CustomerAccount(); 
    }

	
	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerAccount updateCustomer(CustomerAccount ca) throws DALException {
		Customer c = CustomerAccount.toCustomer(ca);
		c.setUpdateDate(new Date());
		return new CustomerAccount(customerDAO.updateCustomer(c));	
	}
	
	@DELETE
	@Path(value="/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProject(@PathParam("id")long id) throws DALException {
		Customer p = customerDAO.getCustomerById(id);
		logger.log(Level.WARNING,p.getId()+" customer:"+p.getName()+" to delete");
		return "\""+customerDAO.deleteCustomer(id)+"\"";
	}

	

	
}
