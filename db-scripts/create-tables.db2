CREATE TABLE ACCOUNTS (
  id INTEGER NOT NULL, 
  accountNumber VARCHAR(50), 
  dropped INTEGER, 
  international DECIMAL, 
  local DECIMAL, 
  localBillType VARCHAR(50), 
  longDistance DECIMAL, 
  longDistanceBillType VARCHAR(50), 
  paymentMethod VARCHAR(50), 
  ratePlan VARCHAR(10), 
  PRIMARY KEY (id)) 


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
 PRIMARY KEY (id)) 
        
COMMIT;
