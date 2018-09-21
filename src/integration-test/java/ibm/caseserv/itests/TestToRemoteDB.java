package ibm.caseserv.itests;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import ibm.ra.customer.DALException;
import ibm.ra.customer.service.CustomerService;
import ibm.ra.integration.dao.CustomerPersistenceManager;
import po.model.Customer;
import po.model.ProductAssociation;

public class TestToRemoteDB {
	static Properties jdbcProperties = new Properties();
	
	@BeforeClass
	public static void initPersistenceFactory() {
		
		jdbcProperties.setProperty("javax.persistence.jdbc.driver", "com.ibm.db2.jcc.DB2Driver");
		jdbcProperties.setProperty("javax.persistence.jdbc.user", "DB2INST1");
		jdbcProperties.setProperty("javax.persistence.jdbc.password", "Brown01");
		jdbcProperties.setProperty("javax.persistence.jdbc.url", "jdbc:db2://172.16.254.23:50000/CUSTDB:retrieveMessagesFromServerOnGetMessage=true;");
		jdbcProperties.setProperty("openjpa.Log", "SQL=TRACE");
		jdbcProperties.setProperty("javax.persistence.jdbc.Schema", "DB2INST1");
		jdbcProperties.setProperty("openjpa.DataCache", "true");
		jdbcProperties.setProperty("openjpa.RemoteCommitProvider", "sjvm");
		jdbcProperties.setProperty("openjpa.DynamicEnhancementAgent", "true");
		jdbcProperties.setProperty("openjpa.ConnectionFactoryProperties",
				"PrettyPrint=true, PrettyPrintLineLength=80, PrintParameters=True");
		EntityManagerFactory emfTest = Persistence.createEntityManagerFactory("customer", jdbcProperties);
		CustomerPersistenceManager.getInstance().setEntityManagerFactory(emfTest);
	}

	@Test
	public void testAccessCustomer() throws DALException {
		CustomerService serv = new CustomerService();
		Customer ca = serv.getCustomerById(1);
		for (ProductAssociation pa : ca.getOwnedProducts()) {
			System.out.println(pa.getProduct().getName());
		}
		
	}

}
