



INSERT INTO BST_ISE_SCH01.workflow_attribute_hist (wf_attribute_hist_id, wf_step_hist_id,
						name, val, created_by, created_on) VALUES (
	1,
	1,
	'COLOR',
	null,
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.workflow_attribute_seq.NEXTVAL FROM dual;

INSERT INTO BST_ISE_SCH01.workflow_attribute_hist (wf_attribute_hist_id, wf_step_hist_id,
						name, val, created_by, created_on) VALUES (
	2,
	1,
	'NATION',
	'usa',
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.workflow_attribute_seq.NEXTVAL FROM dual;

INSERT INTO BST_ISE_SCH01.workflow_attribute_hist (wf_attribute_hist_id, wf_step_hist_id,
						name, val, created_by, created_on) VALUES (
	3,
	2,
	'NAME',
	'john',
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.workflow_attribute_seq.NEXTVAL FROM dual;

INSERT INTO BST_ISE_SCH01.workflow_attribute_hist (wf_attribute_hist_id, wf_step_hist_id,
						name, val, created_by, created_on) VALUES (
	4,
	3,
	'COLOR',
	'red',
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.workflow_attribute_seq.NEXTVAL FROM dual;


INSERT INTO BST_ISE_SCH01.workflow_attribute_hist (wf_attribute_hist_id, wf_step_hist_id,
						name, val, created_by, created_on) VALUES (
	5,
	3,
	'NATION',
	null,
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.workflow_attribute_seq.NEXTVAL FROM dual;

