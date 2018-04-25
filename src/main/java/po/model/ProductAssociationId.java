package po.model;

import java.io.Serializable;

public class ProductAssociationId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8171306857054901901L;
	protected Long   customerId;
	protected String productName;

	  public int hashCode() {
		
	    return (int)(customerId + productName.hashCode());
	  }

	  public boolean equals(Object object) {
	    if (object instanceof ProductAssociationId) {
	    	ProductAssociationId otherId = (ProductAssociationId) object;
	      return (otherId.customerId == this.customerId) && (otherId.productName != null && otherId.productName.equals( this.productName));
	    }
	    return false;
	  }

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
