package ibm.ra.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import ibm.ra.integration.CustomerResource;
import ibm.ra.integration.DALException;
import po.model.Customer;

/**
 * Use JDBC directly to save to DB2 Warehouse on ICP
 * @author jeromeboyer
 *
 */
public class Db2WarehousePersister {

	protected final static CustomerRestClient client = new CustomerRestClient();
	private Gson parser;
	
	public Db2WarehousePersister(){
		JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
			  @Override
			  public Date deserialize(JsonElement json, Type typeOfT,
			       JsonDeserializationContext context) throws JsonParseException {
			    return json == null ? null : new Date(json.getAsLong());
			  }
			};
		parser = new GsonBuilder().registerTypeAdapter(Date.class, deser).create();
	}
	
	
	
	private Customer updateName(Customer c){
		String sid =c.getId().toString();
		System.out.println(sid+" "+c.getEmailAddress());
		c.setName(c.getName().replace("null", sid));
		c.setFirstName(c.getFirstName().replace("null",sid));
		c.setEmailAddress(c.getEmailAddress().replaceAll("null", sid));
		return c;
	}
	
	public void useLocalServiceToAccessDB2() throws DALException{
		 CustomerResource serv = new CustomerResource();
		 Collection<Customer> l=serv.getCustomers();
		 Iterator<Customer> i = l.iterator();
		 int count=0;
		 while (i.hasNext()) {
			 Customer c = i.next();
			 if (c.getName().contains("null")) {
				 c=updateName(c);
				 serv.updateCustomer(c);
			 }
			 count++;
			 System.out.println(count+" "+c.getId()+" "+c.getEmailAddress());
		 }
	}
	
	public static void main(String... args) { 
		Db2WarehousePersister tool = new Db2WarehousePersister();
		try {
			tool.useLocalServiceToAccessDB2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
