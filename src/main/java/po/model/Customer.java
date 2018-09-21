package po.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity(name="Customer")
@Table(name="CUSTOMERS")
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer extends Party{
	@Column(nullable=false, length=50)
	protected String firstName;
	@Column(nullable=false, length=50)
	protected String lastName;
	@Column(nullable=false, length=150)
	protected String emailAddress;
	@Column(nullable=true, length=10)
	// the following attributes may be used for analytics
	protected String gender;
	protected double age;
	@Column(nullable=true, length=10)
	protected String type;
	@Column(nullable=true, length=20)
	protected String status;
	protected int children = 0;
	protected double estimatedIncome = 10000;
	@Column(nullable=true, length=8)
	protected String zipCode;
	@Column(nullable=true, length=10, name="CAROWNER")
	protected String carOwner;
	@Column(nullable=true, length=50)
	protected String profession;
	@Column(nullable=true, length=20, name="CHURNSTATUS")
	protected String churnStatus="NotEvaluated";
	@Column(nullable=true, length=3, name="CHURNCLASS")
	protected String churnClass="F";
	@Column(nullable=true,name="churnrisk")
	protected double churn;
	protected String maritalStatus;

	protected String mostDominantTone;
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="ACCOUNT_ACCOUNTNUMBER")
	protected Account account;
	// Customer owns 1 to many devices / products
	@OneToMany(mappedBy="customer",fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	protected List<ProductAssociation> ownedProducts;

	public Customer(){
	}

	public Customer(String n) {
		this.name=n;
	}

	public String toString(){
		return getId()+" "+getName()+" "+getEmailAddress();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		if (this.getType() == null || "PERSON".equals(this.getType())) {
			this.name=this.lastName;
		}
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public double getEstimatedIncome() {
		return estimatedIncome;
	}

	public void setEstimatedIncome(double estimatedIncome) {
		this.estimatedIncome = estimatedIncome;
	}

	public boolean isCarOwner() {
		return "Y".equals(getCarOwner().trim());
	}

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getChurnStatus() {
		return churnStatus;
	}

	public void setChurnStatus(String churn) {
		this.churnStatus = churn;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMostDominantTone() {
		return mostDominantTone;
	}

	public void setMostDominantTone(String mostDominantTone) {
		this.mostDominantTone = mostDominantTone;
	}

	public double getChurnRisk() {
		return churn;
	}

	public void setChurnRisk(double churnRisk) {
		this.churn = churnRisk;
	}

	public List<ProductAssociation> getOwnedProducts() {
		if (ownedProducts == null) ownedProducts = new ArrayList<ProductAssociation>();
		return ownedProducts;
	}

	public void setOwnedProducts(List<ProductAssociation> ownedProducts) {
		this.ownedProducts = ownedProducts;
	}

	public void addProduct(Product p,String pNumber){
		ProductAssociation pa = new ProductAssociation();
		pa.setCustomer(this);
		pa.setProduct(p);
		pa.setPhoneNumber(pNumber);
		pa.setCustomerId(this.getId());
		pa.setProductName(p.getName());
		if (!getOwnedProducts().contains(pa)) {
			getOwnedProducts().add(pa);
		}
		//p.getOwners().add(pa);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getChurn() {
		return churn;
	}

	public void setChurn(double churn) {
		this.churn = churn;
	}

	public String getChurnClass() {
		return churnClass;
	}

	public void setChurnClass(String churnClass) {
		this.churnClass = churnClass;
	}

}
