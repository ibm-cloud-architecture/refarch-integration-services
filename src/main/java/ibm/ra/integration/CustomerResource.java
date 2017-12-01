package ibm.ra.integration;

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

import po.model.Customer;

@Path("/customers")
public class CustomerResource {
	 Logger logger = Logger.getLogger(CustomerResource.class.getName());
	 CustomerDAO dao;
	 
	 public CustomerResource(){
		 dao= new CustomerDAOImpl();
	 }
	 
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Customer newCustomer(Customer p) throws DALException {
		logger.log(Level.WARNING,p.getLastName()+" received in customer resource");
		//p.setCreationDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		p.setCreationDate(new Date());
		p.setStatus("New");
		p=dao.saveCustomer(p);
		return p;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer>  getCustomers() throws DALException{
		logger.warning((new Date()).toString()+" Get all Customers");
		return dao.getCustomers();
	}
	
	@GET
	@Path(value="/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomerById(@PathParam("id")long id) throws DALException{
		logger.warning((new Date()).toString()+" Get Customer "+id);
		return dao.getCustomerById(id);
    }
	
	@GET
	@Path(value="/name/{pname}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomerByName(@PathParam("pname")String pname) throws DALException{
		logger.warning("Get customer:"+pname);
		return dao.getCustomerByName(pname);
    }

	
	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Customer updateCustomer(Customer c) throws DALException {
		return dao.updateCustomer(c);	
	}
	
	@DELETE
	@Path(value="/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProject(@PathParam("id")long id) throws DALException {
		Customer p = dao.getCustomerById(id);
		logger.log(Level.WARNING,p.getId()+" customer:"+p.getName()+" to delete");
		return "\""+dao.deleteCustomer(id)+"\"";
	}
	
}
