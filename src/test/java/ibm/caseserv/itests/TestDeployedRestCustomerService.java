package ibm.caseserv.itests;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.jpa.ut.ModelFactory;
import ibm.ra.util.CustomerRestClient;
import po.dto.model.CustomerAccount;
import po.dto.model.ProductDTO;
import po.model.Product;

/** 
 * Test at the service level, running locally, could be docker run or a wlp start
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
		props.setProperty("customerms.host", "localhost");
		props.setProperty("protocol", "http");
		props.setProperty("port", "9080");
		props.setProperty("customerms.webcontext", "/caseserv");
		props.setProperty("customerms.baseapi", "/api/v1");
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
	public void assureProductsExist() throws Exception{
		System.out.println("Get all products in DB");
		String rep=client.executeGetMethodAsJson("/products",null);
		ProductDTO[] l=client.getParser().fromJson(rep,ProductDTO[].class);
		for (ProductDTO p : l){
			System.out.println(p.getProductName()+" "+p.getCategoryName()+" "+p.getPrice());
		}
	}
	
	
	
	@Test
	public void testANewCustomer() throws Exception {
		System.out.println("Create customer in DB");
		CustomerAccount c = ModelFactory.createCustomerAccount();
		c.setId(new Long(30000));
		ProductDTO pdto = new ProductDTO();
		pdto.setProductName("sam");
		pdto.setPhoneNumber("6507004500");
		c.getDevicesOwned().add(pdto);
		String rep=client.executeCustomerPost("/customers", c);
		Rep repO = client.getParser().fromJson(rep, Rep.class);
		Assert.assertTrue(rep.contains("{\"id"));
		Assert.assertTrue(repO.getId() !=null);
		customerId=Long.parseLong(repO.getId());
		
		System.out.println("Get customer by email");
		rep=client.executeGetMethodAsJson("/customers/email/bp@supersite.com",null);
		CustomerAccount ca = client.getParser().fromJson(rep, CustomerAccount.class);
		Assert.assertTrue("LeBoulanger".equals(ca.getLastName()));
		ProductDTO p = ca.getDevicesOwned().get(0);
		System.out.println(p.getPhoneNumber()+" "+p.getProductName());
		System.out.println(ca.getAccountNumber()+" "+ca.getBalance());
		
		System.out.println("delete lastly added customer "+customerId);
		rep=client.executeDeleteMethod("/customers/"+customerId,null);
	}

	
	@Test
	public void testGetCustomers() throws Exception{
		System.out.println("Get all customers in DB");
		String rep=client.executeGetMethodAsJson("/customers",null);
		CustomerAccount[] caa = client.getParser().fromJson(rep, CustomerAccount[].class);
		for (CustomerAccount ca:caa) {
			System.out.println(ca.getId()+" "+ca.getName()+" "+ca.getAccountNumber());
		}
		Assert.assertTrue(caa.length > 1);
	}
	
	
}
