package ibm.ra.customer;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@SwaggerDefinition(tags= {@Tag(name = "Customer API",description=" JAX-RS API for customer management micro service")})
public class RAIntegrationApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(CustomerResource.class);
		classes.add(PurchaseOrderResource.class);
		classes.add(AccountResource.class);
		classes.add(ProductResource.class);
		return classes;
	}
	
}
