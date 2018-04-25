package ibm.ra.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ibm.ra.integration.dao.ProductDAO;
import ibm.ra.integration.dao.ProductDAOImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import po.dto.model.ProductDTO;
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
	 public ProductDTO getProductByName(@PathParam("name")String name) throws DALException {	
		logger.info("Get Product By name :"+name);
		Product p =pDAO.getProductByName(name);
		return ProductDTO.toProductDTO(p);
	}
	 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Retrieve all Products",responseContainer = "array", response = Product.class)
	public Collection<ProductDTO>  getProducts() throws DALException{
		logger.warning((new Date()).toString()+" Get all Products");
		Collection<ProductDTO> l = new ArrayList<ProductDTO>();
		Collection<Product> lo= pDAO.getProducts();
		for (Product p:lo){
			ProductDTO pdto=ProductDTO.toProductDTO(p);
			l.add(pdto);
		}
 		return l;
	}
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Create a new product")
	@ApiResponses({ @ApiResponse(code = 201, message = "Product created", response = String.class) })
	public Response newProduct(@ApiParam(required = true) ProductDTO p) throws DALException {
		
		Product po=pDAO.saveProduct(p.toProduct());
		return Response.status(Response.Status.CREATED).build();
	}
}
