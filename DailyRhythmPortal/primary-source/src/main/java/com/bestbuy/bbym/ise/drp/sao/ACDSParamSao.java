package com.bestbuy.bbym.ise.drp.sao;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.ACDSParameters;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public interface ACDSParamSao {

    public List<ACDSParameters> getACDSParameters(String groupName) throws ServiceException, IseBusinessException;

}
