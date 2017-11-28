package ibm.ra.integration;

import java.util.Collection;

import po.model.Customer;

public interface CustomerDAO {

	public Customer saveCustomer(Customer c) throws DALException;
	
	Collection<Customer> getCustomers() throws DALException;
	
	Customer updateCustomer(Customer c) throws DALException;

	Customer getCustomerById(long id) throws DALException;
	
	Customer getCustomerByName(String name) throws DALException;
	
	String deleteCustomer(long id) throws DALException;

}
