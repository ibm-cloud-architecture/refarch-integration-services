package integration.ut;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ibm.ra.integration.AccountResource;
import ibm.ra.integration.DALException;
import po.dto.model.CustomerAccount;
import po.model.Account;
import po.model.Customer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCustomerService extends BaseTest{

	private static long customerId=0;
	AccountResource as = new AccountResource();

	public static Customer buildCustomer(){
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
		System.out.println("Create customer");
		Customer c = buildCustomer();
		try {
			CustomerAccount cOut=serv.newCustomer(new CustomerAccount(c));
			Assert.assertNotNull(cOut);
			Assert.assertNotNull(cOut.getId());
			customerId=cOut.getId();

			Account aOut = as.getAccountByAccountNumber(cOut.getAccountNumber());
			Assert.assertNotNull(aOut);
			Assert.assertNotNull(aOut.getId());
			System.out.println("customer id:"+cOut.getId()+ " account id:"+aOut.getId());
		} catch (DALException e) {
			e.printStackTrace();
			fail("Persistence of customer failed");
		}
		System.out.println(" --> success");
	}
	
	@Test
	public void testGetCustomer(){
		System.out.println("Get customer");
		Customer c = null;
		try {
			if (customerId == 0) {
				c = buildCustomer();
				CustomerAccount cOut=serv.newCustomer(new CustomerAccount(c));
				customerId=cOut.getId();		
			} 
			System.out.println("Load customer with id:"+customerId);
			CustomerAccount ca=serv.getCustomerById(customerId);
			Assert.assertNotNull(ca);
			Assert.assertTrue("Paul".equals(ca.getFirstName()));
			System.out.println(ca.toString());
		} catch (DALException e) {
			e.printStackTrace();
			fail("Load customer failed");
		}
		System.out.println(" --> success");
	}
    
	@Test
	public void testGetCustomers(){
		System.out.println("Get customers");
		Customer c = buildCustomer();
		c.setLastName("Martin");
		try {
			CustomerAccount cOut=serv.newCustomer(new CustomerAccount(c));
			Collection<CustomerAccount> cl=serv.getCustomers();
			Assert.assertNotNull(cl);
			Assert.assertTrue(cl.size()>1);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Load customer failed");
		}
		System.out.println(" --> success");
	}
	
	@Test
	public void testUpdateCustomer(){
		System.out.println("Update customer "+customerId);
		CustomerAccount c = null;
		try {
			c=serv.getCustomerById(customerId);
			Assert.assertNotNull(c);
			c.setEstimatedIncome(78000);
			serv.updateCustomer(c);
			CustomerAccount c2=serv.getCustomerById(customerId);
			Assert.assertTrue("Paul".equals(c2.getFirstName()));
			Assert.assertTrue(78000 == c2.getEstimatedIncome());
			System.out.println(c.toString());
		} catch (DALException e) {
			e.printStackTrace();
			fail("Load customer failed");
		}
		System.out.println(" --> success");
	}
}
