
INSERT INTO BST_ISE_SCH01.workflow (workflow_id, cust_first_name, cust_last_name,
						work_flow_typ, start_tm, status,
						wf_comment, created_by, created_on) VALUES (
	1,
	'John',
	'Morgan',
	'TRIAGE',
	SYSDATE,
	'GOOD',
	'Hello',
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.workflow_seq.NEXTVAL FROM dual;

