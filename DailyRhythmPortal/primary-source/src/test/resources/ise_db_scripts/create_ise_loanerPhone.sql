SET MODE Oracle;

CREATE SCHEMA IF NOT EXISTS BST_ISE_SCH01;

/*==============================================================*/
/* Table: OS_LKUP                                               */
/*==============================================================*/
create table BST_ISE_SCH01.OS_LKUP  (
   OS_LKUP_ID           NUMBER(10)                      not null,
   OS_NM                VARCHAR2(20),
   INSTRCTN             VARCHAR2(2000),
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP
);

alter table BST_ISE_SCH01.OS_LKUP
   add constraint OS_LKUP_PK primary key (OS_LKUP_ID);

create sequence OS_LKUP_SEQ
increment by 1
start with 1;
      
/*==============================================================*/
/* Table: CARR_LKUP                                             */
/*==============================================================*/
create table BST_ISE_SCH01.CARR_LKUP  (
   CARR_LKUP_ID         NUMBER(10)                      not null,
   CARR                 VARCHAR2(20),
   CARR_LOANR_SKU       VARCHAR2(20),
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP
);

alter table BST_ISE_SCH01.CARR_LKUP
   add constraint CARR_LKUP_PK primary key (CARR_LKUP_ID);

create sequence CARR_LKUP_SEQ
increment by 1
start with 1;


/*==============================================================*/
/* Table: LOANR_PH                                              */
/*==============================================================*/
create table BST_ISE_SCH01.LOANR_PH  (
   LOANR_PH_ID          NUMBER(10)                      not null,
   STOR_ID              NUMBER(10)                      not null,
   CARR_LKUP_ID         NUMBER(10)                      not null,
   OS_LKUP_ID           NUMBER(10)                      not null,
   IMEI_ESN             VARCHAR2(100),
   MAKE                 VARCHAR2(100),
   MDL                  VARCHAR2(100),
   PH_COND              VARCHAR2(50),
   PH_COND_CMNT         VARCHAR2(500),
   LAST_ACTN_USR_ID     VARCHAR2(20),
   BBY_SKU              VARCHAR2(20),
   DEL_REAS             VARCHAR2(100),
   ACTV_FLG             NUMBER(1),
   USR_FRST_NM			VARCHAR2(60),
   USR_LAST_NM			VARCHAR2(60),
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP
);

alter table BST_ISE_SCH01.LOANR_PH
   add constraint PHONE_PK primary key (LOANR_PH_ID);

create index PH_IX1 on BST_ISE_SCH01.LOANR_PH (
   STOR_ID ASC
);

create index PH_IX2 on BST_ISE_SCH01.LOANR_PH (
   CARR_LKUP_ID ASC
);

create index PH_IX3 on BST_ISE_SCH01.LOANR_PH (
   OS_LKUP_ID ASC
);

create index PH_IX4 on BST_ISE_SCH01.LOANR_PH (
   ACTV_FLG ASC
);

alter table BST_ISE_SCH01.LOANR_PH
   add constraint CARR_LKUP_FK foreign key (CARR_LKUP_ID)
      references CARR_LKUP (CARR_LKUP_ID)
      on delete cascade
      not deferrable;

alter table BST_ISE_SCH01.LOANR_PH
   add constraint OS_LKUP_FK foreign key (OS_LKUP_ID)
      references OS_LKUP (OS_LKUP_ID)
      on delete cascade
      not deferrable;

alter table bst_ise_sch01.loanr_ph add constraint loanr_ph_uk1 unique (stor_id,imei_esn);
      
create sequence bst_ise_sch01.PHONE_SEQ
increment by 1
start with 1000;
 

/*==============================================================*/
/* Table: CHKOUT_CHKIN_HIST                                     */
/*==============================================================*/
create table BST_ISE_SCH01.CHKOUT_CHKIN_HIST  (
   CHKOUT_CHKIN_HIST_ID NUMBER(10)                      not null,
   LOANR_PH_ID          NUMBER(10)                      not null,
   CUST_FRST_NM         VARCHAR2(100),
   CUST_LAST_NM         VARCHAR2(100),
   CUST_CNTCT_PH        VARCHAR2(20),
   CUST_EMAIL           VARCHAR2(100),
   CUST_SVC_ORD_ID     VARCHAR2(30),
   FLFLMNT_TYP          VARCHAR2(50),
   GSP_ID              VARCHAR2(30),
   CHKOUT_TM            TIMESTAMP,
   CHKIN_TM             TIMESTAMP,
   CHKOUT_COND          VARCHAR2(100),
   CHKIN_COND           VARCHAR2(100),
   CHKOUT_COND_CMNT     VARCHAR2(500),
   CHKIN_COND_CMNT      VARCHAR2(500),
   CHKOUT_USR_NM        VARCHAR2(200),
   CHKIN_USR_NM         VARCHAR2(200),
   CHKOUT_DEP           NUMBER(8,2),
   CHKIN_DEP            NUMBER(8,2),
   CHKOUT_STAT          NUMBER(1),
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   REGISTER_ID          NUMBER(10,0),
   TRANSACTION_NUMBER   NUMBER(10,0),
   TRANSACTION_DATE     DATE,
   NOTES                VARCHAR(2000),
   POS_STORE_ID         VARCHAR2(10)
);

alter table BST_ISE_SCH01.CHKOUT_CHKIN_HIST
   add constraint CHKOUT_CHKIN_HIST_PK primary key (CHKOUT_CHKIN_HIST_ID);

create index COCIHST_IX1 on BST_ISE_SCH01.CHKOUT_CHKIN_HIST (
   LOANR_PH_ID ASC
);

alter table BST_ISE_SCH01.CHKOUT_CHKIN_HIST
   add constraint CHKOUT_CHKIN_HIST_FK foreign key (LOANR_PH_ID)
      references LOANR_PH (LOANR_PH_ID)
      on delete cascade
      not deferrable;

create sequence bst_ise_sch01.CHKOUT_CHKIN_HIST_SEQ
increment by 1
start with 1000;
 
 
/*==============================================================*/
/* Table: PHONE_EXTRAS                                          */
/*==============================================================*/
create table BST_ISE_SCH01.PHONE_EXTRAS  (
   PHONE_EXTRAS_ID      NUMBER(10)                      not null,
   CHKOUT_CHKIN_HIST_ID NUMBER(10)                      not null,
   PH_XTRA_TYP          VARCHAR2(100),
   CHKOUT_STAT          VARCHAR2(10),
   CHKIN_STAT           VARCHAR2(10),
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP
);

alter table BST_ISE_SCH01.PHONE_EXTRAS
   add constraint PHONE_EXTRAS_PK primary key (PHONE_EXTRAS_ID);

create index PHXTRA_IX1 on BST_ISE_SCH01.PHONE_EXTRAS (
   CHKOUT_CHKIN_HIST_ID ASC
);

alter table BST_ISE_SCH01.PHONE_EXTRAS
   add constraint PH_EXTRAS_FK foreign key (CHKOUT_CHKIN_HIST_ID)
      references CHKOUT_CHKIN_HIST (CHKOUT_CHKIN_HIST_ID)
      on delete cascade
      not deferrable;

create sequence bst_ise_sch01.PH_EXTRAS_SEQ
increment by 1
start with 1;
 
 