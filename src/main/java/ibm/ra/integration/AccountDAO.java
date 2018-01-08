package ibm.ra.integration;

import java.util.Collection;

import po.model.Account;

public interface AccountDAO {

	Account getAccountByAccountNumber(String accountNumber) throws DALException;

	Collection<Account> getAccounts() throws DALException;

}
