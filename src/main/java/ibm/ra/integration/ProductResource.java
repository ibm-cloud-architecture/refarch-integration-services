package ibm.ra.integration;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import po.dto.model.CustomerAccount;
import po.model.Account;
import po.model.Customer;
import po.model.Product;

@Path("/products")
@Api("Product management API")
public class ProductResource {
	 Logger logger = Logger.getLogger(ProductResource.class.getName());
	 ProductDAO  pDAO;
	 
	 public ProductResource(){
		 pDAO = new ProductDAOImpl();
	 }
	
	 
	 @GET
	 @Path(value="/{name}")
	 @ApiOperation(value = "Get Product by name")
	 @Produces(MediaType.APPLICATION_JSON)
	 @ApiResponses({ @ApiResponse(code = 200, message = "Product retrieved", response = Product.class),
	 @ApiResponse(code = 404, message = "Product not found") })
	 public Product getProductByName(@PathParam("name")String name) throws DALException {	
		logger.info("Get Product By name :"+name);
		return pDAO.getProductByName(name);
	}
	 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Retrieve all Products",responseContainer = "array", response = Product.class)
	public Collection<Product>  getProducts() throws DALException{
		logger.warning((new Date()).toString()+" Get all Products");
 		return pDAO.getProducts();
	}
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Create a new product")
	@ApiResponses({ @ApiResponse(code = 201, message = "Product created", response = String.class) })
	public Response newProduct(@ApiParam(required = true) Product p) throws DALException {
		p=pDAO.saveProduct(p);
		return Response.status(Status.CREATED).entity("{\"name\":" + p.getName() + "}").build();
	}
}
