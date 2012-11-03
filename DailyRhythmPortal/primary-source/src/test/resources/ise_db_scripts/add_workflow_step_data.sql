




INSERT INTO BST_ISE_SCH01.workflow_step_hist (wf_step_hist_id, workflow_id, sequence_number,
						addstep_id, step_comment, start_tm, created_by, created_on) VALUES (
	1,
	1,
	1,
	101,
	'Step 101',
	SYSDATE,
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.workflow_step_hist_seq.NEXTVAL FROM dual;



INSERT INTO BST_ISE_SCH01.workflow_step_hist (wf_step_hist_id, workflow_id, sequence_number,
						addstep_id, step_comment, start_tm, created_by, created_on) VALUES (
	2,
	1,
	2,
	102,
	'Step 102',
	SYSDATE,
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.workflow_step_hist_seq.NEXTVAL FROM dual;



INSERT INTO BST_ISE_SCH01.workflow_step_hist (wf_step_hist_id, workflow_id, sequence_number,
						addstep_id, step_comment, start_tm, created_by, created_on) VALUES (
	3,
	1,
	3,
	103,
	'Step 103',
	SYSDATE,
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.workflow_step_hist_seq.NEXTVAL FROM dual;



