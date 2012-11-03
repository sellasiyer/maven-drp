package com.bestbuy.bbym.ise.drp.tools;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.domain.DataUsageFactors;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author JAM0314
 */
public class DataUsageCalculatorPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(DataUsageCalculatorPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "drpPropertyService")
    private DrpPropertyService drpPropertyService;

    public DataUsageCalculatorPage(final PageParameters parameters) {
	super(parameters);
	DataUsageCalculatorPanel dataUsageCalculatorPanel = new DataUsageCalculatorPanel("dataUsagePanel", true);
	dataUsageCalculatorPanel.setOutputMarkupPlaceholderTag(true);
	add(dataUsageCalculatorPanel);

	List<DataUsageFactors> factorList = new ArrayList<DataUsageFactors>();
	try{
	    factorList.add(DataUsageFactors.buildDataUsageFactors(DataUsageFactors.DataUsageTypes.THREE_G,
		    drpPropertyService));
	    factorList.add(DataUsageFactors.buildDataUsageFactors(DataUsageFactors.DataUsageTypes.THREE_FOUR_G,
		    drpPropertyService));
	    factorList.add(DataUsageFactors.buildDataUsageFactors(DataUsageFactors.DataUsageTypes.FOUR_G,
		    drpPropertyService));

	}catch(ServiceException e){
	    logger.error("Error", e);
	    //POPUP WARNING MODAL.
	}

	String customText = drpPropertyService.getProperty("DU_CUSTOMTEXT", "");
	dataUsageCalculatorPanel.setDisclaimerText(customText);

	JSONArray json = JSONArray.fromObject(factorList);

	StringBuilder jsonString = new StringBuilder();
	jsonString.append("wicket3Gvals = " + json.get(0) + ";");
	jsonString.append("wicket3G4Gvals = " + json.get(1) + ";");
	jsonString.append("wicket4Gvals = " + json.get(2) + ";");
	jsonString.append("setBandwidth(\"3g4g\");");

	Label myScript = new Label("myScript", jsonString.toString());
	myScript.setEscapeModelStrings(false);
	add(myScript);

    }

}
