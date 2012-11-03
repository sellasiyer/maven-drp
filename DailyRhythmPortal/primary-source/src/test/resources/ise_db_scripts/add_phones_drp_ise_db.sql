SET MODE Oracle;

insert into BST_ISE_SCH01.CARR_LKUP (CARR_LKUP_ID, CARR, CARR_LOANR_SKU,
			 created_by, created_on)
			values (1,
			'AT&'||'T',
			'8459072',
			'a186288', SYSDATE);
			
insert into BST_ISE_SCH01.CARR_LKUP (CARR_LKUP_ID, CARR, CARR_LOANR_SKU,
			 created_by, created_on)
			values (2,
			'Sprint',
			'8459081',
			'a186288', SYSDATE);
			
insert into BST_ISE_SCH01.CARR_LKUP (CARR_LKUP_ID, CARR, CARR_LOANR_SKU,
			 created_by, created_on)
			values (3,
			'T-Mobile',
			'8459107',
			'a186288', SYSDATE);
			
insert into BST_ISE_SCH01.CARR_LKUP (CARR_LKUP_ID, CARR, CARR_LOANR_SKU,
			 created_by, created_on)
			values (4,
			'Verizon',
			'8459116',
			'a186288', SYSDATE);

insert into BST_ISE_SCH01.OS_LKUP (OS_LKUP_ID, OS_NM, INSTRCTN,
			 created_by, created_on)
			values (1,
			'iOS',
			'<p>Follow the steps below to perform a hard reset. Note: All data will be permanently erased. </p><ol><li>Press and hold down the Home button for about 10 seconds until a yellow triangle with text that says "Please connect to iTunes" appears</li><li>Connect the iPhone to a computer and open iTunes if it''s not already running</li><li>iTunes will prompt a message that says "iTunes has detected an iPhone in recovery mode". Click "Ok"</li><li>You will be taken to the iPhone device page. Click on ''Restore''.  Wait for the full restoration process to complete</li><li>Once the device is back on, the reset is complete.</li></ol>',
			'a186288', SYSDATE);
			
insert into BST_ISE_SCH01.OS_LKUP (OS_LKUP_ID, OS_NM, INSTRCTN,
			 created_by, created_on)
			values (2,
			'BlackBerry',
			'<p>Follow the steps below to perform a hard reset. Note: All data will be permanently erased.</p> <ol><li>Click on ''Options''</li><li>Click on ''Security Options''</li><li>Select ''Security Wipe''</li><li>Once the device is back on, the reset is complete.</li></ol>',
			'a186288', SYSDATE);
			
insert into BST_ISE_SCH01.OS_LKUP (OS_LKUP_ID, OS_NM, INSTRCTN,
			 created_by, created_on)
			 values (3,
			'Android',
			'<p>Follow the steps below to perform a hard reset. Note: All data will be permanently erased. </p><ol><li>From the main home screen, tap ''Menu'', tap ''Settings'', then ''Privacy''</li><li>Tap ''Factory Data Reset'' then tap ''Reset Phone''</li><li>Enter a password if required, then tap ''Erase Everything'' to confirm</li><li>Once the device is back on, the reset is complete.</li></ol>',
			'a186288', SYSDATE);
			
insert into BST_ISE_SCH01.OS_LKUP (OS_LKUP_ID, OS_NM, INSTRCTN,
			 created_by, created_on)
			values (4,
			'Windows',
			'<p>Follow the steps below to perform a hard reset. Note: All data will be permanently erased. </p><ol><li>From the application screen, tap ''Settings'' and select ''About''</li><li>Tap the ''Reset Your Phone'' box</li><li>Once the device is back on, the reset is complete.</li></ol>',
			'a186288', SYSDATE);
			
insert into BST_ISE_SCH01.OS_LKUP (OS_LKUP_ID, OS_NM, INSTRCTN,
			 created_by, created_on)
			values (5,
			'Other',
			'<p>Follow the steps below to perform a hard reset. Note: All data will be permanently erased. </p><ol><li>Go to ''Settings''</li><li>Search for ''Security'', ''About'', or ''Clear Data''</li><li>Once the device is back on, the reset is complete.</li></ol>',
			'a186288', SYSDATE);



INSERT INTO BST_ISE_SCH01.LOANR_PH (LOANR_PH_ID, STOR_ID, CARR_LKUP_ID, OS_LKUP_ID, IMEI_ESN, MAKE, MDL,ACTV_FLG,LAST_ACTN_USR_ID,CREATED_BY,CREATED_ON,PH_COND) VALUES (
	1,
	'0699',
	1,
	1,
	'12345678',
	'Apple',
	'iphone 4S',
	1,
	'a123',
	'a123',
	SYSDATE,
	'DAMAGED'
);

INSERT INTO BST_ISE_SCH01.LOANR_PH (LOANR_PH_ID, STOR_ID, CARR_LKUP_ID, OS_LKUP_ID, IMEI_ESN, MAKE, MDL,ACTV_FLG,LAST_ACTN_USR_ID,CREATED_BY,CREATED_ON,PH_COND) VALUES (
	2,
	'0699',
	2,
	2,
	'87654321',
	'Android',
	'Samsung',
	1,
	'a234',
	'a234',
	SYSDATE,
	'GOOD'
);

INSERT INTO BST_ISE_SCH01.LOANR_PH (LOANR_PH_ID, STOR_ID, CARR_LKUP_ID, OS_LKUP_ID, IMEI_ESN, MAKE, MDL,ACTV_FLG,LAST_ACTN_USR_ID,CREATED_BY,CREATED_ON,PH_COND) VALUES (
	3,
	'0699',
	1,
	1,
	'12348765',
	'Apple',
	'iphone 4S',
	1,
	'a123',
	'a123',
	SYSDATE,
	'GOOD'
);

INSERT INTO BST_ISE_SCH01.LOANR_PH (LOANR_PH_ID, STOR_ID, CARR_LKUP_ID, OS_LKUP_ID, IMEI_ESN, MAKE, MDL,ACTV_FLG,LAST_ACTN_USR_ID,CREATED_BY,CREATED_ON,PH_COND) VALUES (
	4,
	'0699',
	1,
	1,
	'11111111',
	'Apple',
	'iphone 4S',
	0,
	'a123',
	'a123',
	SYSDATE,
	'LOST'
);

INSERT INTO BST_ISE_SCH01.CHKOUT_CHKIN_HIST(CHKOUT_CHKIN_HIST_ID,LOANR_PH_ID,CUST_FRST_NM,CUST_LAST_NM,CUST_SVC_ORD_ID,CHKOUT_TM,CHKIN_TM,CREATED_BY,CREATED_ON,CHKOUT_STAT) VALUES (
1,
1,
'Deepthi',
'Vankayala',
'111112345',
SYSDATE - 3,
SYSDATE - 4,
'a123',
SYSDATE,
1
);

INSERT INTO BST_ISE_SCH01.CHKOUT_CHKIN_HIST(CHKOUT_CHKIN_HIST_ID,LOANR_PH_ID,CUST_FRST_NM,CUST_LAST_NM,CUST_SVC_ORD_ID,CHKOUT_TM,CHKIN_TM,CREATED_BY,CREATED_ON,CHKOUT_STAT) VALUES (
2,
3,
'Eric',
'Togerson',
'222222222',
SYSDATE - 4,
SYSDATE - 3,
'a123',
SYSDATE,
0
);

INSERT INTO BST_ISE_SCH01.CHKOUT_CHKIN_HIST(CHKOUT_CHKIN_HIST_ID,LOANR_PH_ID,CUST_FRST_NM,CUST_LAST_NM,CUST_SVC_ORD_ID,CHKOUT_TM,CHKIN_TM,CREATED_BY,CREATED_ON,CHKOUT_STAT) VALUES (
3,
2,
'Mike',
'D',
'333333333',
SYSDATE - 5,
SYSDATE - 4,
'a123',
SYSDATE,
0
);

