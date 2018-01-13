package po.dto.model;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import po.model.Account;
import po.model.Customer;
import po.model.ProductAssociation;



/**
 * Group customer and account as 1 to 1 relationship to one DTO. This could be changed in the future. The goal
 * is to flatten the model for DTO.
 * @author jeromeboyer
 *
 */

public class CustomerAccount {
	protected Long id=null;
	@ApiModelProperty(required = true)
	protected String name;
	protected String firstName;
	protected String lastName;
	protected String emailAddress;
	protected double age;
	protected String gender;	
	protected String type;
	@ApiModelProperty(required = false, example = "New")
	protected String status;
	protected int children;
	protected double estimatedIncome;
	protected String carOwner;
	protected String profession;
	@ApiModelProperty(required = false, example = "Will be defined by the scoring service")
	protected String churn;
	protected double churnRisk;
	protected String zipcode;
	protected String maritalStatus;
	protected String mostDominantTone;
	// Account account;
	protected String accountNumber;
	protected double longDistance;
	protected String longDistanceBillType;
	protected double international;
	protected double local;
	protected double balance;
	protected double usage;
	protected int dropped;
	protected String paymentMethod;
	protected String localBillType;
	protected String ratePlan;
	protected List<ProductDTO> devicesOwned;
	
	
	public CustomerAccount(Customer c){
		this.id=c.getId();
		this.name=c.getName();
		this.firstName = c.getFirstName();
		this.lastName=c.getLastName();
		this.emailAddress=c.getEmailAddress();
		this.age=c.getAge();
		this.gender=c.getGender();
		this.type=c.getType();
		this.status=c.getStatus();
		this.children=c.getChildren();
		this.estimatedIncome=c.getEstimatedIncome();
		this.profession=c.getProfession();
		this.carOwner=c.getCarOwner();
		this.churn=c.getChurn();
		this.churnRisk=c.getChurnRisk();
		this.maritalStatus=c.getMaritalStatus();
		this.zipcode=c.getZipCode();
		this.mostDominantTone=c.getMostDominantTone();
		if (c.getAccount() != null) {
			this.accountNumber=c.getAccount().getAccountNumber();
			this.longDistance=c.getAccount().getLongDistance();
			this.longDistanceBillType=c.getAccount().getLongDistanceBillType();
			this.international=c.getAccount().getInternational();
			this.local=c.getAccount().getLocal();
			this.localBillType=c.getAccount().getLocalBillType();
			this.balance=c.getAccount().getBalance();
			this.usage=c.getAccount().getUsage();
			this.dropped=c.getAccount().getDropped();
			this.paymentMethod=c.getAccount().getPaymentMethod();
			this.ratePlan=c.getAccount().getRatePlan();
		}
		if (c.getOwnedProducts() != null) {
			this.devicesOwned=new ArrayList<ProductDTO>();
			for (ProductAssociation pa : c.getOwnedProducts()) {
				ProductDTO pDTO = ProductDTO.toProductDTO(pa.getProduct(), pa.getPhoneNumber());
				this.getDevicesOwned().add(pDTO);
			}
		}
	}
	
	public  Customer toCustomer() {
		Customer c=new Customer();
		c.setId(this.getId());
		c.setName(this.getName());
		c.setFirstName(this.getFirstName());
		c.setLastName(this.getLastName());
		c.setEmailAddress(this.getEmailAddress());
		c.setAge(this.getAge());
		c.setGender(this.getGender());
		c.setType(this.getType());
		c.setStatus(this.getStatus());
		c.setChildren(this.getChildren());
		c.setEstimatedIncome(this.getEstimatedIncome());
		c.setCarOwner(this.isCarOwner());
		c.setProfession(this.getProfession());
		c.setChurn(this.getChurn());
		c.setChurnRisk(this.getChurnRisk());
		c.setZipCode(this.getZipcode());
		c.setMaritalStatus(this.getMaritalStatus());
		c.setMostDominantTone(this.getMostDominantTone());
		Account a = new Account();
		a.setAccountNumber(this.getAccountNumber());
		a.setLongDistance(this.getLongDistance());
		a.setLongDistanceBillType(this.getLongDistanceBillType());
		a.setInternational(this.getInternational());
		a.setLocal(this.getLocal());
		a.setLocalBillType(this.getLocalBillType());		
		a.setBalance(this.getBalance());
		a.setUsage(this.getUsage());
		a.setDropped(this.getDropped());
		a.setPaymentMethod(this.getPaymentMethod());
		a.setRatePlan(this.getRatePlan());
		c.setAccount(a);
		/* This logic should be in the service as the product has to be loaded from back end to make JPA happy
		for (ProductDTO p: this.getDevicesOwned()){
			c.addProduct(p.toProduct(), p.getPhoneNumber());
		}
		*/
		return c;
	}
	
	public CustomerAccount(String n) {
		this.name=n;
	}
	
	public String toString(){
		return getId()+" "+getName()+" "+getEmailAddress()+" "+getAccountNumber()+" "+getUsage();
	}

	
	public CustomerAccount() {
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

	public String isCarOwner() {
		return carOwner;
	}

	public String getCarOwner() {
		return carOwner;
	}

	
	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getUsage() {
		return usage;
	}

	public void setUsage(double usage) {
		this.usage = usage;
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

	public String getChurn() {
		return churn;
	}

	public void setChurn(String churn) {
		this.churn = churn;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMostDominantTone() {
		return mostDominantTone;
	}

	public void setMostDominantTone(String mostDominantTone) {
		this.mostDominantTone = mostDominantTone;
	}

	public double getChurnRisk() {
		return churnRisk;
	}

	public void setChurnRisk(double churnRisk) {
		this.churnRisk = churnRisk;
	}

	public List<ProductDTO> getDevicesOwned() {
		if (devicesOwned == null) devicesOwned = new ArrayList<ProductDTO>();
		return devicesOwned;
	}

	public void setDevicesOwned(List<ProductDTO> devicesOwned) {
		this.devicesOwned = devicesOwned;
	}


}
