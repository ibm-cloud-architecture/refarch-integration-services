package ibm.caseserv.itests;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.jpa.ut.ModelFactory;
import po.dto.model.CustomerAccount;

public class TestDeployedRestCustomerService {
	static CustomerRestClient client;
	protected static Properties props = new Properties();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		props.setProperty("customerjaxrs", "localhost");
		props.setProperty("protocol", "http");
		props.setProperty("port", "9080");
		props.setProperty("webcontext", "caseserv");
		client = new CustomerRestClient(props);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//@Test
	public void testNewCustomer() throws Exception {
		System.out.println("Create customer in DB");
		CustomerAccount c = ModelFactory.createCustomerAccount();
		String rep=client.executeCustomerPost("api/customers", c);
		Assert.assertTrue(rep.contains("{\"id"));
		//fail("Not yet implemented");
	}

	@Test
	public void testGetCustomer() throws Exception{
		System.out.println("Get existing customer in DB");
		String rep=client.executeGetMethodAsJson("api/customers/2",null);
		CustomerAccount ca = client.parser.fromJson(rep, CustomerAccount.class);
		Assert.assertTrue("LeBoulanger".equals(ca.getLastName()));
	}
	
	//@Test
	public void testGetCustomers() throws Exception{
		System.out.println("Get all customers in DB");
		String rep=client.executeGetMethodAsJson("api/customers",null);
		CustomerAccount[] caa = client.parser.fromJson(rep, CustomerAccount[].class);
		for (CustomerAccount ca:caa) {
			System.out.println(ca.getId()+" "+ca.getName());
		}
		//Assert.assertTrue("LeBoulanger".equals(ca.getLastName()));
	}
	
	@Test
	public void testDelete() throws Exception{
		System.out.println("delete lastly added customer");
		String rep=client.executeDeleteMethod("api/customers/6501",null);
		
	}
}
