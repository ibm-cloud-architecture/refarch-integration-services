package dao.jpa.ut;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ibm.ra.customer.DALException;
import ibm.ra.integration.dao.CustomerDAOImpl;
import ibm.ra.integration.dao.ProductDAO;
import ibm.ra.integration.dao.ProductDAOImpl;
import ibm.ra.util.CustomerCSVReader;
import po.dto.model.CustomerAccount;
import po.dto.model.ProductDTO;
import po.model.Customer;
import po.model.Product;

public class TestLoadingFromCSV extends BaseTest {
	public static ProductDAO daoP;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao= new CustomerDAOImpl();
		// need to prepopulate product table 
		daoP= new ProductDAOImpl();
		
		try {
			Product p=ModelFactory.buildIpho();
			daoP.saveProduct(p);
			p=ModelFactory.buildSam();
			daoP.saveProduct(p);
			p=ModelFactory.buildMoto();
			daoP.saveProduct(p);
		} catch (DALException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void testLoadCSV() throws DALException {
		CustomerCSVReader tool= new CustomerCSVReader();
		List<CustomerAccount> cl = tool.readCustomersFromCSV("./dataset/customer.csv");
		for (CustomerAccount ca : cl) {
			Customer c=ca.toCustomer();
			c.setOwnedProducts(null);
			ProductDTO pdo=ca.getDevicesOwned().get(0);
			System.out.println("Upload "+ca.getName()+" age:"+ca.getAge()+" account: "+ca.getAccountNumber()+ " device: "+pdo.getName());
			
			Product pho=daoP.getProductByName(pdo.getName());
			c.addProduct(pho,pdo.getPhoneNumber());
			dao.saveCustomer(c);
		}
		Customer cOut3= dao.getCustomerById(11);
		Assert.assertNotNull(cOut3);
		Assert.assertNotNull(cOut3.getAccount());
		Assert.assertTrue("sam".equals(cOut3.getOwnedProducts().get(0).getProduct().getName()));
	}

}
