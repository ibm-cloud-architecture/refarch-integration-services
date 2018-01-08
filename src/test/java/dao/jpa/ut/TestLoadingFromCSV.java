package dao.jpa.ut;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ibm.ra.integration.CustomerDAOImpl;
import ibm.ra.integration.DALException;
import ibm.ra.util.CustomerCSVReader;
import po.dto.model.CustomerAccount;
import po.model.Customer;

public class TestLoadingFromCSV extends BaseTest {
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao= new CustomerDAOImpl();
	}


	@Test
	public void testLoadCSV() throws DALException {
		CustomerCSVReader tool= new CustomerCSVReader();
		List<CustomerAccount> cl = tool.readCustomersFromCSV("./dataset/customer.csv");
		for (CustomerAccount ca : cl) {
			System.out.println("Upload "+ca.getName()+" age:"+ca.getAge()+" account "+ca.getAccountNumber());
			dao.saveCustomer(ca.toCustomer(ca));
		}
		Customer cOut3= dao.getCustomerById(11);
		Assert.assertNotNull(cOut3);
		Assert.assertNotNull(cOut3.getAccount());
		Assert.assertTrue("sam".equals(cOut3.getAccount().getDeviceOwned()));
	}

}
