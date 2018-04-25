package ibm.ra.customer;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ibm.ra.integration.dao.PurchaseOrderDAO;
import po.model.PurchaseOrder;

@Path("/pos")
public class PurchaseOrderResource {

	private PurchaseOrderDAO dao;
	
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PurchaseOrder createPO(PurchaseOrder po) throws DALException {
		return dao.createPurchaseOrder(po);
	}
	
}
