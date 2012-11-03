package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.YGIBDevice;
import com.bestbuy.bbym.ise.drp.domain.YGIBResponse;
import com.bestbuy.bbym.ise.drp.domain.YGIBTestResult;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("ygibService2")
public class DummyYGIBService implements YGIBService {

    @Override
    public YGIBResponse processRequest(YGIBDevice device) throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	DummyData.throwIseBusinessException(30);
	if (device == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}

	YGIBResponse response = new YGIBResponse();
	if (DummyData.getRandomIndex(40) < 26){
	    response.setResponseCode(YGIBService.SUCCESS_RESPONSE_CODE);
	}else{
	    response.setResponseCode(-1);
	}
	response.setManufacturer(DummyData.getHardware());

	YGIBTestResult testResult;
	List<YGIBTestResult> testResults = new ArrayList<YGIBTestResult>();
	for(int i = 0; i < DummyData.getTriageTests().length; i++){
	    testResult = new YGIBTestResult();
	    testResult.setTestId(DummyData.getTriageTests()[i]);
	    testResult.setDesc(DummyData.getTriageTests()[i].toLowerCase());
	    testResult.setTimestamp(DummyData.getDate(5));
	    testResult.setStatus(DummyData.getTriageStatus());
	    testResults.add(testResult);
	}
	response.setTestResults(testResults);
	return response;
    }

}
