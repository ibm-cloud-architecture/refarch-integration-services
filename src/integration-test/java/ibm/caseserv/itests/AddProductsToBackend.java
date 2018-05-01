package ibm.caseserv.itests;

import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.jpa.ut.ModelFactory;
import ibm.caseserv.itests.TestDeployedRestCustomerService.Rep;
import po.dto.model.ProductDTO;
import po.model.Product;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddProductsToBackend {
	static CustomerRestClient client;
	protected static Properties props = new Properties();
	protected long customerId=-1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		props.setProperty("customerms.host", "localhost");
		props.setProperty("protocol", "http");
		props.setProperty("port", "9080");
		props.setProperty("customerms.webcontext", "/caseserv");
		props.setProperty("customerms.baseapi", "/api/v1");
		client = new CustomerRestClient(props);
	}
	
	@Test
	public void addProduct() throws Exception {
		Product p = ModelFactory.buildIpho();
		client.executeProductPost("/products", ProductDTO.toProductDTO(p));
	}

	@Test
	public void addTwoProducts() throws Exception {
		Product p = ModelFactory.buildSam();
		client.executeProductPost("/products", ProductDTO.toProductDTO(p));
		p=ModelFactory.buildMoto();
		client.executeProductPost("/products", ProductDTO.toProductDTO(p));
	}
	
	@Test
	public void ensureProductsExist()throws Exception{
		System.out.println("Get all products in DB");
		String rep=client.executeGetMethodAsJson("/products",null);
		ProductDTO[] l=client.getParser().fromJson(rep,ProductDTO[].class);
		for (ProductDTO p : l){
			System.out.println(p.getProductName()+" "+p.getCategoryName()+" "+p.getPrice());
		}
	}
}
