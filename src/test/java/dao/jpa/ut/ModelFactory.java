package dao.jpa.ut;


import po.dto.model.CustomerAccount;
import po.model.Customer;
import po.model.Party;

public class ModelFactory {

	public static Party buildParty(String name, String atype,String fname,String em) {
		Customer recipient = new Customer(name);
		recipient.setEmailAddress(em);
		recipient.setFirstName(fname);
		return recipient;
	}
	
	
	public static CustomerAccount buildCustomerAccount(){
		CustomerAccount c = new CustomerAccount();
		c.setFirstName("Paul");
		c.setLastName("LeBoulanger");
		c.setAge(40);
		c.setCarOwner(false);
		c.setChildren(2);
		c.setEmailAddress("bp@supersite.com");
		c.setEstimatedIncome(90000);
		c.setGender("M");
		c.setType("PERSON");
		
		c.setAccountNumber("act001");
		c.setDropped(0);
		c.setInternational(10);
		c.setLocal(200);
		c.setLongDistance(30);
		c.setPaymentMethod("CC");
		c.setRatePlan("A");
		return c;
	}
	
	public static Customer createCustomer(){
		// use the DTO transformation and one source of bean definition
		Customer c = CustomerAccount.toCustomer(ModelFactory.buildCustomerAccount());
		// add here some overwrite
		return c;
	}
}
