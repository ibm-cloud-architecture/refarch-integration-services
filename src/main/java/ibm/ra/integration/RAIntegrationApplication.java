package ibm.ra.integration;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;


@ApplicationPath("/caseserv")
@SwaggerDefinition(tags= {@Tag(name = "Customer API",description=" JAXRS API for customer management micro service")})
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
	
	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<Object>();
		singletons.add(CustomerResource.class);
		singletons.add(PurchaseOrderResource.class);
		singletons.add(AccountResource.class);
		singletons.add(ProductResource.class);
		return singletons;
	}

}
