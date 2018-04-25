package ibm.ra.integration.dao;

import java.util.Collection;

import ibm.ra.customer.DALException;
import po.model.Customer;

public interface CustomerDAO {

	public Customer saveCustomer(Customer c) throws DALException;
	
	Collection<Customer> getCustomers() throws DALException;
	
	Customer updateCustomer(Customer c) throws DALException;

	Customer getCustomerById(long id) throws DALException;
	
	Customer getCustomerByName(String name) throws DALException;
	
	String deleteCustomer(long id) throws DALException;

	public Customer getCustomerByEmail(String email) throws DALException ;

}
