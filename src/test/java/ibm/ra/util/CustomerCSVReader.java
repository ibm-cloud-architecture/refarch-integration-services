package ibm.ra.util;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import ibm.caseserv.itests.CustomerRestClient;
import po.dto.model.CustomerAccount;
import po.model.Account;
import po.model.Customer;

public class CustomerCSVReader {
    
	protected static CustomerRestClient client = new CustomerRestClient();
	
	public static void main(String... args) { 
		List<CustomerAccount> cl =null;
		CustomerCSVReader tool= new CustomerCSVReader();
		if (args.length >0) {
			cl = tool.readCustomersFromCSV(args[0]); 
		} else {
			cl = tool.readCustomersFromCSV("./dataset/customerMed.csv"); 
		}
		
		for (CustomerAccount c : cl) {
			System.out.println("Upload "+c.getName()+" age:"+c.getAge()+" account "+c.getAccountNumber());
			tool.uploadToDB2ViaREST(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public  void uploadToDB2ViaREST(CustomerAccount c){		
		try {
			client.executeCustomerPost("api/customers",c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public  List<CustomerAccount>  readCustomersFromCSV(String fileName){
    	List<CustomerAccount> cl = new ArrayList<CustomerAccount>();
    	Path pathToFile = Paths.get(fileName); 
    	// create an instance of BufferedReader 

    	try 
    	{ 
    		BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII);
    		// read the first line from the text file 
    		String line = br.readLine(); 
    		line = br.readLine(); 
    		// loop until all lines are read 
    		while (line != null) { 
    			// use string.split to load a string array with the values from 
    			// each line of the file, using a comma as the delimiter 
    			String[] attributes = line.split(","); 
    			CustomerAccount aRow = createCustomer(attributes); 
    			cl.add(aRow);
    			// read next line before looping // if end of file reached, line would be null 
    			line = br.readLine(); 
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return cl;
    }
    
    public  CustomerAccount createCustomer(String[] attributes){
    	CustomerAccount c = new CustomerAccount();
    	//c.setId(Long.decode(attributes[0]));
    	c.setName("NameOf_"+attributes[0]);
    	c.setLastName(c.getName());
    	c.setFirstName("firstName_"+attributes[0]);
    	c.setEmailAddress(c.getLastName()+"."+c.getFirstName()+"@ibm");
    	c.setType("Person");
    	c.setGender(attributes[1]);
    	c.setStatus(attributes[2]);
    	c.setChildren(Integer.parseInt(attributes[3]));
    	c.setEstimatedIncome(Double.parseDouble(attributes[4]));
    	if ("Y".equals(attributes[5])) {
    		c.setCarOwner(true);
    	} else {
    		c.setCarOwner(false);
    	}
    	c.setAge(Double.parseDouble(attributes[6]));
    	c.setAccountNumber("ACCT_"+c.getId());
    	c.setLongDistance(Double.parseDouble(attributes[7]));
    	c.setInternational(Double.parseDouble(attributes[8]));
    	c.setLocal(Double.parseDouble(attributes[9]));
        c.setDropped(Integer.parseInt(attributes[10]));
        c.setPaymentMethod(attributes[11]);
        c.setLocalBillType(attributes[12]);
        c.setLongDistanceBillType(attributes[13]);
        c.setUsage(Double.parseDouble(attributes[14]));
        c.setRatePlan(attributes[15]);
    	return c;
    }
}
