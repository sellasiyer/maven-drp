SET MODE Oracle;

CREATE SCHEMA IF NOT EXISTS BST_ISE_SCH01;

CREATE TABLE BST_ISE_SCH01.DEVICE
  (
    DEVC_ID      VARCHAR2(100) NOT NULL ,
    TECHNLGY_TYP VARCHAR2(50) ,
    HANDSET_ID   VARCHAR2(100) ,
    MDL_NBR      VARCHAR2(100) ,
    PH_NAME      VARCHAR2(100) ,
    MFG_NM       VARCHAR2(100) ,
    SKU          VARCHAR2(100) ,
    DESCRIPTION  VARCHAR2(200) ,
    CREATED_BY   VARCHAR2(20) NOT NULL ,
    CREATED_ON DATE NOT NULL ,
    AMENDED_BY VARCHAR2(20) ,
    AMENDED_ON DATE 
  );
  
 ALTER TABLE BST_ISE_SCH01.DEVICE
  ADD CONSTRAINT DEVICE_PK
  PRIMARY KEY (DEVC_ID);

