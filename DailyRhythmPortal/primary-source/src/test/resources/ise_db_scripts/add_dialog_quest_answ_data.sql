


INSERT INTO BST_ISE_SCH01.dialog_question (dialog_question_id, question_code, question,
							instruction, instruction_detail, dialog_question_typ,
							image_url, display_typ, created_by, created_on) VALUES (
	1,
	'question_1',
	'Question 1',
	'Instruction 1',
	'Instruction Detail 1',
	'TRG',
	null,
	null,
	'a123',
	SYSDATE
);


INSERT INTO BST_ISE_SCH01.dialog_question (dialog_question_id, question_code, question,
							instruction, instruction_detail, dialog_question_typ,
							image_url, display_typ, created_by, created_on) VALUES (
	2,
	'question_2',
	'Question 2',
	'Instruction 2',
	'Instruction Detail 2',
	'TRG',
	'http://www.some.com/sample2.jpg',
	'R',
	'a123',
	SYSDATE
);

INSERT INTO BST_ISE_SCH01.dialog_question (dialog_question_id, question_code, question,
							instruction, instruction_detail, dialog_question_typ,
							image_url, display_typ, created_by, created_on) VALUES (
	3,
	'question_3',
	'Question 3',
	'Instruction 3',
	'Instruction Detail 3',
	'TRG',
	'http://www.some.com/sample3.jpg',
	'D',
	'a123',
	SYSDATE
);



INSERT INTO BST_ISE_SCH01.dialog_question_answer (dialog_question_answer_id, answer_code, answer,
						     created_by, created_on) VALUES (
	1,
	'choice_1',
	'Choice 1',
	'a123',
	SYSDATE
);


INSERT INTO BST_ISE_SCH01.dialog_question_answer (dialog_question_answer_id, answer_code, answer,
						     created_by, created_on) VALUES (
	2,
	'choice_2',
	'Choice 2',
	'a123',
	SYSDATE
);


