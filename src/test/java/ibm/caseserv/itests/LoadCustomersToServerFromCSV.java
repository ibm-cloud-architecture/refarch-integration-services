package ibm.caseserv.itests;

import java.util.List;

import org.junit.Test;

import ibm.ra.integration.DALException;
import ibm.ra.util.CustomerCSVReader;
import ibm.ra.util.CustomerRestClient;
import po.dto.model.CustomerAccount;

public class LoadCustomersToServerFromCSV  {
		
	
	@Test
	public void testLoadCSV() throws DALException {
		CustomerCSVReader tool= new CustomerCSVReader();
		List<CustomerAccount> cl = tool.readCustomersFromCSV("./dataset/customer.csv");
		
		CustomerRestClient client = new CustomerRestClient();
		for (CustomerAccount ca : cl) {
			System.out.println("Upload to DB "+ca.getName()+" age:"+ca.getAge()+" account "+ca.getAccountNumber());
			try {
				client.executeCustomerPost("/customers",ca);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
