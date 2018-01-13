package dao.jpa.ut;

import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ibm.ra.integration.CustomerDAO;
import ibm.ra.integration.CustomerDAOImpl;
import ibm.ra.integration.DALException;
import ibm.ra.integration.ProductDAO;
import ibm.ra.integration.ProductDAOImpl;
import po.model.Customer;
import po.model.Product;
import po.model.ProductAssociation;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProductDAO extends BaseTest{
	static ProductDAO dao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao= new ProductDAOImpl();
	}
	
	
	@Test
	public void testCreateCustomerWithProduct(){
		// products are in DB
		Product p=ModelFactory.buildIpho();
		try {
			Product cOut=dao.saveProduct(p);
			Assert.assertNotNull(cOut);
			Assert.assertTrue(p.getName().equals(cOut.getName()));
			p=ModelFactory.buildSam();
			dao.saveProduct(p);
			// two products in catalog
			Collection<Product> l =dao.getProducts();
			Assert.assertNotNull(l);
			Assert.assertTrue(l.size()==2);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}

		// now a customer is added referencing the two products
		Customer c = ModelFactory.createCustomer();

		CustomerDAO cdao = new CustomerDAOImpl();

		try {
			Product ipho=dao.getProductByName("ipho");
			c.addProduct(ipho,"650650650");
			Product sam=dao.getProductByName("sam");
			c.addProduct(sam,"650650651");
			cdao.saveCustomer(c);
			Customer cOut = cdao.getCustomerByEmail(c.getEmailAddress());
			Assert.assertNotNull(cOut);
			for (ProductAssociation pa:cOut.getOwnedProducts()){
				System.out.println(pa.getProductName());
			}
		} catch (DALException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//@Test
	public void testXDelete(){
		try {
			String cOut=dao.deleteProduct("sam");
			Assert.assertNotNull(cOut);
			Collection<Product> l =dao.getProducts();
			Assert.assertNotNull(l);
			Assert.assertTrue(l.size()==1);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}

}
