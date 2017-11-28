package ibm.ra.integration;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;


public class RAIntegrationApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(LoginResource.class);
		classes.add(CustomerResource.class);
		classes.add(PurchaseOrderResource.class);
		return classes;
	}

}
