package dao.jpa.ut;

import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ibm.ra.customer.DALException;
import ibm.ra.integration.dao.CustomerDAOImpl;
import ibm.ra.integration.dao.ProductDAO;
import ibm.ra.integration.dao.ProductDAOImpl;
import po.model.Customer;
import po.model.Product;

/**
 * Validate CRUD operations on the customer DAO
 * 
 * @author jeromeboyer
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCustomerPersistence extends BaseTest {
	protected static Long id;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new CustomerDAOImpl();
		// add one product to be used by the customer of this test
		ProductDAO pdao = new ProductDAOImpl();
		Product p = ModelFactory.buildIpho();
		p.setName("ipho8");
		pdao.saveProduct(p);
	}

	@Test
	public void createCustomerShouldGetAnId() {
		System.out.println("Create customer in DB");
		Customer c = ModelFactory.createCustomer();
		// c.addProduct(ModelFactory.buildIpho(), "p001");
		try {
			Customer cOut = dao.saveCustomer(c);
			Assert.assertNotNull(cOut);
			Assert.assertNotNull(cOut.getId());
			Assert.assertNotNull(cOut.getCreationDate());
			id = cOut.getId();
			Assert.assertTrue(c.getName().equals(cOut.getName()));
			System.out.println(cOut.getCreationDate().toString() + " " + id);

		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}

	@Test
	public void getCustomerByName() {
		Customer c = ModelFactory.createCustomer();
		System.out.println("getCustomerByName ...");
		try {
			Customer cOut = dao.getCustomerByName(c.getName());
			Assert.assertNotNull(cOut);
			Assert.assertTrue(c.getName().equals(cOut.getName()));
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}

	@Test
	public void getCustomerById() {
		System.out.println("getCustomerById ...");
		try {
			Customer cOut = dao.getCustomerById(1);
			Assert.assertNotNull(cOut);
			Assert.assertTrue(1 == cOut.getId());
			Assert.assertNotNull(cOut.getAccount());
			Assert.assertTrue("act001".equals(cOut.getAccount().getAccountNumber()));
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}

	@Test
	public void getCustomerByEmailUpdateIt() {
		System.out.println("getCustomerByEmail ...");
		try {
			Customer cOut = dao.getCustomerByEmail("bp@supersite.com");
			Assert.assertNotNull(cOut);
			Assert.assertNotNull(cOut.getAccount());
			Assert.assertTrue("act001".equals(cOut.getAccount().getAccountNumber()));
			cOut.setEmailAddress("bp2@superemail.com");
			Customer cout2= dao.updateCustomer(cOut);
			Assert.assertTrue("bp2@superemail.com".equals(cout2.getEmailAddress()));
		} catch(	DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}

	 @Test
	public void testxAddDelete() {
		System.out.println("Create 2nd customer in DB");
		Customer c = ModelFactory.createCustomer();
		c.setId(new Long(2));
		c.getAccount().setAccountNumber("act002");
		try {
			Customer cOut = dao.saveCustomer(c);
			Assert.assertNotNull(cOut);
			System.out.println("Customer has ID:" + cOut.getId());
			System.out.println("then delete it");
			dao.deleteCustomer(cOut.getId());
			Customer cOut3 = dao.getCustomerById(cOut.getId());
			Assert.assertNull(cOut3);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}
	}

	@Test
	public void testGetMoreCustomers() {
		System.out.println("Get all customers");
		Customer c = ModelFactory.createCustomer();
		c.setName("Test2");
		c.setId(new Long(3));
		c.getAccount().setAccountNumber("act003");
		try {
			Customer cOut = dao.saveCustomer(c);
			Assert.assertNotNull(cOut);

			c = ModelFactory.createCustomer();
			c.setName("Test3");
			c.setId(new Long(4));
			c.getAccount().setAccountNumber("act004");
			
			cOut = dao.saveCustomer(c);

			Collection<Customer> l = dao.getCustomers();
			Assert.assertTrue(l.size() > 1);
		} catch (DALException e) {
			e.printStackTrace();
			fail("Exception in test");
		}

	}

}
