CREATE TABLE ACCOUNTS (
  accountNumber VARCHAR(50) NOT NULL, 
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


CREATE INDEX I_CCOUNTS_CUSTOMER 
    ON ACCOUNTS (CUSTOMER_ID);

 CREATE INDEX I_CUSTOMER_ACCOUNT 
    ON CUSTOMERS (ACCOUNT_ACCOUNTNUMBER);

CREATE TABLE CUSTOMERS 
(id INTEGER NOT NULL, 
 name VARCHAR(100) NOT NULL, 
 firstName VARCHAR(50) NOT NULL, 
 lastName VARCHAR(50) NOT NULL, 
 emailAddress VARCHAR(150), 
 status VARCHAR(20), 
 type VARCHAR(10), 
 age INTEGER, 
 carOwner SMALLINT, 
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
        
COMMIT;
