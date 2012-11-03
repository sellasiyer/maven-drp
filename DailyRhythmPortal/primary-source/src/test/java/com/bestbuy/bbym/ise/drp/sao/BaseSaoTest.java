package com.bestbuy.bbym.ise.drp.sao;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.dao.TestUtil;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Base class for all SAO tests.
 */
public abstract class BaseSaoTest {

    protected DrpPropertyService drpPropertyService;

    @Before
    public void setUp() {
	drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
    }

    @Test
    public abstract void testTimeout();

    /**
     * Asserts that the international business hierarchy values are populated
     * correctly.
     */
    @Test
    public abstract void assertIbhValuesPopulatedCorrectly() throws Exception;

    /**
     * Sets expectations for calls to {@link DrpPropertyService} to get
     * international business hierarchy values.
     */
    protected final void expectIbhPropertiesCalls(boolean replay) {
	try{
	    EasyMock.expect(drpPropertyService.getProperty("IBH_ENTERPRISE")).andReturn("SomeEnterprise");
	    EasyMock.expect(drpPropertyService.getProperty("IBH_TRADING_AREA")).andReturn("SomeTradingArea");
	    EasyMock.expect(drpPropertyService.getProperty("IBH_COMPANY")).andReturn("SomeCompany");
	    EasyMock.expect(drpPropertyService.getProperty("IBH_BRAND")).andReturn("SomeBrand");
	    EasyMock.expect(drpPropertyService.getProperty("IBH_BUSINESS_UNIT")).andReturn("SomeBusinessUnit");
	    EasyMock.expect(drpPropertyService.getProperty("IBH_CHANNEL")).andReturn("SomeChannel");
	    if (replay){
		EasyMock.replay(drpPropertyService);
	    }
	}catch(ServiceException e){
	    fail("Failed to set up international business hierarchy properties");
	}
    }

    /**
     * Asserts that the international business hierarchy values are populated
     * correctly.
     */
    protected final void assertIbhValuesPopulatedCorrectly(Class ibhClass) throws Exception {

	Map<String, String> propertyNameValueMap = new HashMap<String, String>();
	propertyNameValueMap.put("enterprise", "SomeEnterprise");
	propertyNameValueMap.put("tradingArea", "SomeTradingArea");
	propertyNameValueMap.put("company", "SomeCompany");
	propertyNameValueMap.put("brand", "SomeBrand");
	propertyNameValueMap.put("businessUnit", "SomeBusinessUnit");
	propertyNameValueMap.put("channel", "SomeChannel");

	DrpPropertyService mockDrpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	EasyMock.expect(mockDrpPropertyService.getProperty("IBH_ENTERPRISE")).andReturn(
		propertyNameValueMap.get("enterprise"));
	EasyMock.expect(mockDrpPropertyService.getProperty("IBH_TRADING_AREA")).andReturn(
		propertyNameValueMap.get("tradingArea"));
	EasyMock.expect(mockDrpPropertyService.getProperty("IBH_COMPANY")).andReturn(
		propertyNameValueMap.get("company"));
	EasyMock.expect(mockDrpPropertyService.getProperty("IBH_BRAND")).andReturn(propertyNameValueMap.get("brand"));
	EasyMock.expect(mockDrpPropertyService.getProperty("IBH_BUSINESS_UNIT")).andReturn(
		propertyNameValueMap.get("businessUnit"));
	EasyMock.expect(mockDrpPropertyService.getProperty("IBH_CHANNEL")).andReturn(
		propertyNameValueMap.get("channel"));
	EasyMock.replay(mockDrpPropertyService);

	AbstractSao abstractSao = new AbstractSao() {

	    @Override
	    public <T> T getPopulatedInternationalBusinessHierarchy(T emptyIbh) throws ServiceException {
		return super.getPopulatedInternationalBusinessHierarchy(emptyIbh);
	    }

	};

	abstractSao.setDrpPropertiesService(mockDrpPropertyService);

	Object ibhClassInstance = abstractSao.getPopulatedInternationalBusinessHierarchy(ibhClass.newInstance());

	for(String propertyName: propertyNameValueMap.keySet()){
	    assertSame("Incorrect value for property: " + propertyName, propertyNameValueMap.get(propertyName),
		    BeanUtils.getProperty(ibhClassInstance, propertyName));
	}
    }

    /**
     * Reads the sample XML response from a file.
     * 
     * @param xmlFileName
     *            the file containing the XML response
     * @param responseClass
     *            the class that should be populated with the XML file contents
     */
    protected final <T> T loadResponse(String xmlFileName, Class<T> responseClass) {
	InputStream inputStream = TestUtil.readFileToInputStream(getClass(), xmlFileName);
	try{
	    JAXBContext jaxbContext = JAXBContext.newInstance(responseClass);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

	    JAXBElement<T> root = unmarshaller.unmarshal(new StreamSource(inputStream), responseClass);
	    return root.getValue();

	}catch(Exception e){
	    fail("Failed to unmarshall XML contents read from file " + xmlFileName + "\n" + e.getMessage());
	}

	return null;
    }

    /**
     * Reads the sample JSON response from a file.
     * 
     * @param jsonFileName
     *            the file containing the JSON response
     * @param responseClass
     *            the class that should be populated with the JSON file contents
     */
    protected final <T> T loadJsonResponse(String jsonFileName, Class<T> responseClass) {
	InputStream inputStream = TestUtil.readFileToInputStream(getClass(), jsonFileName);
	ObjectMapper mapper = new ObjectMapper();
	try{
	    return mapper.readValue(inputStream, responseClass);
	}catch(Exception e){
	    fail("Failed to unmarshall JSON contents read from file " + jsonFileName + "\n" + e.getMessage());
	}

	return null;
    }
}
