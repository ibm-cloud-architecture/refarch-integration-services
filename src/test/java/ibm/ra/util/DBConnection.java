package ibm.ra.util;

import java.io.InputStream;

public class DBConnection {
	
	public DBConnection(){
		InputStream fin=CustomerRestClient.class.getClassLoader().getResourceAsStream("env.properties");
	}
}
