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

public class CustomerCSVReader {
    
	protected static CustomerRestClient client = new CustomerRestClient();
	
	public static void main(String... args) { 
		List<CustomerAccount> cl =null;
		CustomerCSVReader tool= new CustomerCSVReader();
		if (args.length >0) {
			cl = tool.readCustomersFromCSV(args[0]); 
		} else {
			cl = tool.readCustomersFromCSV("./dataset/customer.csv"); 
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
    	c.setId(Long.decode(attributes[0]));
    	c.setName("NameOf_"+attributes[0]);
    	c.setLastName(c.getName());
    	c.setFirstName("firstName_"+attributes[0]);
    	c.setEmailAddress(c.getLastName()+"."+c.getFirstName()+"@supermail");
    	c.setType("Person");
    	c.setGender(attributes[1]);
    	c.setStatus(attributes[2]);
    	c.setChildren(Integer.parseInt(attributes[3]));
    	c.setEstimatedIncome(Double.parseDouble(attributes[4]));
    	c.setCarOwner(attributes[5]);
    	c.setAge(Double.parseDouble(attributes[6]));
    	c.setMaritalStatus(attributes[7]);
    	c.setZipcode(attributes[8]);
    	c.setAccountNumber("ACCT_"+c.getId());
    	c.setLongDistance(Double.parseDouble(attributes[9]));
    	c.setInternational(Double.parseDouble(attributes[10]));
    	c.setLocal(Double.parseDouble(attributes[11]));
        c.setDropped(Integer.parseInt(attributes[12]));
        c.setPaymentMethod(attributes[13]);
        c.setLocalBillType(attributes[14]);
        c.setLongDistanceBillType(attributes[15]);
        c.setUsage(Double.parseDouble(attributes[16]));
        c.setRatePlan(attributes[17]);
        c.setDeviceOwned(attributes[18]);
        c.setChurn(attributes[19]);
    	return c;
    }
}
