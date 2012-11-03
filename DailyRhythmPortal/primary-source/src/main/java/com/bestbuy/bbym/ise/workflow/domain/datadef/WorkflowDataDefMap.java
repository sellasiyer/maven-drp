package com.bestbuy.bbym.ise.workflow.domain.datadef;

import com.bestbuy.bbym.ise.util.jdbc.DataDef;
import com.bestbuy.bbym.ise.util.jdbc.DataDefMap;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;
import com.bestbuy.bbym.ise.workflow.domain.DialogAnswer;
import com.bestbuy.bbym.ise.workflow.domain.DialogQuestion;
import com.bestbuy.bbym.ise.workflow.domain.QueryAnswer;
import com.bestbuy.bbym.ise.workflow.domain.QueryQuestion;
import com.bestbuy.bbym.ise.workflow.domain.Workflow;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowAttribute;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowStep;

public class WorkflowDataDefMap implements DataDefMap {

    @Override
    public DataDef generateDataDef(String tableAlias) {
	if ("wf".equals(tableAlias)){
	    return new WorkflowDataDef();
	}else if ("wf_step".equals(tableAlias)){
	    return new WorkflowStepDataDef();
	}else if ("dlg_ans".equals(tableAlias)){
	    return new DialogAnswerDataDef();
	}else if ("dlg_ques".equals(tableAlias)){
	    return new DialogQuestionDataDef();
	}else if ("dlg".equals(tableAlias)){
	    return new DialogDataDef();
	}else if ("wf_attr".equals(tableAlias)){
	    return new WorkflowAttributeDataDef();
	}else if ("qry_ans".equals(tableAlias)){
	    return new QueryAnswerDataDef();
	}else if ("qry_ques".equals(tableAlias)){
	    return new QueryQuestionDataDef();
	}
	throw new RuntimeException("No DataDef for tableAlias " + tableAlias);
    }

    @Override
    public String getTableAlias(Class<?> dataClass) {
	if (dataClass == null){
	    throw new RuntimeException("Invalid input to getTableAlias");
	}
	if (Workflow.class.getName().equals(dataClass.getName())){
	    return "wf";
	}else if (WorkflowStep.class.getName().equals(dataClass.getName())){
	    return "wf_step";
	}else if (DialogAnswer.class.getName().equals(dataClass.getName())){
	    return "dlg_ans";
	}else if (DialogQuestion.class.getName().equals(dataClass.getName())){
	    return "dlg_ques";
	}else if (Dialog.class.getName().equals(dataClass.getName())){
	    return "dlg";
	}else if (WorkflowAttribute.class.getName().equals(dataClass.getName())){
	    return "wf_attr";
	}else if (QueryAnswer.class.getName().equals(dataClass.getName())){
	    return "qry_ans";
	}else if (QueryQuestion.class.getName().equals(dataClass.getName())){
	    return "qry_ques";
	}
	throw new RuntimeException("No tableAlias for className " + dataClass.getName());
    }

}
