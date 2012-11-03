SET MODE Oracle;

CREATE SCHEMA IF NOT EXISTS BST_ISE_SCH01;



create table BST_ISE_SCH01.WORKFLOW  (
   WORKFLOW_ID          NUMBER(10)                      not null,
   CUST_FIRST_NAME      VARCHAR2(30),
   CUST_LAST_NAME       VARCHAR2(60),
   EXTERNAL_CUST_ID     NUMBER(10),
   WORK_FLOW_TYP        VARCHAR2(10)                    not null,
   START_TM             TIMESTAMP,
   END_TM               TIMESTAMP,
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   WF_COMMENT           VARCHAR2(500),
   STATUS               VARCHAR2(10),
   constraint WORKFLOW_PK primary key (WORKFLOW_ID)
);

create table BST_ISE_SCH01.WORKFLOW_STEP_HIST  (
   WF_STEP_HIST_ID      NUMBER(10)                      not null,
   WORKFLOW_ID          NUMBER(10)                      not null,
   START_TM             TIMESTAMP,
   END_TM               TIMESTAMP,
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   SEQUENCE_NUMBER      NUMBER(10)                      not null,
   ADDSTEP_ID           NUMBER(10)                      not null,
   STEP_COMMENT         VARCHAR2(1000),
   constraint PK_WORKFLOW_STEP_HIST primary key (WF_STEP_HIST_ID),
   constraint WSH_FK1 foreign key (WORKFLOW_ID)
         references BST_ISE_SCH01.WORKFLOW (WORKFLOW_ID)
         on delete cascade
         not deferrable
);

create table BST_ISE_SCH01.DIALOG_HISTORY  (
   DIALOG_HISTORY_ID    NUMBER(10)                      not null,
   WF_STEP_HIST_ID      NUMBER(10)                      not null,
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   NAME                 VARCHAR2(50)                    not null,
   constraint PK_DIALOG_HISTORY primary key (DIALOG_HISTORY_ID),
   constraint DH_FK1 foreign key (WF_STEP_HIST_ID)
         references BST_ISE_SCH01.WORKFLOW_STEP_HIST (WF_STEP_HIST_ID)
         on delete cascade
         not deferrable
);

create table BST_ISE_SCH01.DIALOG_QUESTION  (
   DIALOG_QUESTION_ID   NUMBER(10)                      not null,
   QUESTION_CODE        VARCHAR2(40)                    not null,
   QUESTION             VARCHAR2(1000),
   DIALOG_QUESTION_TYP  VARCHAR2(15)                    not null,
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   INSTRUCTION          VARCHAR2(2000),
   INSTRUCTION_DETAIL   VARCHAR2(1000),
   IMAGE_URL            VARCHAR2(500),
   DISPLAY_TYP          VARCHAR2(1),
   constraint DIALOG_QUESTION_PK primary key (DIALOG_QUESTION_ID)
);

create table BST_ISE_SCH01.DIALOG_QUESTION_ANSWER  (
   DIALOG_QUESTION_ANSWER_ID NUMBER(10)                      not null,
   ANSWER               VARCHAR2(500),
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   ANSWER_CODE          VARCHAR2(40)                    not null,
   constraint DIALOG_QUESTION_ANSWER_PK primary key (DIALOG_QUESTION_ANSWER_ID)
);

create table BST_ISE_SCH01.DIALOG_QUESTION_HIST  (
   DG_QUESTION_HIST_ID  NUMBER(10)                      not null,
   DIALOG_HISTORY_ID    NUMBER(10)                      not null,
   DIALOG_QUESTION_ID   NUMBER(10),
   ANSWER_TEXT          VARCHAR2(500),
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   SEQUENCE_NUMBER      NUMBER(10)                      not null,
   constraint DIALOG_QUESTION_HIST_PK primary key (DG_QUESTION_HIST_ID),
   constraint DQH_FK1 foreign key (DIALOG_HISTORY_ID)
         references BST_ISE_SCH01.DIALOG_HISTORY (DIALOG_HISTORY_ID)
         on delete cascade
         not deferrable,
   constraint DQH_FK2 foreign key (DIALOG_QUESTION_ID)
         references DIALOG_QUESTION (DIALOG_QUESTION_ID)
         on delete cascade
         not deferrable
);

create table BST_ISE_SCH01.DIALOG_QUESTION_ANSWER_HIST  (
   DG_QUESTION_ANSWER_HIST_ID NUMBER(10)                      not null,
   DG_QUESTION_HIST_ID  NUMBER(10)                      not null,
   ANSWER_CODE          VARCHAR2(40)                    not null,
   ANSWER               VARCHAR2(500)                   not null,
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   SEQUENCE_NUMBER      NUMBER(10)                      not null,
   constraint PK_DIALOG_QUESTION_ANSWER_HIST primary key (DG_QUESTION_ANSWER_HIST_ID),
   constraint DQAH_FK1 foreign key (DG_QUESTION_HIST_ID)
         references BST_ISE_SCH01.DIALOG_QUESTION_HIST (DG_QUESTION_HIST_ID)
         on delete cascade
         not deferrable
);

create table BST_ISE_SCH01.HOTLINKS  (
   ID                   NUMBER                          not null,
   URL_ALIAS            VARCHAR2(100)                   not null,
   URL                  VARCHAR2(500)                   not null,
   MODIFIED_BY          VARCHAR2(20)                    not null,
   MODIFIED_DATE        TIMESTAMP                       not null,
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_DATE         TIMESTAMP,
   USER_ID              VARCHAR2(20)                    not null,
   DISPLAY_ORDER        NUMBER(10),
   constraint HOTLINKS_PK primary key (ID)
);

create table BST_ISE_SCH01.ISE_PROPERTIES  (
   PROP_NAME            VARCHAR2(50)                    not null,
   PROP_VALUE           VARCHAR2(200)                   not null,
   PROP_DESC            VARCHAR2(200),
   CREATED_BY           VARCHAR2(20),
   CREATED_DATE         TIMESTAMP,
   MODIFIED_BY          VARCHAR2(20),
   MODIFIED_DATE        TIMESTAMP,
   PROP_ID              NUMBER(10)                      not null,
   IS_DRP_PROP_FLG      NUMBER(1,0),
   constraint ISE_PROPERTIES_PK primary key (PROP_ID)
);

create table BST_ISE_SCH01.RECOMMENDATION  (
   RECOMMENDATION_ID    NUMBER(10)                      not null,
   DEVICE_CAPABILITIES  NUMBER(38)                     default 0 not null,
   WOW_REQUIREMENTS     NUMBER(38)                     default 0 not null,
   RECOMMENDED_SUBSCRIPTION VARCHAR2(250),
   RECOMMENDED_DEVICE   VARCHAR2(100),
   NET_USE_INFO         VARCHAR2(500),
   NOTES                VARCHAR2(500),
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   STORE_ID             VARCHAR2(10),
   EMP_CONTACT          VARCHAR2(100),
   FIRST_NAME           VARCHAR2(50),
   LAST_NAME            VARCHAR2(50),
   MOBILE_NO            VARCHAR2(25),
   BEST_CONTACT_TM_INFO VARCHAR2(100),
   BBY_CUSTOMER_ID      VARCHAR2(20),
   UPGRADE_REMINDER_TEXT VARCHAR2(1),
   UPGRADE_REMINDER_CALL VARCHAR2(1),
   UPGRADE_ELIGIBILITY_DATE DATE,
   SUBSCRIPTION_INFO    VARCHAR2(200),
   TRADE_IN_VAL         NUMBER(6,2),
   EMP_CRT_FRST_NM      VARCHAR(50),
   EMP_CRT_LAST_NM      VARCHAR(50),
   EMP_ALT_FRST_NM      VARCHAR(50),
   EMP_ALT_LAST_NM      VARCHAR(50),
   REC_SHT_TYP NUMBER(1,0),
   ADDR              VARCHAR2(250) ,
   CITY              VARCHAR2(100) ,
   STATE             VARCHAR2(100) ,
   ZIPCODE           VARCHAR2(10) ,
   BBY_CNS_FRST_NM   VARCHAR2(50) ,
   BBY_CNS_LAST_NM   VARCHAR2(50) ,
   BBY_CNS_PH_NBR    VARCHAR2(25) ,
   BBY_CNS_PH_EXT    VARCHAR2(10) ,
   LANG    VARCHAR2(20) ,
   constraint PK_RECOMMENDATION primary key (RECOMMENDATION_ID)
);

create table BST_ISE_SCH01.RECOMMENDATION_ESSENTIAL_TYPE  (
   RECOMMENDATION_ESENTIAL_TYP_ID NUMBER(10)                      not null,
   DESCRIPTION          VARCHAR2(200),
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   constraint RET_PK primary key (RECOMMENDATION_ESENTIAL_TYP_ID)
);

create table BST_ISE_SCH01.RECOMMENDATION_ESSENTIAL  (
   RECOMMENDATION_ESSENTIAL_ID NUMBER(10)                      not null,
   RECOMMENDATION_ID    NUMBER(10)                      not null,
   RECOMMENDATION_ESENTIAL_TYP_ID NUMBER(10),
   COMMENTS             VARCHAR2(200),
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   constraint PK_RECOMMENDATION_ESSENTIAL primary key (RECOMMENDATION_ESSENTIAL_ID),
   constraint RS_FK1 foreign key (RECOMMENDATION_ID)
         references BST_ISE_SCH01.RECOMMENDATION (RECOMMENDATION_ID)
         on delete cascade
         not deferrable,
   constraint RS_FK2 foreign key (RECOMMENDATION_ESENTIAL_TYP_ID)
         references BST_ISE_SCH01.RECOMMENDATION_ESSENTIAL_TYPE (RECOMMENDATION_ESENTIAL_TYP_ID)
         not deferrable
);

create table BST_ISE_SCH01.WORKFLOW_ATTRIBUTE_HIST  (
   WF_ATTRIBUTE_HIST_ID NUMBER(10)                      not null,
   NAME                 VARCHAR2(50)                    not null,
   VAL                  VARCHAR2(500),
   CREATED_BY           VARCHAR2(20)                    not null,
   CREATED_ON           TIMESTAMP                       not null,
   AMENDED_BY           VARCHAR2(20),
   AMENDED_ON           TIMESTAMP,
   WF_STEP_HIST_ID      NUMBER(10)                      not null,
   constraint PK_WORKFLOW_ATTRIBUTE_HIST primary key (WF_ATTRIBUTE_HIST_ID),
   constraint WAH_FK foreign key (WF_STEP_HIST_ID)
         references BST_ISE_SCH01.WORKFLOW_STEP_HIST (WF_STEP_HIST_ID)
         not deferrable
);


create sequence BST_ISE_SCH01.DEVICE_GROUP_SEQ
increment by 1
start with 1;

create sequence BST_ISE_SCH01.DEVICE_SEQ
increment by 1
start with 1;

create sequence BST_ISE_SCH01.DG_QUESTION_ANSWER_HIST_SEQ
increment by 1
start with 1;

create sequence BST_ISE_SCH01.DIALOG_HISTORY_SEQ
increment by 1
start with 1;

create sequence BST_ISE_SCH01.DIALOG_QUESTION_ANSWER_SEQ
increment by 1
start with 1;

create sequence BST_ISE_SCH01.DIALOG_QUESTION_DIALOG_SEQ
increment by 1
start with 1;

create sequence BST_ISE_SCH01.DIALOG_QUESTION_HIST_SEQ
increment by 1
start with 1;

create sequence BST_ISE_SCH01.HOTLINKS_SEQ
increment by 1
start with 1;
 
create sequence BST_ISE_SCH01.ISE_PROPERTIES_SEQ
increment by 1
start with 1;

create sequence BST_ISE_SCH01.SEQ_CUSTOMER
increment by 1
start with 1;

create sequence BST_ISE_SCH01.SEQ_CUSTOMER_DEVICE
increment by 1
start with 1;

create sequence BST_ISE_SCH01.SEQ_RECOMMENDATION
increment by 1
start with 1;

create sequence BST_ISE_SCH01.SEQ_RECOMMENDATION_ESENTL_TYPE
increment by 1
start with 1;

create sequence BST_ISE_SCH01.SEQ_RECOMMENDATION_ESSENTIAL
increment by 1
start with 1;

create sequence BST_ISE_SCH01.WORKFLOW_ATTRIBUTE_SEQ
increment by 1
start with 1;

create sequence BST_ISE_SCH01.WORKFLOW_SEQ
increment by 1
start with 1;

create sequence BST_ISE_SCH01.WORKFLOW_STEP_HIST_SEQ
increment by 1
start with 1;


