package ibm.caseserv.itests;

import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import po.dto.model.CustomerAccount;
import po.dto.model.ProductDTO;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddNeededCustomersToBackend {
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
	
	@Test
	public void addEddieCustomer()  {
		try {
			client.executeGetMethodAsJson("/customers/email/eddie@email.com",null);
		} catch (Exception e) {
			CustomerAccount c = new CustomerAccount();
			c.setId(new Long(1));
			c.setFirstName("Eddie");
			c.setLastName("TheBuilder");
			c.setName("Eddie TheBuilder");
			c.setAge(40);
			c.setCarOwner(true);
			c.setChildren(2);
			c.setMaritalStatus("Familly");
			c.setEmailAddress("eddie@email.com");
			c.setEstimatedIncome(140000);
			c.setGender("M");
			c.setType("PERSON");	
			c.setAccountNumber("ACT01");
			c.setDropped(0);
			c.setInternational(50);
			c.setLocal(200);
			c.setLongDistance(30);
			c.setPaymentMethod("CC");
			c.setRatePlan("3");
			ProductDTO pDTO = new ProductDTO();
			pDTO.setPhoneNumber("4157890001");
			pDTO.setName("ipho");
			c.getDevicesOwned().add(pDTO);
			pDTO = new ProductDTO();
			pDTO.setPhoneNumber("4157890002");
			pDTO.setName("sam");
			c.getDevicesOwned().add(pDTO);
			try {
				client.executeCustomerPost("/customers", c);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test
	public void addBobCustomer()  {
		try {
			client.executeGetMethodAsJson("/customers/email/bobbuilder@email.com",null);
		} catch (Exception e) {
			CustomerAccount c = new CustomerAccount();
			c.setId(new Long(3));
			c.setFirstName("Bob");
			c.setLastName("TheBuilder");
			c.setName("Bob TheBuilder");
			c.setAge(24);
			c.setCarOwner(false);
			c.setChildren(0);
			c.setMaritalStatus("Single");
			c.setEmailAddress("bobbuilder@email.com");
			c.setEstimatedIncome(30000);
			c.setGender("M");
			c.setType("PERSON");	
			c.setAccountNumber("ACT03");
			c.setDropped(0);
			c.setInternational(0);
			c.setLocal(300);
			c.setLongDistance(20);
			c.setPaymentMethod("CC");
			c.setRatePlan("1");
			ProductDTO pDTO = new ProductDTO();
			pDTO.setPhoneNumber("4157890003");
			pDTO.setName("moto");
			c.getDevicesOwned().add(pDTO);
			try {
				client.executeCustomerPost("/customers", c);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test
	public void testGetCustomer() throws Exception{
		System.out.println("Get existing customer in DB");
		String rep=client.executeGetMethodAsJson("/customers/1",null);
		CustomerAccount ca = client.getParser().fromJson(rep, CustomerAccount.class);
		Assert.assertTrue("Eddie".equals(ca.getFirstName()));
	}
	
	@Test
	public void testGetCustomerByEmail() throws Exception{
		System.out.println("Get existing customer in DB");
		String rep=client.executeGetMethodAsJson("/customers/email/bobbuilder@email.com",null);
		CustomerAccount ca = client.getParser().fromJson(rep, CustomerAccount.class);
		Assert.assertTrue("TheBuilder".equals(ca.getLastName()));
		ProductDTO p = ca.getDevicesOwned().get(0);
		System.out.println(p.getPhoneNumber()+" "+p.getName());
		System.out.println(ca.getAccountNumber()+" "+ca.getBalance());
	}
}
