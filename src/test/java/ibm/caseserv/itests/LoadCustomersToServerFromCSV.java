package ibm.caseserv.itests;

import java.util.List;

import org.junit.Test;

import ibm.ra.customer.DALException;
import ibm.ra.util.CustomerCSVReader;
import ibm.ra.util.CustomerRestClient;
import po.dto.model.CustomerAccount;

public class LoadCustomersToServerFromCSV  {
		
	
	@Test
	public void testLoadCSV() throws DALException {
		CustomerCSVReader tool= new CustomerCSVReader();
		List<CustomerAccount> cl = tool.readCustomersFromCSV("./dataset/last_customer_churn.csv");
		
		CustomerRestClient client = new CustomerRestClient();
		for (CustomerAccount ca : cl) {
			System.out.println("Upload "+ca.getName()+" age:"+ca.getAge()+" account "+ca.getAccountNumber()+ " device "+ca.getDevicesOwned().get(0).getPhoneNumber());
			
			
			try {
				client.executeCustomerPost("/customers",ca);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
