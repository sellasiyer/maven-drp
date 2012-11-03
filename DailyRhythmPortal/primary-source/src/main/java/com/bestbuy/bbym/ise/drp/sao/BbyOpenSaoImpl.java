package com.bestbuy.bbym.ise.drp.sao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Sku;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("bbyOpenSao")
public class BbyOpenSaoImpl extends AbstractSao implements BbyOpenSao {

    private static Logger logger = LoggerFactory.getLogger(BbyOpenSaoImpl.class);
    private static final String SERVICE = "BBY OPEN ";

    private static final String STORE_ID = "storeId";
    private static final String SKU = "sku";
    private static final String UPC = "upc";
    private static final String APIKEY = "apiKey";
    private static final String SHOW = "show";
    private static final String SKU_AND_ACCESSORIES_SHOW_VALUES = "name,sku,image,accessories";
    private static final String SKU_AND_ACCESSORIES_VALUES = "products.sku,products.name,products.regularPrice,products.salePrice,products.thumbnailImage,products.shortDescription,products.longDescription,products.subclassId";

    @Autowired
    private RestOperations restOperations;

    @Override
    public Device getSKUDetails(String skuUpc) throws ServiceException, IseBusinessException {

	logger.info("getSKUDetails - Begin. sku/upc:" + skuUpc);

	if (skuUpc == null){
	    return null;
	}

	String url = drpPropertiesService.getProperty(DrpConstants.BBYOPEN_URL)
		+ "/v1/products((upc={upc}|sku={sku})&active=*)?show={show}&apiKey={apiKey}";
	logger.info("url:" + url);

	Map<String, Object> urlVariables = new HashMap<String, Object>();
	urlVariables.put(UPC, skuUpc);
	urlVariables.put(SKU, skuUpc);
	urlVariables.put(SHOW, "name,upc,sku,deviceManufacturer");
	urlVariables.put(APIKEY, drpPropertiesService.getProperty(DrpConstants.BBYOPEN_API_KEY));

	try{
	    String response = restOperations.getForObject(url, String.class, urlVariables);

	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();
	    String name = xPath.evaluate("/products/product[1]/name", doc);
	    String upc = xPath.evaluate("/products/product[1]/upc", doc);
	    String sku = xPath.evaluate("/products/product[1]/sku", doc);
	    String deviceManufacturer = xPath.evaluate("/products/product[1]/deviceManufacturer", doc);
	    if (StringUtils.isEmpty(name) || StringUtils.isEmpty(upc) || StringUtils.isEmpty(sku)){
		return null;
	    }
	    Device device = new Device();
	    device.setSku(sku);
	    device.setDescription(name);
	    device.setUpc(upc);
	    device.setManufacturer(deviceManufacturer);

	    return device;
	}catch(RestClientException e){
	    logger.error("Error calling BByOpen Products Service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling BByOpen Products Service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - get SKU details", t);
	    return null;
	}finally{
	    logger.info("getSKUDetails - End.");
	}
    }

    @Override
    public List<Sku> getSKUAndAccessories(String... skus) throws ServiceException, IseBusinessException {

	logger.info("getSKUAndAccessories - Begin. skus=" + ArrayUtils.toString(skus));

	if (skus == null || skus.length == 0){
	    return null;
	}

	String url = drpPropertiesService.getProperty(DrpConstants.BBYOPEN_URL)
		+ "/v1/products(sku in({sku})&active=*)?show={show}&apiKey={apiKey}";
	logger.info("url:" + url);

	Map<String, Object> urlVariables = new HashMap<String, Object>();
	urlVariables.put(SKU, StringUtils.join(skus, ','));
	urlVariables.put(SHOW, SKU_AND_ACCESSORIES_SHOW_VALUES);
	urlVariables.put(APIKEY, drpPropertiesService.getProperty(DrpConstants.BBYOPEN_API_KEY));

	List<Sku> productList = new ArrayList<Sku>();
	try{
	    String response = restOperations.getForObject(url, String.class, urlVariables);

	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    NodeList productNodeList = (NodeList) xPath.evaluate("//product", doc, XPathConstants.NODESET);
	    int length = productNodeList.getLength();
	    for(int i = 0; i < length; i++){

		if (productNodeList.item(i) != null){

		    Sku product = new Sku();

		    String skuId = xPath.evaluate("/products/product[" + (i + 1) + "]/sku", doc);
		    String skuName = xPath.evaluate("/products/product[" + (i + 1) + "]/name", doc);
		    String skuImage = xPath.evaluate("/products/product[" + (i + 1) + "]/image", doc);
		    NodeList accessoryNodeList = (NodeList) xPath.evaluate("/products/product[" + (i + 1)
			    + "]/accessories/sku", doc, XPathConstants.NODESET);

		    List<Sku> accessoryList = new ArrayList<Sku>();
		    for(int j = 0; j < accessoryNodeList.getLength(); j++){
			if (accessoryNodeList.item(j) != null){
			    Sku accessory = new Sku();
			    String accessorySku = accessoryNodeList.item(j).getTextContent();
			    accessory.setSku(accessorySku);
			    accessoryList.add(accessory);
			}

		    }
		    product.setSku(skuId);
		    product.setName(skuName);
		    product.setImage(skuImage);
		    product.setSkuList(accessoryList.size() > 0?accessoryList:null);
		    productList.add(product);
		}
	    }

	}catch(RestClientException e){
	    logger.error("Error calling BByOpen Products Service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling BByOpen Products Service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - get SKU and accessories", t);
	    return null;
	}finally{
	    logger.info("getSKUAndAccessories - End.");
	}

	return productList;
    }

    @Override
    public List<Sku> getAccessories(String storeId, String... skus) throws ServiceException, IseBusinessException {

	logger.info("getAccessories - Begin. skus=" + ArrayUtils.toString(skus));

	if (skus == null || skus.length == 0){
	    return null;
	}

	String url = drpPropertiesService.getProperty(DrpConstants.BBYOPEN_URL)
		+ "/v1/stores(storeId={storeId})+products(sku in({sku}))?show={show}&apiKey={apiKey}";
	logger.info("url:" + url);

	Map<String, Object> urlVariables = new HashMap<String, Object>();
	urlVariables.put(STORE_ID, storeId);
	urlVariables.put(SKU, StringUtils.join(skus, ','));
	urlVariables.put(SHOW, SKU_AND_ACCESSORIES_VALUES);
	urlVariables.put(APIKEY, drpPropertiesService.getProperty(DrpConstants.BBYOPEN_API_KEY));

	try{

	    String response = restOperations.getForObject(url, String.class, urlVariables);

	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    NodeList accessoryNodeList = (NodeList) xPath.evaluate("/stores/store/products/product", doc,
		    XPathConstants.NODESET);
	    List<Sku> accessoryList = new ArrayList<Sku>();
	    for(int i = 0; i < accessoryNodeList.getLength(); i++){
		if (accessoryNodeList.item(i) != null){

		    Sku accessory = new Sku();

		    String skuId = xPath.evaluate("/stores/store/products/product[" + (i + 1) + "]/sku", doc);
		    String skuName = xPath.evaluate("/stores/store/products/product[" + (i + 1) + "]/name", doc);
		    BigDecimal regularPrice = new BigDecimal(xPath.evaluate("/stores/store/products/product[" + (i + 1)
			    + "]/regularPrice", doc));
		    String image = xPath
			    .evaluate("/stores/store/products/product[" + (i + 1) + "]/thumbnailImage", doc);
		    String shortDescription = xPath.evaluate("/stores/store/products/product[" + (i + 1)
			    + "]/shortDescription", doc);
		    String longDescription = xPath.evaluate("/stores/store/products/product[" + (i + 1)
			    + "]/longDescription", doc);
		    String subClassId = xPath.evaluate("/stores/store/products/product[" + (i + 1) + "]/subclassId",
			    doc);

		    accessory.setSku(skuId);
		    accessory.setName(skuName);
		    accessory.setRegularPrice(regularPrice);
		    accessory.setImage(image);
		    accessory.setDescription(shortDescription);
		    accessory.setLongDescription(longDescription);
		    accessory.setSubClassId(StringUtils.isBlank(subClassId)?0:Integer.parseInt(subClassId));
		    accessoryList.add(accessory);
		}
	    }

	    return accessoryList;

	}catch(RestClientException e){
	    logger.error("Error calling BByOpen Stores Service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling BByOpen Stores Service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - get accessories", t);
	    return null;
	}finally{
	    logger.info("getAccessories - End.");
	}

    }

}
