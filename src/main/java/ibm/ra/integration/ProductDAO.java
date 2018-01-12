package ibm.ra.integration;

import java.util.Collection;

import po.model.Product;

public interface ProductDAO {

	public Product saveProduct(Product c) throws DALException;
	
	Collection<Product> getProducts() throws DALException;
	
	Collection<Product> getProductsByCategory(String categoryName) throws DALException;
	
	Product updateProduct(Product c) throws DALException;

	
	Product getProductByName(String name) throws DALException;
	
	String deleteProduct(String name) throws DALException;


}
