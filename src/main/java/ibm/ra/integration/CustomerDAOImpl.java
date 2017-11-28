package ibm.ra.integration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import po.model.Customer;

public class CustomerDAOImpl extends BaseDao implements CustomerDAO {

	@Override
	public Customer saveCustomer(Customer c) throws DALException {
		return (Customer)save(c);
	}

	@Override
	public Collection<Customer> getCustomers() throws DALException {
		EntityManager em = getEntityManager();
		List<Customer> results = new ArrayList<Customer>();
		try{ 
			Query query =em.createNamedQuery("Customer.findAll");
			results = query.getResultList ();
		
		} finally {
			em.close();
		}
		return results;
	}

	@Override
	public Customer updateCustomer(Customer c) throws DALException {
		return (Customer)merge(c);
	}

	@Override
	public Customer getCustomerById(long id) throws DALException {
		if (id <= 0) {
			DALFault f = new DALFault("ERRDAO3005","Customer identifier negative or 0");
			throw new DALException("DAL exception input data", f);
		}
		EntityManager em = getEntityManager();
		Customer entity=em.find(Customer.class, id);
		em.close();
		return entity;
	}

	@Override
	public Customer getCustomerByName(String name) throws DALException {
		if (name == null || name.isEmpty()) {
			DALFault f = new DALFault("ERRDAO3012","Customer name is empty");
			throw new DALException("DAL exception input data", f);
		}
		EntityManager em = getEntityManager();
		List<Customer> l=null;
		try{ 
			Query query =em.createQuery("select p from Customer p where p.name = ?1",Customer.class);
			query.setParameter (1, name);
			l=query.getResultList();
		} finally {
			if (em != null) {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				em.close();
			}
		}
		if (l != null && ! l.isEmpty()) 
			return l.get(0);
		return null;
	}

	@Override
	public String deleteCustomer(long id) throws DALException {
		Customer entity=null;
		 EntityManager em =null;
		try {
			 em = begin();
			 entity=em.find(Customer.class, id);
			 if (entity != null) {
					logger.info("Removing entity "+entity.getId());
					 em.remove(entity);
					 em.getTransaction().commit();
				} 
		}catch (Exception e){
			e.printStackTrace();
			 throw new DALException("ERRDAO4002","Error on delete operation at tx level id="+id);
		} finally {
			if (em != null) {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				em.close();
			}
		}
		if (entity == null) {
			 throw new DALException("ERRDAO4003","Error on delete, entity not found for id="+id);
		}
		 return "Success"; 
		 
	}

}
