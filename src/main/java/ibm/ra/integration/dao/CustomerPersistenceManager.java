package ibm.ra.integration.dao;

import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CustomerPersistenceManager {
	 Logger logger = Logger.getLogger(CustomerPersistenceManager.class.getName());
	public String persistenceName="customer";
	
	// EntityManagerFactory instances are heavy weight objects. 
	// Each factory might maintain a metadata cache, object state cache, EntityManager pool, connection pool...
	protected EntityManagerFactory emf;
	private static final CustomerPersistenceManager singleton = new CustomerPersistenceManager();
	public static final boolean DEBUG = true;
	 
	public static CustomerPersistenceManager getInstance() {   
	    return singleton;
	}
	  
	private CustomerPersistenceManager() {
	}
	 
	
	public void setEntityManagerFactory(EntityManagerFactory entityFactory) {
		this.emf = entityFactory;
	}
	
	public EntityManagerFactory getEntityManagerFactory() {    
	    if (emf == null)
	      synchronized (singleton) {
	    	  createEntityManagerFactory();
		}
	    return emf;
	}
	  
	public void closeEntityManagerFactory() {	    
	    if (emf != null) {
	      emf.close();
	      emf = null;
	      if (DEBUG)
	          logger.info("@@@@ Persistence manager factory finished at " + new java.util.Date());
	    }
	}
	  
	protected void createEntityManagerFactory() {	    
	   emf = Persistence.createEntityManagerFactory(persistenceName);
	   if (DEBUG)
		   logger.info("@@@@ Persistence manager factory started at " + new java.util.Date());
	}
}
