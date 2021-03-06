package po.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Party {
	
	@Id
	@Column( nullable = false)
	protected Long id=null;
	@Column(nullable=true, length=100)
	protected String name;
	@Column(nullable=true, length=10)
	protected String type;
	@Column(nullable=true, length=10)
	protected String status;
	protected String zipCode;
	protected Date creationDate;
	protected Date updateDate;
	
	
	public String toString(){
		return getId()+" "+getName()+" "+getType();
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


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipcode) {
		this.zipCode = zipcode;
	}

}
