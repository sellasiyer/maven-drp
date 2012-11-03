package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("gspPlanService2")
public class DummyGSPPlanService implements GSPPlanService {

    @Override
    public List<GSPPlan> cancelGSPPlans(String dataSharingKey) throws ServiceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<GSPPlan> getGSPPlansMarkedForCancel(String dataSharingKey) throws ServiceException {
	DummyData.sleep(1);
	DummyData.throwServiceException(40);

	GSPPlan plan;
	List<GSPPlan> protectionPlanList = new ArrayList<GSPPlan>();
	int listSize = DummyData.getRandomIndex(8);
	for(int i = 0; i < listSize; i++){
	    plan = new GSPPlan();
	    plan.setProtectionPlanId(DummyData.getPlanNumber());
	    protectionPlanList.add(plan);
	}
	return protectionPlanList;
    }

    @Override
    public void addGSPPlan(GSPPlan gspPlan) throws ServiceException {
	DummyData.sleep(1);
	DummyData.throwServiceException(20);
    }

}
