package dao.jpa.ut;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
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

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCreateCustomer() {
		Customer c = ModelFactory.createCustomer();
		try {
			Customer cOut=dao.saveCustomer(c);
			Assert.assertNotNull(cOut);
			Assert.assertNotNull(cOut.getId());
			Assert.assertTrue(c.getName().equals(cOut.getName()));
			
			Customer cOut2= dao.getCustomerByName(c.getName());
			Assert.assertNotNull(cOut2);
			
			Customer cOut3= dao.getCustomerById(cOut.getId());
			Assert.assertNotNull(cOut3);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}
	
	@Test
	public void addDelete(){
		Customer c = ModelFactory.createCustomer();
		try {
			Customer cOut=dao.saveCustomer(c);
			Assert.assertNotNull(cOut);
			dao.deleteCustomer(cOut.getId());
			Customer cOut3= dao.getCustomerById(cOut.getId());
			Assert.assertNull(cOut3);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}	
	}

}
