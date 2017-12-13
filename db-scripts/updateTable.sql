alter table accounts add column  updatedate timestamp

CREATE INDEX I_CCOUNTS_CUSTOMER 
    ON ACCOUNTS (CUSTOMER_ID) 

create table OPENJPA_SEQUENCE_TABLE (ID SMALLINT NOT NULL, SEQUENCE_VALUE BIGINT, PRIMARY KEY (ID)) 

alter table customers add column churn VARCHAR(20);