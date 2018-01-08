package po.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="Account")
@Table(name="ACCOUNTS")
@NamedQuery(name="Account.findAll", query="SELECT i FROM Account i")
public class Account {	
	@Id
	@Column(nullable=false, length=20)
	protected String accountNumber;
	protected double balance;
	protected int dropped;
	protected double longDistance;
	@Column(nullable=true, length=30)
	protected String longDistanceBillType;
	protected double international;
	protected double local;

	@Column(nullable=true, length=10)
	protected String paymentMethod;
	@Column(nullable=true, length=30)
	protected String localBillType;
	@Column(nullable=true, length=30)
	protected String ratePlan;
	protected double usage;
	protected String deviceOwned;
	protected Date creationDate;
	protected Date updateDate;
	@OneToOne(cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
	protected Customer customer;
	
	public Account(){}


	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getLongDistance() {
		return longDistance;
	}

	public void setLongDistance(double longDistance) {
		this.longDistance = longDistance;
	}

	public String getLongDistanceBillType() {
		return longDistanceBillType;
	}

	public void setLongDistanceBillType(String longDistanceBillType) {
		this.longDistanceBillType = longDistanceBillType;
	}

	public double getInternational() {
		return international;
	}

	public void setInternational(double international) {
		this.international = international;
	}

	public double getLocal() {
		return local;
	}

	public void setLocal(double local) {
		this.local = local;
	}

	public int getDropped() {
		return dropped;
	}

	public void setDropped(int dropped) {
		this.dropped = dropped;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getLocalBillType() {
		return localBillType;
	}

	public void setLocalBillType(String localBillType) {
		this.localBillType = localBillType;
	}

	public String getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getUsage() {
		return usage;
	}

	public void setUsage(double usage) {
		this.usage = usage;
	}

	public String getDeviceOwned() {
		return deviceOwned;
	}

	public void setDeviceOwned(String deviceOwned) {
		this.deviceOwned = deviceOwned;
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
}
