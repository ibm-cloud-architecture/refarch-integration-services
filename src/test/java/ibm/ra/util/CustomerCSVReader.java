package ibm.ra.util;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import ibm.caseserv.itests.CustomerRestClient;
import po.model.Account;
import po.model.Customer;

public class CustomerCSVReader {
    
	protected static CustomerRestClient client = new CustomerRestClient();
	
	public static void main(String... args) { 
		List<Customer> cl =null;
		CustomerCSVReader tool= new CustomerCSVReader();
		if (args.length >0) {
			cl = tool.readCustomersFromCSV(args[0]); 
		} else {
			cl = tool.readCustomersFromCSV("./dataset/customerMed.csv"); 
		}
		
		for (Customer c : cl) {
			System.out.println("Upload "+c.getName()+" age:"+c.getAge()+" account "+c.getAccount().getAccountNumber());
			tool.uploadToDB2ViaREST(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public  void uploadToDB2ViaREST(Customer c){		
		try {
			client.executeCustomerPost("api/customers",c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public  List<Customer>  readCustomersFromCSV(String fileName){
    	List<Customer> cl = new ArrayList<Customer>();
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
    			Customer aRow = createCustomer(attributes); 
    			cl.add(aRow);
    			// read next line before looping // if end of file reached, line would be null 
    			line = br.readLine(); 
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return cl;
    }
    
    public  Customer createCustomer(String[] attributes){
    	Customer c = new Customer();
    	Account a = new Account();
    	c.setAccount(a);
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
    	a.setAccountNumber("ACCT_"+c.getId());
    	a.setLongDistance(Double.parseDouble(attributes[7]));
    	a.setInternational(Double.parseDouble(attributes[8]));
    	a.setLocal(Double.parseDouble(attributes[9]));
        a.setDropped(Integer.parseInt(attributes[10]));
        a.setPaymentMethod(attributes[11]);
        a.setLocalBillType(attributes[12]);
        a.setLongDistanceBillType(attributes[13]);
        a.setUsage(Double.parseDouble(attributes[14]));
        a.setRatePlan(attributes[15]);
    	return c;
    }
}
