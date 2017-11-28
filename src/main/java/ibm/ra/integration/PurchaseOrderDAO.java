package ibm.ra.integration;

import po.model.PurchaseOrder;

public interface PurchaseOrderDAO {

	PurchaseOrder createPurchaseOrder(PurchaseOrder poIn) throws DALException;
	String deletePO(long id) throws DALException;
}
