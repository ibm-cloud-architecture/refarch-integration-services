package dao.jpa.ut;

import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ibm.ra.integration.CustomerDAOImpl;
import ibm.ra.integration.DALException;
import po.model.Customer;

/**
 * Validate CRUD operations on the customer DAO
 * @author jeromeboyer
 *
 */
public class CustomerPersistence extends BaseTest{
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao= new CustomerDAOImpl();
	}


	@Test
	public void testCreateCustomer() {
		System.out.println("Create customer in DB");
		Customer c = ModelFactory.createCustomer();
		try {
			Customer cOut=dao.saveCustomer(c);
			Assert.assertNotNull(cOut);
			Assert.assertNotNull(cOut.getId());
			System.out.println("Customer has ID:"+cOut.getId());
			Assert.assertTrue(c.getName().equals(cOut.getName()));
			
			System.out.println("getCustomerByName ...");
			Customer cOut2= dao.getCustomerByName(c.getName());
			Assert.assertNotNull(cOut2);
			Assert.assertTrue(cOut2.getId().longValue() == cOut.getId().longValue());
			System.out.println("Customer has ID:"+cOut2.getId());
			
			System.out.println("getCustomerById ...");
			Customer cOut3= dao.getCustomerById(cOut.getId());
			Assert.assertNotNull(cOut3);
			System.out.println("Customer has ID:"+cOut3.getId());
			Assert.assertNotNull(cOut3.getAccount());
			Assert.assertTrue("act001".equals(cOut3.getAccount().getAccountNumber()));
			
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}
	
	@Test
	public void addDelete(){
		System.out.println("Create customer in DB");
		Customer c = ModelFactory.createCustomer();
		c.setId(new Long(2));
		c.getAccount().setAccountNumber("act002");
		try {
			Customer cOut=dao.saveCustomer(c);
			Assert.assertNotNull(cOut);
			System.out.println("Customer has ID:"+cOut.getId());
			System.out.println("then delete it");
			dao.deleteCustomer(cOut.getId());
			Customer cOut3= dao.getCustomerById(cOut.getId());
			Assert.assertNull(cOut3);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}	
	}
	
	@Test
	public void testGetMoreCustomers(){
		Customer c = ModelFactory.createCustomer();
		c.setName("Test2");
		c.setId(new Long(3));
		c.getAccount().setAccountNumber("act003");
		try {
			Customer cOut=dao.saveCustomer(c);
			Assert.assertNotNull(cOut);
			c = ModelFactory.createCustomer();
			c.setName("Test3");
			c.setId(new Long(4));
			c.getAccount().setAccountNumber("act004");
			cOut=dao.saveCustomer(c);
			Collection<Customer>l=dao.getCustomers();
			Assert.assertTrue(l.size()>1);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
		
	}

}
