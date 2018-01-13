package po.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="CUSTOMERS_PRODUCTS")
@IdClass(ProductAssociationId.class)
public class ProductAssociation {
	@Id
	protected long   customerId;
	@Id
	@Column(nullable=false, length=20)
	protected String productName;
	@Column(length=20)
	protected String phoneNumber;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="CUSTOMER_ID", referencedColumnName="ID")
	protected Customer customer;
	@ManyToOne
	@PrimaryKeyJoinColumn(name="OWNEDPRODUCTS_NAME", referencedColumnName="NAME")
	protected Product product;
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
