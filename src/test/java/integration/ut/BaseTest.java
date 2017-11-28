package integration.ut;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import ibm.ra.integration.CustomerResource;

public class BaseTest {
	static CustomerResource serv;
	// Delete the DB files
		static void deleteDir(File file) {
		    File[] contents = file.listFiles();
		    if (contents != null) {
		        for (File f : contents) {
		            deleteDir(f);
		        }
		    } 
		    file.delete();
		}
		
		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
			 serv = new CustomerResource();
		}

		@AfterClass
		public static void tearDownAfterClass() throws Exception {
			deleteDir(new File("./custdb"));
		}
}
