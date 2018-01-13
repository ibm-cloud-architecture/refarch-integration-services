package po.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="Product")
@Table(name="PRODUCTS")
@NamedQuery(name="Product.findAll", query="SELECT i FROM Product i")
public class Product {
	@Id
	@Column(nullable=false, length=20)
	protected String name;
	@Column(nullable=true, length=50)
	protected String packageName;
	@Column(nullable=true, length=50)
	protected String productCategory;
	protected double monthlyUsage;
	protected double downloadSpeed;
	protected double price;
	@OneToMany(mappedBy="product",cascade=CascadeType.REMOVE)
	protected List<ProductAssociation> owners;
	
	protected Date creationDate;
	protected Date updateDate;
	
	
	public Product() {
		super();
	}
	
	public Product(String packageName, String productCategory, double price) {
		super();
		this.packageName = packageName;
		this.productCategory = productCategory;
		this.price = price;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public double getMonthlyUsage() {
		return monthlyUsage;
	}

	public void setMonthlyUsage(double monthlyUsage) {
		this.monthlyUsage = monthlyUsage;
	}

	public double getDownloadSpeed() {
		return downloadSpeed;
	}

	public void setDownloadSpeed(double downloadSpeed) {
		this.downloadSpeed = downloadSpeed;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<ProductAssociation> getOwners() {
		if (owners == null) owners = new ArrayList<ProductAssociation>();
		return owners;
	}

	public void setOwners(List<ProductAssociation> owners) {
		this.owners = owners;
	}
	
}
