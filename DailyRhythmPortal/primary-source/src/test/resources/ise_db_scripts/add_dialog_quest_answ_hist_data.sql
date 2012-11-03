




INSERT INTO BST_ISE_SCH01.dialog_question_hist (dg_question_hist_id, dialog_question_id, dialog_history_id,
							sequence_number, answer_text,
						     created_by, created_on) VALUES (
	1,
	1,
	1,
	1,
	'Answer 1 DLG 1',
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.dialog_question_hist_seq.NEXTVAL FROM dual;


INSERT INTO BST_ISE_SCH01.dialog_question_hist (dg_question_hist_id, dialog_question_id, dialog_history_id,
							sequence_number, answer_text,
						     created_by, created_on) VALUES (
	2,
	2,
	1,
	2,
	'choice_2',
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.dialog_question_hist_seq.NEXTVAL FROM dual;



INSERT INTO BST_ISE_SCH01.dialog_question_answer_hist (dg_question_answer_hist_id, dg_question_hist_id,
							answer_code, answer, sequence_number,
						     created_by, created_on) VALUES (
	1,
	2,
	'choice_1',
	'Choice 1',
	1,
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.dg_question_answer_hist_seq.NEXTVAL FROM dual;


INSERT INTO BST_ISE_SCH01.dialog_question_answer_hist (dg_question_answer_hist_id, dg_question_hist_id,
							answer_code, answer, sequence_number,
						     created_by, created_on) VALUES (
	2,
	2,
	'choice_2',
	'Choice 2',
	2,
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.dg_question_answer_hist_seq.NEXTVAL FROM dual;






INSERT INTO BST_ISE_SCH01.dialog_question_hist (dg_question_hist_id, dialog_question_id, dialog_history_id,
							sequence_number, answer_text,
						     created_by, created_on) VALUES (
	3,
	1,
	2,
	1,
	'Answer 1 DLG 2',
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.dialog_question_hist_seq.NEXTVAL FROM dual;



INSERT INTO BST_ISE_SCH01.dialog_question_hist (dg_question_hist_id, dialog_question_id, dialog_history_id,
							sequence_number, answer_text,
						     created_by, created_on) VALUES (
	4,
	2,
	3,
	1,
	'Answer 1 DLG 3',
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.dialog_question_hist_seq.NEXTVAL FROM dual;



INSERT INTO BST_ISE_SCH01.dialog_question_hist (dg_question_hist_id, dialog_question_id, dialog_history_id,
							sequence_number, answer_text,
						     created_by, created_on) VALUES (
	5,
	1,
	3,
	2,
	'Answer 2 DLG 3',
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.dialog_question_hist_seq.NEXTVAL FROM dual;



INSERT INTO BST_ISE_SCH01.dialog_question_answer_hist (dg_question_answer_hist_id, dg_question_hist_id,
							answer_code, answer, sequence_number,
						     created_by, created_on) VALUES (
	3,
	5,
	'choice_1',
	'Choice 1',
	1,
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.dg_question_answer_hist_seq.NEXTVAL FROM dual;

INSERT INTO BST_ISE_SCH01.dialog_question_answer_hist (dg_question_answer_hist_id, dg_question_hist_id,
							answer_code, answer, sequence_number,
						     created_by, created_on) VALUES (
	4,
	5,
	'choice_2',
	'Choice 2',
	2,
	'a123',
	SYSDATE
);
SELECT BST_ISE_SCH01.dg_question_answer_hist_seq.NEXTVAL FROM dual;
