package com.bestbuy.bbym.ise.drp.sao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.ACDSParameters;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientacdsparam.ACDSParamFault;
import com.bestbuy.bbym.ise.iseclientacdsparam.Group;
import com.bestbuy.bbym.ise.iseclientacdsparam.InternationalBusinessHierarchyType;
import com.bestbuy.bbym.ise.iseclientacdsparam.ParamGroupRequest;
import com.bestbuy.bbym.ise.iseclientacdsparam.ParamRequestType;
import com.bestbuy.bbym.ise.iseclientacdsparam.ParamResponseType;
import com.bestbuy.bbym.ise.iseclientacdsparam.ParamType;

/**
 * SAO Implementation for ACDS Parameters TSH Service.
 * 
 * @author a909237
 */
@Service("acdsParamSao")
public class ACDSParamSaoImpl extends AbstractSao implements ACDSParamSao {

    private static Logger logger = LoggerFactory.getLogger(ACDSParamSaoImpl.class);
    private static final String SERVICE = "ACDS PARAM ";

    @Override
    public List<ACDSParameters> getACDSParameters(String groupName) throws ServiceException, IseBusinessException {
	List<ACDSParameters> acdsParam = new ArrayList<ACDSParameters>();
	ParamRequestType paramRequest = prepareParamRequest(groupName);
	try{
	    logger.info("Invoking the ACDS Param Service");

	    ParamResponseType paramResponse = getACDSParamService().acdsParam(paramRequest);
	    acdsParam = getACDSParam(paramResponse);
	    logger.info("Response fetched with param size: " + acdsParam.size());
	}catch(ACDSParamFault e){
	    String faultMessage = e.getFaultInfo().getFault().get(0).getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "ACDS parameter service exception - getACDSParameters: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - get ACDS parameters", t);
	}
	logger.debug("ACDSParameters:" + acdsParam);
	return acdsParam;
    }

    private List<ACDSParameters> getACDSParam(ParamResponseType paramResponse) {
	List<ACDSParameters> acdsParamList = new ArrayList<ACDSParameters>();
	List<ParamType> acdsParamType = paramResponse.getParamValues().getParamValue();
	for(ParamType param: acdsParamType){
	    ACDSParameters acdsParam = new ACDSParameters();

	    if (StringUtils.isNotBlank(param.getGroup()))
		acdsParam.setGroupName(param.getGroup());
	    for(Group paramGroup: param.getParamGroup()){
		if ("1".equals(paramGroup.getActivationFlag())){
		    acdsParam.setActivationFlag(paramGroup.getActivationFlag());
		    if (StringUtils.isNotBlank(paramGroup.getSySCode()))
			acdsParam.setSysCode(paramGroup.getSySCode());
		    if (StringUtils.isNotBlank(paramGroup.getValue()))
			acdsParam.setValue(paramGroup.getValue());
		    if (StringUtils.isNotBlank(paramGroup.getDescription()))
			acdsParam.setDescription(paramGroup.getDescription());
		    if (acdsParam != null)
			acdsParamList.add(acdsParam);
		}
	    }
	}
	return acdsParamList;
    }

    private ParamRequestType prepareParamRequest(String groupName) throws ServiceException {
	ParamRequestType paramRequest = new ParamRequestType();
	paramRequest
		.setInternationBussinessHirerchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));
	logger.info("Assigning the ACDS Param request to the ParamRequestType: ");
	ParamGroupRequest groupRequest = new ParamGroupRequest();
	if (StringUtils.isNotBlank(groupName))
	    groupRequest.getGroup().add(groupName);
	logger.info("Group Name: " + groupRequest.getGroup().get(0).toString());
	if (groupRequest != null)
	    paramRequest.setGroupRequest(groupRequest);
	return paramRequest;
    }

}
