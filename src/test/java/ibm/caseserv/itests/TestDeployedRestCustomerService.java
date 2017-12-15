package ibm.caseserv.itests;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.jpa.ut.ModelFactory;
import po.dto.model.CustomerAccount;

/** 
 * Test at the service level, running locally, could be docker run or wlp start
 * 
 * @author jeromeboyer
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDeployedRestCustomerService {
	static CustomerRestClient client;
	protected static Properties props = new Properties();
	protected long customerId=-1;
	
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

	public class Rep {
		public String id;
		public String getId() { return id;}
		public void setId(String s) { id=s;}
	}
	
	@Test
	public void testANewCustomer() throws Exception {
		System.out.println("Create customer in DB");
		CustomerAccount c = ModelFactory.createCustomerAccount();
		String rep=client.executeCustomerPost("api/customers", c);
		Rep repO = client.parser.fromJson(rep, Rep.class);
		Assert.assertTrue(rep.contains("{\"id"));
		Assert.assertTrue(repO.getId() !=null);
		customerId=Long.parseLong(repO.getId());
		System.out.println("delete lastly added customer "+customerId);
		rep=client.executeDeleteMethod("api/customers/"+customerId,null);
	}

	@Test
	public void testGetCustomer() throws Exception{
		System.out.println("Get existing customer in DB");
		String rep=client.executeGetMethodAsJson("api/customers/2",null);
		CustomerAccount ca = client.parser.fromJson(rep, CustomerAccount.class);
		Assert.assertTrue("LeBoulanger".equals(ca.getLastName()));
	}
	
	@Test
	public void testGetCustomers() throws Exception{
		System.out.println("Get all customers in DB");
		String rep=client.executeGetMethodAsJson("api/customers",null);
		CustomerAccount[] caa = client.parser.fromJson(rep, CustomerAccount[].class);
		for (CustomerAccount ca:caa) {
			System.out.println(ca.getId()+" "+ca.getName());
		}
	}
	
	
}
