package dao.jpa.ut;

import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ibm.ra.customer.DALException;
import ibm.ra.integration.dao.ProductDAO;
import ibm.ra.integration.dao.ProductDAOImpl;
import po.model.Product;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProductDAO extends BaseTest{
	static ProductDAO dao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao= new ProductDAOImpl();
	}
	
	@Test
	public void testCreateProduct() {
		Product p=ModelFactory.buildIpho();
		try {
			Product cOut = dao.saveProduct(p);
			Assert.assertNotNull(cOut);
			Assert.assertTrue(p.getName().equals(cOut.getName()));
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}
	
	@Test
	public void testGetProductByName() {
		Product p = ModelFactory.buildSam();
		try {
			dao.saveProduct(p);
			Product cOut = dao.getProductByName(p.getName());
			Assert.assertNotNull(cOut);
			Assert.assertTrue(p.getName().equals(cOut.getName()));
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}
	
	@Test
	public void testGetProducts() {
		try {
			Collection<Product> l = dao.getProducts();
			Assert.assertNotNull(l);
			Assert.assertTrue(l.size() > 1);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}
	
	@Test
	public void testGetProductsByCategory() {
		try {
			Collection<Product> l = dao.getProductsByCategory("smartphone");
			Assert.assertNotNull(l);
			Assert.assertTrue(l.size() > 1);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}
	
	@Test
	public void testGetProductsByEmptyCategoryShouldReturnException() {
		try {
			dao.getProductsByCategory("");
		} catch (DALException e) {
			Assert.assertTrue(e.getFaultInfo().getMessage().contains("Category name is empty"));
		}
	}
	
	@Test
	public void testSaveEmptyProductShouldReturnException() {
		Product p=ModelFactory.buildIpho();
		p.setName("");
		try {
			dao.saveProduct(p);
		} catch (DALException e) {
			Assert.assertTrue(e.getFaultInfo().getMessage().contains("Product name is empty"));
		}
	}
	
	@Test
	public void testUpdateProduct() {
		try {
			Product cOut = dao.getProductByName("sam");
			Assert.assertNotNull(cOut.getPrice() == 600);
			cOut.setPrice(550);
			dao.updateProduct(cOut);
			Product cOut2 = dao.getProductByName("sam");
			Assert.assertNotNull(cOut2.getPrice() == 550);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}
	
	
	@Test
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
