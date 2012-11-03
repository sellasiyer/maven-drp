package com.bestbuy.bbym.ise.workflow.rules;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.workflow.domain.Workflow;

@Ignore
public class TestWorkflowComp implements Comparator<Workflow> {

    private Logger logger = LoggerFactory.getLogger(TestWorkflowComp.class);

    @Override
    public int compare(Workflow wf1, Workflow wf2) {
	if (wf1 == null || wf2 == null){
	    logger.info("At least one Workflow object is null");
	    return -1;
	}
	if (wf1.getId() != wf2.getId()){
	    logger.info("Workflow.id values are not equal");
	    return -1;
	}
	if (!StringUtils.equals(wf1.getStatus(), wf2.getStatus())){
	    logger.info("Workflow.status values are not equal");
	    return -1;
	}
	return 0;
    }
}
