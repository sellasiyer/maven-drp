package com.bestbuy.bbym.ise.drp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.YGIBDevice;
import com.bestbuy.bbym.ise.drp.domain.YGIBResponse;
import com.bestbuy.bbym.ise.drp.sao.YGIBSao;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * YGIBServiceImpl Responsible for Tech Checker Service
 * 
 * @author a194869
 */
@Service("ygibService")
public class YGIBServiceImpl implements YGIBService {

    @Autowired
    private YGIBSao ygibSao;

    @Override
    public YGIBResponse processRequest(YGIBDevice device) throws ServiceException, IseBusinessException {
	YGIBResponse returnTestResult = ygibSao.postDeviceTest(device);
	return returnTestResult;
    }
}
