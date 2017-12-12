package ibm.ra.integration;

import po.model.Account;

public interface AccountDAO {

	Account getAccountByAccountNumber(String accountNumber) throws DALException;

}
