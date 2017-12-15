package dao.jpa.ut;


import org.junit.Test;

import po.model.Party;
import po.model.PurchaseOrder;

public class TestPurchaseOrder extends BaseTest {


	@Test
	public void testCreatePartyPO() {
		Party recipient = ModelFactory.buildParty("aBuyer","Person","Paul","abuyer@us.ibm.com");
		PurchaseOrder po = new PurchaseOrder();
		
	}

}
