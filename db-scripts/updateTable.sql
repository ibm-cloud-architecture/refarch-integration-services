alter table accounts add column  balance decimal(8,2)

CREATE INDEX I_CCOUNTS_CUSTOMER 
    ON ACCOUNTS (CUSTOMER_ID) 
