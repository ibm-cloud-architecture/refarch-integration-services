package ibm.ra.util;

import java.util.List;

import po.dto.model.CustomerAccount;
import po.model.Customer;

public class CleanCustomerData {

	public static void main(String[] args) {
		CustomerCSVReader tool= new CustomerCSVReader();
		List<CustomerAccount> cl = tool.readCustomersFromCSV("./dataset/customer_churn.csv");
		for (CustomerAccount ca : cl) {
			System.out.println(ca.toCsvString());
			
		}
		tool.saveCustomerCSV(cl);
	}

}
