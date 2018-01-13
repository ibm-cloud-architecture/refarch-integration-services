package dao.jpa.ut;


import po.dto.model.CustomerAccount;
import po.model.Customer;
import po.model.Party;
import po.model.Product;

public class ModelFactory {

	public static Party buildParty(String name, String atype,String fname,String em) {
		Customer recipient = new Customer(name);
		recipient.setEmailAddress(em);
		recipient.setFirstName(fname);
		return recipient;
	}
	
	
	public static CustomerAccount createCustomerAccount(){
		CustomerAccount c = new CustomerAccount();
		c.setId(new Long(1));
		c.setFirstName("Paul");
		c.setLastName("LeBoulanger");
		c.setName("Paul LeBoulanger");
		c.setAge(40);
		c.setCarOwner("F");
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
		CustomerAccount ca = ModelFactory.createCustomerAccount();
		Customer c = ca.toCustomer();
		// add here some overwrite
		return c;
	}
	
	public static Product buildIpho(){
		Product p = new Product();
		p.setName("ipho");
		p.setPackageName("ipho");
		p.setProductCategory("smartphone");
		p.setPrice(750);
		p.setDownloadSpeed(10000000);
		p.setMonthlyUsage(-1.0);
		return p;
	}
	
	public static Product buildSam(){ 
		Product p = new Product();
		p.setName("sam");
		p.setPackageName("sam");
		p.setProductCategory("smartphone");
		p.setPrice(600);
		p.setDownloadSpeed(10000000);
		p.setMonthlyUsage(-1.0);
		return p;
	}
	public static Product buildMoto(){ 
		Product p = new Product();
		p.setName("moto");
		p.setPackageName("moto");
		p.setProductCategory("smartphone");
		p.setPrice(250);
		p.setDownloadSpeed(5000000);
		p.setMonthlyUsage(-1.0);
		return p;
	}
}
