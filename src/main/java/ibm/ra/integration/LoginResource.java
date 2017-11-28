package ibm.ra.integration;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("/login")
public class LoginResource {
	Logger logger = Logger.getLogger(LoginResource.class.getName());
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public void authenticate(String username,String pwd){
		
	}
}
