package po.model;

import java.util.Date;
import java.util.List;

public class PurchaseOrder {
	String PONumber;
	Date orderDate;
	Date creationDate;
	Double poValue;
	List<Integer> items;
	Party buyer;
	
	public PurchaseOrder(){}

	public String getPONumber() {
		return PONumber;
	}

	public void setPONumber(String pONumber) {
		PONumber = pONumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Double getPoValue() {
		return poValue;
	}

	public void setPoValue(Double poValue) {
		this.poValue = poValue;
	}


	public Party getBuyer() {
		return buyer;
	}

	public void setBuyer(Party buyer) {
		this.buyer = buyer;
	}

	public List<Integer> getItems() {
		return items;
	}

	public void setItems(List<Integer> items) {
		this.items = items;
	}
	
}
