CREATE TABLE ACCOUNTS (
  id INTEGER NOT NULL, 
  accountNumber VARCHAR(50), 
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
  PRIMARY KEY (id));
  

create table DB2INST1.OPENJPA_SEQUENCE_TABLE (ID SMALLINT NOT NULL, SEQUENCE_VALUE 
        BIGINT, PRIMARY KEY (ID)) 


CREATE INDEX I_CCOUNTS_CUSTOMER 
    ON ACCOUNTS (CUSTOMER_ID);


CREATE TABLE CUSTOMERS 
(id INTEGER NOT NULL, 
 name VARCHAR(100) NOT NULL, 
 firstName VARCHAR(50) NOT NULL, 
 lastName VARCHAR(50) NOT NULL, 
 emailAddress VARCHAR(150), 
 status VARCHAR(20), 
 type VARCHAR(10), 
 updateDate TIMESTAMP, 
 age INTEGER, 
 carOwner SMALLINT, 
 children INTEGER, 
 MOSTDOMINANTTONE VARCHAR(10),
 estimatedIncome DOUBLE, 
 gender VARCHAR(10), 
 profession VARCHAR(100), 
 creationDate TIMESTAMP, 
 ACCOUNT_ID BIGINT,
 PRIMARY KEY (id)) 
        
COMMIT;
