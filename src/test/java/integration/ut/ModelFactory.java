package integration.ut;


import po.model.Customer;
import po.model.Party;

public class ModelFactory {

	public static Party buildParty(String name, String atype,String fname,String em) {
		Customer recipient = new Customer(name);
		recipient.setEmailAddress(em);
		recipient.setFirstName(fname);
		return recipient;
	}
}
