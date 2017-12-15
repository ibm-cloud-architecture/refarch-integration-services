package ibm.ra.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import ibm.caseserv.itests.CustomerRestClient;

public class DBConnection {
	protected static Properties props = new Properties();
	
	public DBConnection(){
		InputStream fin=CustomerRestClient.class.getClassLoader().getResourceAsStream("env.properties");
		 try {
				props.load(fin);
			} catch (IOException e) {
				e.printStackTrace();
				props.setProperty("dbhost", "172.16.40.131");
				props.setProperty("dbport", "50000");
				props.setProperty("DB_DRIVER_CLASS", "com.ibm.db2.jcc.DB2Driver");
				props.setProperty("DB_URL","jdbc:db2://172.16.40.131:50000/BLUDB");
				props.setProperty("DB_USERNAME","bluadmin");
				props.setProperty("DB_PASSWORD","changemeplease");
			}
	}
	
	public Connection getConnection() {
		Connection con = null;
		try {
			// load the Driver Class
			Class.forName(props.getProperty("DB_DRIVER_CLASS"));

			// create the connection now
			con = DriverManager.getConnection(props.getProperty("DB_URL"),
					props.getProperty("DB_USERNAME"),
					props.getProperty("DB_PASSWORD"));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
