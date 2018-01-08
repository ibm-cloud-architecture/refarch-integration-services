package ibm.ra.integration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import po.model.Account;
import po.model.Customer;

public class AccountDAOImpl extends BaseDao  implements AccountDAO {

	@Override
	public Account getAccountByAccountNumber(String accountNumber) throws DALException{
		if (accountNumber == null || accountNumber.isEmpty()) {
			DALFault f = new DALFault("ERRDAO3012","accountNumber is empty");
			throw new DALException("DAL exception input data", f);
		}
		EntityManager em = getEntityManager();
		List<Account> l=null;
		try{ 
			Query query =em.createQuery("select p from Account p where p.accountNumber = ?1",Account.class);
			query.setParameter (1, accountNumber);
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
	public Collection<Account> getAccounts() throws DALException {
		EntityManager em = getEntityManager();
		List<Account> results = new ArrayList<Account>();
		try{ 
			Query query =em.createNamedQuery("Account.findAll");
			results = query.getResultList ();
		
		} finally {
			em.close();
		}
		return results;
	}

}
