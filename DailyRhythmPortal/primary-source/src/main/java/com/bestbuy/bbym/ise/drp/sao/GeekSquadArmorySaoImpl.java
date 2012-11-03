package com.bestbuy.bbym.ise.drp.sao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.Article;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("geekSquadArmorySao")
public class GeekSquadArmorySaoImpl extends AbstractSao implements GeekSquadArmorySao {

    private static Logger logger = LoggerFactory.getLogger(GeekSquadArmorySaoImpl.class);
    private static final String SERVICE = "GEEKSQUAD ARMORY ";

    @Autowired
    private RestOperations restOperations;

    @Override
    public List<Article> getArticles(String searchText, String model, Carrier carrier) throws ServiceException,
	    IseBusinessException {

	logger.info("getArticles - Begin. searchText=" + searchText + ", model=" + model + ", carrier=" + carrier);

	if (StringUtils.isEmpty(searchText)){
	    logger.info("Search Text cannot be null");
	    return new ArrayList<Article>();
	}

	String url = drpPropertiesService.getProperty(DrpConstants.GEEKSQUAD_ARMORY_URL)
		+ drpPropertiesService.getProperty(DrpConstants.GEEKSQUAD_ARMORY_SEARCH_SUFFIX);
	logger.info("url=" + url);

	Map<String, Object> urlVariables = new HashMap<String, Object>();
	urlVariables.put("text", searchText);
	urlVariables.put("model", model);
	urlVariables.put("carrier", carrier == null?null:carrier.getLabel());

	try{
	    Article[] articles = restOperations.getForObject(url, Article[].class, urlVariables);
	    if (articles == null){
		return new ArrayList<Article>();
	    }
	    return Arrays.asList(articles);
	}catch(RestClientException e){
	    logger.error("Error calling GeekSquad Armory Service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling GeekSquad Armory Service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - get articles", t);
	    return null;
	}finally{
	    logger.info("getArticles - End.");
	}
    }
}
