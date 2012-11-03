SET MODE Oracle;

CREATE SCHEMA IF NOT EXISTS BST_ISE_SCH01;

CREATE TABLE bst_ise_sch01.addr
(
   addr_id      VARCHAR2 (100 ) NOT NULL,
   addr_ln_1    VARCHAR2 (250 ),
   addr_ln_2    VARCHAR2 (250 ),
   city         VARCHAR2 (100 ),
   state        VARCHAR2 (100 ),
   zipcode      VARCHAR2 (10 ),
   created_by   VARCHAR2 (20 ) NOT NULL,
   created_on   DATE NOT NULL,
   amended_by   VARCHAR2 (20 ),
   amended_on   DATE
);

ALTER TABLE bst_ise_sch01.addr
  ADD CONSTRAINT addr_pk
  PRIMARY KEY (addr_id);
  
CREATE TABLE bst_ise_sch01.bbym_acct
(
   dta_sharing_key   VARCHAR2 (100 ) NOT NULL,
   cust_frst_nm      VARCHAR2 (100 ),
   cust_last_nm      VARCHAR2 (100 ),
   cust_ph_nbr       VARCHAR2 (30 ),
   cust_email_id     VARCHAR2 (200 ),
   addr_id           VARCHAR2 (100 ),
   created_by        VARCHAR2 (20 ) NOT NULL,
   created_on        DATE NOT NULL,
   amended_by        VARCHAR2 (20 ),
   amended_on        DATE,
   bby_cust_id          VARCHAR2 (100 ),
   rwz_nbr				VARCHAR2 (20 )
);

ALTER TABLE bst_ise_sch01.bbym_acct
  ADD CONSTRAINT bbym_acct_pk
  PRIMARY KEY (dta_sharing_key);

CREATE TABLE bst_ise_sch01.carr_acct
(
   dta_sharing_key   VARCHAR2 (100 ) NOT NULL,
   acct_nbr          VARCHAR2 (60 ),
   covg_zip          VARCHAR2 (10 ),
   cust_frst_nm      VARCHAR2 (100 ),
   cust_last_nm      VARCHAR2 (100 ),
   cust_ph_nbr       VARCHAR2 (30 ),
   cust_email_id     VARCHAR2 (200 ),
   addr_id           VARCHAR2 (100 ),
   created_by        VARCHAR2 (20 ) NOT NULL,
   created_on        DATE NOT NULL,
   amended_by        VARCHAR2 (20 ),
   amended_on        DATE,
   carr_id			 VARCHAR2 (10 )
);

ALTER TABLE bst_ise_sch01.carr_acct
  ADD CONSTRAINT carr_acct_pk
  PRIMARY KEY (dta_sharing_key);
  
CREATE TABLE bst_ise_sch01.dta_xfer
(
   dta_sharing_key   VARCHAR2 (100 ) NOT NULL,
   stor_id           VARCHAR2 (10 ) NOT NULL,
   xfer_flg          NUMBER (1) DEFAULT 0 NOT NULL,
   src_sys           VARCHAR2 (60 ),
   created_by        VARCHAR2 (20 ) NOT NULL,
   created_on        DATE NOT NULL,
   amended_by        VARCHAR2 (20 ),
   amended_on        DATE,
   cap_trans_id		 VARCHAR2 (60 ),
   workflow_type     VARCHAR2(50)
);

ALTER TABLE bst_ise_sch01.dta_xfer
  ADD CONSTRAINT dta_xfer_pk
  PRIMARY KEY (dta_sharing_key);
  
CREATE TABLE bst_ise_sch01.rec_sht_summ
(
   dta_sharing_key     VARCHAR2 (100 ) NOT NULL,
   plan_info           VARCHAR2 (200 ),
   device_info         VARCHAR2 (200 ),
   gsp_plan_info       VARCHAR2 (200 ),
   buyback_plan_info   VARCHAR2 (200 ),
   created_by          VARCHAR2 (20 ) NOT NULL,
   created_on          DATE NOT NULL,
   amended_by          VARCHAR2 (20 ),
   amended_on          DATE
);

CREATE TABLE bst_ise_sch01.gsp_plan
(
   id                   VARCHAR2 (100 ) NOT NULL,
   dta_sharing_key      VARCHAR2 (100 ) NOT NULL,
   bill_typ             VARCHAR2 (20 ),
   bsns_dt              DATE,
   cncl_flg             NUMBER (1) DEFAULT 0 NOT NULL,
   cntrct_sku           VARCHAR2 (60 ),
   cntrct_sku_desc      VARCHAR2 (2000 ),
   exp_dt               DATE,
   mthly_pymt           VARCHAR2 (20 ),
   plan_typ             VARCHAR2 (60 ),
   protection_plan_id   VARCHAR2 (60 ),
   rgst_trans_nbr       VARCHAR2 (30 ),
   stor_id              VARCHAR2 (10 ),
   wrkstn_id            VARCHAR2 (20 ),
   created_by           VARCHAR2 (20 ) NOT NULL,
   created_on           DATE NOT NULL,
   amended_by           VARCHAR2 (20 ),
   amended_on           DATE
);

ALTER TABLE bst_ise_sch01.gsp_plan
  ADD CONSTRAINT gsp_plan_pk
  PRIMARY KEY (id);