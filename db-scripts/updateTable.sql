alter table accounts add column  updatedate timestamp

CREATE INDEX I_CCOUNTS_CUSTOMER 
    ON ACCOUNTS (CUSTOMER_ID) 

create table OPENJPA_SEQUENCE_TABLE (ID SMALLINT NOT NULL, SEQUENCE_VALUE BIGINT, PRIMARY KEY (ID)) 

alter table customers add column MOSTDOMINANTTONE VARCHAR(20);
alter table customers alter column carOwner set data type VARCHAR(5)
drop table CUSTOMERS

CREATE TABLE ACCOUNTS (
  accountNumber VARCHAR(10) NOT NULL, 
  balance DECIMAL(8,2),
  dropped INTEGER, 
  international DECIMAL, 
  local DECIMAL(8,2), 
  localBillType VARCHAR(10), 
  longDistance DECIMAL, 
  longDistanceBillType VARCHAR(50), 
  paymentMethod VARCHAR(50), 
  ratePlan VARCHAR(10), 
  usage DECIMAL(8,2),
  deviceOwned VARCHAR(20),
  creationDate TIMESTAMP, 
  updateDate TIMESTAMP, 
  CUSTOMER_ID BIGINT,
  PRIMARY KEY (accountNumber));
  
  
  CREATE TABLE CUSTOMERS 
(id INTEGER NOT NULL, 
 name VARCHAR(100) NOT NULL, 
 firstName VARCHAR(50) NOT NULL, 
 lastName VARCHAR(50) NOT NULL, 
 emailAddress VARCHAR(150), 
 status VARCHAR(20), 
 type VARCHAR(10), 
 age INTEGER, 
 carOwner VARCHAR(5), 
 children INTEGER, 
 estimatedIncome DOUBLE, 
 churn VARCHAR(20), 
 churnRisk DOUBLE,
 gender VARCHAR(10), 
 maritalStatus  VARCHAR(50), 
 mostDominantTone VARCHAR(50), 
 profession VARCHAR(100), 
  zipcode VARCHAR(20), 
 creationDate TIMESTAMP, 
 updateDate TIMESTAMP, 
 ACCOUNT_ACCOUNTNUMBER VARCHAR(20),
 PRIMARY KEY (id)) 
 
 
 CREATE INDEX I_CCOUNTS_CUSTOMER 
    ON ACCOUNTS (CUSTOMER_ID);
    
 CREATE INDEX I_CUSTOMER_ACCOUNT 
    ON CUSTOMERS (ACCOUNT_ACCOUNTNUMBER);
    
REORG TABLE customers

INSERT INTO customers (id,name, status, gender,type,  age, 
        carOwner, children, emailAddress, estimatedIncome, firstName, 
        lastName, profession,maritalStatus,churn,mostDominantTone,zipcode)
VALUES ('1','Eddie TheBuilder','S','M','Person','24.393333','N',	1,'eddie@email.com','40000','Eddie','TheBuilder','Engineer','Married','T','NotEvaluated','95500');

INSERT INTO ACCOUNTS ( accountNumber, 
  balance, dropped, international, local,localBillType, longDistance, longDistanceBillType, paymentMethod, 
  ratePlan, usage,  deviceOwned,   CUSTOMER_ID) 
 VALUES('ACT01','150','0','0','206','Budget','25','Standard','CC',3,'231','ipho',1)
