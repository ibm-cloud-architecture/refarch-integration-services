package po.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
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
	protected String gender;
	
	protected double age;
	protected int children;
	protected double estimatedIncome;
	protected boolean carOwner;
	@Column(nullable=true, length=50)
	protected String profession;
	@OneToOne(cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
	protected Account account;
	
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
		return carOwner;
	}

	public void setCarOwner(boolean carOwner) {
		this.carOwner = carOwner;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
