CREATE TABLE ACCOUNTS (
  id INTEGER NOT NULL, 
  accountNumber VARCHAR(50), 
  dropped INTEGER, 
  international DECIMAL, 
  local DECIMAL(8,2), 
  balance DECIMAL(8,2),
  localBillType VARCHAR(10), 
  longDistance DECIMAL, 
  longDistanceBillType VARCHAR(50), 
  paymentMethod VARCHAR(50), 
  ratePlan VARCHAR(10), 
  CUSTOMER_ID BIGINT,
  PRIMARY KEY (id));
  




CREATE INDEX I_CCOUNTS_CUSTOMER 
    ON ACCOUNTS (CUSTOMER_ID);


CREATE TABLE CUSTOMERS 
(id INTEGER NOT NULL, 
 creationDate TIMESTAMP, 
 name VARCHAR(100) NOT NULL, 
 status VARCHAR(10), 
 type VARCHAR(10), 
 updateDate TIMESTAMP, 
 age INTEGER, 
 carOwner SMALLINT, 
 children INTEGER, 
 emailAddress VARCHAR(150) NOT NULL, 
 estimatedIncome DOUBLE, 
 firstName VARCHAR(50) NOT NULL, 
 gender VARCHAR(10), 
 lastName VARCHAR(50) NOT NULL, 
 profession VARCHAR(100), 
 ACCOUNT_ID BIGINT,
 PRIMARY KEY (id)) 
        
COMMIT;
