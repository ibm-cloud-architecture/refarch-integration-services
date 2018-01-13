alter table accounts drop column  deviceowned timestamp

CREATE INDEX I_CCOUNTS_CUSTOMER 
    ON ACCOUNTS (CUSTOMER_ID) 

create table OPENJPA_SEQUENCE_TABLE (ID SMALLINT NOT NULL, SEQUENCE_VALUE BIGINT, PRIMARY KEY (ID)) 

alter table customers add column MOSTDOMINANTTONE VARCHAR(20);
alter table customers alter column carOwner set data type VARCHAR(5)
drop table CUSTOMERS
drop table ACCOUNTS
drop table CUSTOMERS_PRODUCTS
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
    

INSERT INTO customers (id,name, status, gender,type,  age, 
        carOwner, children, emailAddress, estimatedIncome, firstName, 
        lastName, profession,maritalStatus,churn,mostDominantTone,zipcode)
VALUES ('1','Eddie TheBuilder','S','M','Person','45','N',	1,'eddie@email.com','140000','Eddie','TheBuilder','Engineer','Married','T','NotEvaluated','95500'),
VALUES ('3','Bob TheBuilder','S','M','Person','24.393333','N',	1,'bobbuilder@email.com','40000','Bob','TheBuilder','Engineer','Married','T','NotEvaluated','94000');

INSERT INTO ACCOUNTS ( accountNumber, 
  balance, dropped, international, local,localBillType, longDistance, longDistanceBillType, paymentMethod, 
  ratePlan, usage,   CUSTOMER_ID) 
 VALUES('ACT01','150','0','0','206','Budget','25','Standard','CC',3,'231',1),
 ('ACT03','300','0','0','120','Budget','25','Standard','CC',3,'231',3)
 
 CREATE TABLE PRODUCTS (
  name VARCHAR(20) NOT NULL, 
  downloadSpeed INTEGER, 
  monthlyUsage INTEGER, 
  packageName VARCHAR(50), 
  price DOUBLE, 
  productCategory VARCHAR(50), 
  creationDate TIMESTAMP, 
  updateDate TIMESTAMP, 
  PRIMARY KEY (name)) 

  INSERT INTO PRODUCTS (name,productCategory,price) VALUES('ipho','smartphone',750)
  INSERT INTO PRODUCTS (name,productCategory,price) VALUES('sam','smartphone',700);
  INSERT INTO PRODUCTS (name,productCategory,price) VALUES('moto','smartphone',250)
  
  INSERT INTO CUSTOMERS_PRODUCTS(CUSTOMERID,productName,phoneNumber,CUSTOMER_ID,OWNEDPRODUCTS_NAME)
  VALUES(1,'ipho','4157890001',1,'ipho'),
  VALUES(1,'sam','4157890002',1,'sam'),
    INSERT INTO CUSTOMERS_PRODUCTS(CUSTOMERID,productName,phoneNumber,CUSTOMER_ID,OWNEDPRODUCTS_NAME)
  VALUES(3,'sam','4157890003',3,'sam');
  
  CREATE TABLE CUSTOMERS_PRODUCTS (CUSTOMERID BIGINT NOT NULL,
        productName VARCHAR(20) NOT NULL, 
        phoneNumber VARCHAR(20), 
        CUSTOMER_ID BIGINT,
        OWNEDPRODUCTS_NAME VARCHAR(20),
        PRIMARY KEY (CUSTOMERID, productName)) 
