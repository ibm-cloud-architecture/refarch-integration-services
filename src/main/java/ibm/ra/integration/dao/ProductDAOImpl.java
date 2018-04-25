package ibm.ra.integration.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ibm.ra.customer.DALException;
import ibm.ra.customer.DALFault;
import po.model.Product;

public class ProductDAOImpl extends BaseDao implements ProductDAO {

	@Override
	public Product saveProduct(Product p) throws DALException {
		if (p.getName() == null || p.getName().isEmpty()) {
			DALFault f = new DALFault("ERRDAO3010","Product name is empty");
			throw new DALException("DAL exception input data", f);
		}
		p.setCreationDate(new Date());
		p.setUpdateDate(p.getCreationDate());
		return (Product)save(p);
	}

	@Override
	public Collection<Product> getProducts() throws DALException {
		EntityManager em = getEntityManager();
		List<Product> results = new ArrayList<Product>();
		try{ 
			Query query =em.createNamedQuery("Product.findAll");
			results = query.getResultList ();
		
		} finally {
			em.close();
		}
		return results;
	}

	@Override
	public Collection<Product> getProductsByCategory(String categoryName) throws DALException {
		if (categoryName == null || categoryName.isEmpty()) {
			DALFault f = new DALFault("ERRDAO3012","Category name is empty");
			throw new DALException("DAL exception input data", f);
		}
		EntityManager em = getEntityManager();
		List<Product> l=null;
		try{ 
			Query query =em.createQuery("select p from Product p where p.productCategory = ?1",Product.class);
			query.setParameter (1,categoryName);
			l=query.getResultList();
		} finally {
			if (em != null) {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				em.close();
			}
		}
		return l;
	}

	@Override
	public Product updateProduct(Product p) throws DALException {
		p.setUpdateDate(new Date());
		return (Product)merge(p);
	}

	@Override
	public Product getProductByName(String name) throws DALException {
		if (name == null || name.isEmpty()) {
			DALFault f = new DALFault("ERRDAO3012","Product name is empty");
			throw new DALException("DAL exception input data", f);
		}
		EntityManager em = getEntityManager();
		List<Product> l=null;
		try{ 
			Query query =em.createQuery("select p from Product p where p.name = ?1",Product.class);
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
	public String deleteProduct(String name) throws DALException {
		Product entity=null;
		EntityManager em =null;
		try{ 
			em = begin();
			Query query =em.createQuery("select p from Product p where p.name = ?1",Product.class);
			query.setParameter (1, name);
			List<Product> l=query.getResultList();
			if (l != null && ! l.isEmpty()) {
				entity= l.get(0);
				logger.info("Removing entity "+name);
				em.remove(entity);
				em.getTransaction().commit();
			} else {
				throw new DALException("ERRDAO4003","Error on delete, entity not found for name="+name);
			}
		  
		}catch (Exception e){
			e.printStackTrace();
			 throw new DALException("ERRDAO4002","Error on delete operation at tx level name="+name);
		} finally {
			if (em != null) {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				em.close();
			}
		}
		 return "Success"; 
	}

}
