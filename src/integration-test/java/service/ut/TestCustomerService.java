package service.ut;

import static org.junit.Assert.fail;

import java.util.Collection;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import dao.jpa.ut.BaseTest;
import dao.jpa.ut.ModelFactory;
import ibm.ra.customer.CustomerResource;
import ibm.ra.customer.DALException;
import ibm.ra.customer.ProductResource;
import ibm.ra.customer.service.CustomerService;
import po.dto.model.CustomerAccount;
import po.dto.model.ProductDTO;
import po.model.Customer;

public class TestCustomerService extends BaseTest{

	@Test
	public void testCreateCustomerWithOneAccount() {
		CustomerService serv = new CustomerService();
		CustomerAccount ca = ModelFactory.createCustomerAccount();
		try {
			Customer c=serv.newCustomer(ca);
			Assert.assertTrue(c.getId() > 0);
		} catch (DALException e) {
			e.printStackTrace();
			org.junit.Assert.fail();
		}
	}
	
	// @Test
	public void testCreateCustomerWithProduct(){
			// products are in DB
			ProductDTO p= ProductDTO.toProductDTO(ModelFactory.buildIpho(),"65000000");
			
			ProductResource pres = new ProductResource();
			try {
				Response rep=pres.newProduct(p);
				Assert.assertNotNull(rep);
				Assert.assertTrue(rep.getStatus() == 201);
				p= ProductDTO.toProductDTO(ModelFactory.buildSam(),"65000020");
				rep=pres.newProduct(p);
				// two products in catalog
				Collection<ProductDTO> l =pres.getProducts();
				Assert.assertNotNull(l);
				Assert.assertTrue(l.size()==2);
			} catch (DALException e) {
				e.printStackTrace();
				fail("Exception in test");
			}

			// now a customer is added referencing the two products
			CustomerAccount c = ModelFactory.createCustomerAccount();


			try {
				ProductDTO ipho=pres.getProductByName("ipho");
				CustomerResource serv = new CustomerResource();
				
				c.getDevicesOwned().add(ipho);
				serv.newCustomer(c);
				Response rep  = serv.getCustomerByEmail(c.getEmailAddress());
				Assert.assertNotNull(rep);
				Assert.assertNotNull(rep.getEntity());

			} catch (DALException e) {
				e.printStackTrace();
				fail("Exception in test");
			}
			
		}
		

}
