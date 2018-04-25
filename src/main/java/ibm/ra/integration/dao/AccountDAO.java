package ibm.ra.integration.dao;

import java.util.Collection;

import ibm.ra.customer.DALException;
import po.model.Account;

public interface AccountDAO {

	Account getAccountByAccountNumber(String accountNumber) throws DALException;

	Collection<Account> getAccounts() throws DALException;

}
