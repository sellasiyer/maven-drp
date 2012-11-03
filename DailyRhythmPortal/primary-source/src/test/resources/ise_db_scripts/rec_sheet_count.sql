select t.store_id, t.emp_crt_last_nm, t.emp_crt_frst_nm, changedBy, t.changeDate,  sum(cnt) sumcnt 
		      from  (select store_id,emp_crt_last_nm, emp_crt_frst_nm, created_by changedBy, to_char(created_on, 'yyyymmdd') changeDate, 
		      recommendation_id, count(*) cnt  
		       from BST_ISE_SCH01.recommendation 
		       where store_id = :store_id  AND created_on between :created_on_start AND :created_on_end  
		       group by store_id, emp_crt_last_nm, emp_crt_frst_nm, created_by, to_char(created_on,'yyyymmdd'),  recommendation_id 
		       UNION 
		       select store_id, emp_crt_last_nm, emp_crt_frst_nm,  amended_by changedBy, to_char(amended_on, 'yyyymmdd') 
		       changeDate,  recommendation_id, count(*) cnt 
		       from BST_ISE_SCH01.recommendation 
		       where store_id = :store_id  AND amended_on between :amended_on_start AND :amended_on_end 
		       group by store_id, emp_crt_last_nm, emp_crt_frst_nm, amended_by, to_char(amended_on, 'yyyymmdd'), recommendation_id) t  
		       group by t.store_id, t.emp_crt_last_nm, t.emp_crt_frst_nm, changedBy,  t.changeDate  
		       order by t.store_id, t.emp_crt_last_nm, t.emp_crt_frst_nm, changedBy, t.changeDate ;
