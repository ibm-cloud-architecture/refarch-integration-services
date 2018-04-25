package ibm.ra.integration.dao;

import ibm.ra.customer.DALException;
import po.model.PurchaseOrder;

public interface PurchaseOrderDAO {

	PurchaseOrder createPurchaseOrder(PurchaseOrder poIn) throws DALException;
	String deletePO(long id) throws DALException;
}
