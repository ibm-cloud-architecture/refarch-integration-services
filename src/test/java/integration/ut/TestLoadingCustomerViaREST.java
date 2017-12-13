package integration.ut;

import static org.junit.Assert.fail;

import java.lang.reflect.Type;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import ibm.ra.util.CustomerRestClient;
import po.model.Customer;

/*
 * You need to get the microservice deployed
 */
public class TestLoadingCustomerViaREST {

	protected static CustomerRestClient client;
	protected static Gson parser = new GsonBuilder()
			   .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();;
			   
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {		
	 client = new CustomerRestClient();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//@Test 
	public void tesAddCustomer(){
		 Customer c = TestCustomerService.buildCustomer();
		 String s= parser.toJson(c);
		 System.out.println(s);
        try {
			client.executePostMethodAsJson("api/customers", s);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}
	
	@Test
	public void testGetCustomers() {
		try {
			String rep=client.executeGetMethod("api/customers", null);
			JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
				  @Override
				  public Date deserialize(JsonElement json, Type typeOfT,
				       JsonDeserializationContext context) throws JsonParseException {
				    return json == null ? null : new Date(json.getAsLong());
				  }
				};
			Gson parser = new GsonBuilder().registerTypeAdapter(Date.class, deser).create();
			Customer[] l = parser.fromJson(rep, Customer[].class);
			for (Customer c: l){
				System.out.println(c.getName()+" "+c.getCreationDate().toString());
			}
		} catch (Exception e) {

			e.printStackTrace();
			fail("Exception in test");
		}
	}

}
