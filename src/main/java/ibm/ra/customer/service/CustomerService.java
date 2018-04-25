package ibm.ra.customer.service;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

import ibm.ra.customer.DALException;
import ibm.ra.integration.dao.AccountDAO;
import ibm.ra.integration.dao.AccountDAOImpl;
import ibm.ra.integration.dao.CustomerDAO;
import ibm.ra.integration.dao.CustomerDAOImpl;
import ibm.ra.integration.dao.ProductDAO;
import ibm.ra.integration.dao.ProductDAOImpl;
import po.dto.model.CustomerAccount;
import po.dto.model.ProductDTO;
import po.model.Customer;
import po.model.Product;

public class CustomerService {
	 public static final String SUCCESS = "SUCCESS";
	 public static final String FAILURE = "FAILURE";
	 public static final String NOT_FOUND = "NOT_FOUND";
	 
	 Logger logger = Logger.getLogger(CustomerService.class.getName());
	 CustomerDAO customerDAO;
	 AccountDAO  accountDAO;
	 ProductDAO productDAO;

	 public CustomerService(){
		 customerDAO = new CustomerDAOImpl();
		 accountDAO = new AccountDAOImpl();
		 productDAO = new ProductDAOImpl();
	 }
	
	public Customer newCustomer(CustomerAccount ca) throws DALException {
		    Customer c = ca.toCustomer();
			c.setCreationDate(new Date());
			c.setUpdateDate(c.getCreationDate());
			c.setStatus("New");
			
			for (ProductDTO pdto : ca.getDevicesOwned()) {
				Product pho=productDAO.getProductByName(pdto.getProductName());
				c.addProduct(pho,pdto.getPhoneNumber());
			}

			return customerDAO.saveCustomer(c);
	}

	public Collection<Customer> getCustomers() throws DALException {
		return customerDAO.getCustomers();
	}

	public Customer getCustomerById(long id) throws DALException {	
		return customerDAO.getCustomerById(id);
	}

	public Customer getCustomerByEmail(String email) throws DALException{
		return customerDAO.getCustomerByEmail(email);
	}

	public String updateCustomer(Customer customer) throws DALException {
		Customer c=customerDAO.getCustomerById(customer.getId());
		if (c == null) {
			return CustomerService.NOT_FOUND;
		} else {
			c.setUpdateDate(new Date());
			customerDAO.updateCustomer(c);
			return CustomerService.SUCCESS;
		}	
	}

	public String deleteCustomer(long id) throws DALException  {
			return customerDAO.deleteCustomer(id);
	}
			
}
