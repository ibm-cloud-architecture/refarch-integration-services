package integration.ut;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ibm.ra.integration.DALException;
import po.model.Account;
import po.model.Customer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCustomerService extends BaseTest{

	private static long customerId=0;


	private Customer buildCustomer(){
		Customer c = new Customer();
		c.setFirstName("Paul");
		c.setLastName("LeBoulanger");
		c.setAge(40);
		c.setCarOwner(false);
		c.setChildren(2);
		c.setEmailAddress("bp@supersite.com");
		c.setCreationDate(new Date());
		c.setEstimatedIncome(90000);
		c.setGender("M");
		c.setType("PERSON");
		Account a = new Account();
		a.setAccountNumber("acct001");
		a.setDropped(0);
		a.setInternational(10);
		a.setLocal(200);
		a.setLongDistance(30);
		a.setPaymentMethod("CC");
		a.setRatePlan("A");
		c.setAccount(a);
		return c;
	}
	
	@Test
	public void testCreateCustomer()  {
		Customer c = buildCustomer();
		try {
			Customer cOut=serv.newCustomer(c);
			Assert.assertNotNull(cOut);
			Assert.assertNotNull(cOut.getId());
			customerId=cOut.getId();
		} catch (DALException e) {
			e.printStackTrace();
			fail("Persistence of customer failed");
		}
	}
	
	@Test
	public void testGetCustomer(){
		Customer c = null;
		try {
			if (customerId == 0) {
				c = buildCustomer();
				Customer cOut=serv.newCustomer(c);
				customerId=cOut.getId();		
			} 
			System.out.println("Load customer with id:"+customerId);
			c=serv.getCustomerById(customerId);
			Assert.assertNotNull(c);
			Assert.assertTrue("Paul".equals(c.getFirstName()));
			System.out.println(c.toString());
		} catch (DALException e) {
			e.printStackTrace();
			fail("Load customer failed");
		}
	}
    
	@Test
	public void testGetCustomers(){
		Customer c = buildCustomer();
		c.setLastName("Martin");
		try {
			Customer cOut=serv.newCustomer(c);
			System.out.println("Load customer with id:"+customerId);
			Collection<Customer> cl=serv.getCustomers();
			Assert.assertNotNull(cl);
			Assert.assertTrue(cl.size()>1);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Load customer failed");
		}
	}
	
	@Test
	public void testUpdateCustomer(){
		Customer c = null;
		try {
			c=serv.getCustomerById(customerId);
			Assert.assertNotNull(c);
			c.setEstimatedIncome(78000);
			serv.updateCustomer(c);
			Customer c2=serv.getCustomerById(customerId);
			Assert.assertTrue("Paul".equals(c2.getFirstName()));
			Assert.assertTrue(78000 == c2.getEstimatedIncome());
			System.out.println(c.toString());
		} catch (DALException e) {
			e.printStackTrace();
			fail("Load customer failed");
		}
	}
}
