package com.bestbuy.bbym.ise.drp.utils;

import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.apache.jakarta.log4j.AppenderRefComplexType;
import org.apache.jakarta.log4j.Configuration;
import org.apache.jakarta.log4j.Configuration.Category;
import org.apache.jakarta.log4j.PriorityComplexType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.service.DrpPropertyServiceImpl;

/**
 * Initializes log4j during application context initialization. Recreates log4j
 * whenever there is update for log4j property in DB
 * 
 * @author a175157
 */
@Service("loggerUtilService")
public class LoggerUtilServiceImpl implements LoggerUtilSerivce {

    private static Logger logger = LoggerFactory.getLogger(LoggerUtilServiceImpl.class);
    @Autowired
    private DrpPropertyServiceImpl drpPropertyService;
    private static final String LOG4J_CATEGORIES = "log4j.categories";
    private static final String LOG4J_CATEGORY = "log4j.category";
    private String filePath;

    @Override
    public void createLog4jFromDB() {
	try{
	    logger.info("Intialize log4j from DB - Start");
	    String log4jFromDBFlag = drpPropertyService.getProperty(DrpConstants.LOG4J_FROM_DB, "FALSE");
	    if (!log4jFromDBFlag.equalsIgnoreCase("TRUE")){
		logger.info("Proerty value LOG4J_FROM_DB:" + log4jFromDBFlag);
		logger.info("Default WEB-INF/classes/log4j.xml will be used");
		return;
	    }
	    Configuration configuration = new Configuration();
	    addStaticContent(configuration);
	    addCategories(configuration);
	    addRoot(configuration);
	    File file = new File(filePath);
	    logger.debug("log4j path:" + file.getAbsolutePath());
	    FileUtils.writeStringToFile(file, addLog4jDTD(marshalToXML(configuration)));

	}catch(Exception e){
	    logger.error("Unable to create log4j.xml from Database. Default WEB-INF/classes/log4j.xml will be used", e);
	}
	logger.info("Intialize log4j from DB - End");
    }

    private String addLog4jDTD(String marshaledXML) {
	StringBuffer sb = new StringBuffer();
	sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
	sb.append("<!DOCTYPE log4j:configuration SYSTEM \"log4j.dtd\">\n");
	sb.append(marshaledXML.replaceAll("ns2", "log4j"));
	return sb.toString();
    }

    private void addRoot(Configuration configuration) throws Exception {
	Configuration.Root root = new Configuration.Root();
	try{
	    PriorityComplexType priority = new PriorityComplexType();
	    String priorityVal = drpPropertyService.getProperty("log4j.root.priority", "info");
	    priority.setValue(priorityVal);
	    root.setPriority(priority);

	    String appenders = drpPropertyService.getProperty("log4j.root.appenders", "ApplicationLogAppender");
	    StringTokenizer stringTokenizer = new StringTokenizer(appenders, ",");
	    while(stringTokenizer.hasMoreTokens()){
		addAppender(stringTokenizer.nextToken(), root.getAppenderRef());
	    }
	}catch(Exception e){
	    logger.error("Error occured while adding root from DB", e);
	    throw e;
	}
	configuration.setRoot(root);
    }

    private void addCategories(Configuration configuration) {
	try{
	    String categories = drpPropertyService.getProperty(LOG4J_CATEGORIES, null);
	    if (categories == null){
		logger.info("There are no log4j categories added in database");
		return;
	    }
	    StringTokenizer stringTokenizer = new StringTokenizer(categories, ",");
	    while(stringTokenizer.hasMoreElements()){
		String category = stringTokenizer.nextToken();
		addCategory(category, configuration.getCategory());
	    }
	}catch(Exception e){
	    logger.error("Error in adding categories from database", e);
	}
    }

    private void addCategory(String categoryStr, List<Category> categoryList) {
	Configuration.Category category = new Configuration.Category();
	try{
	    String categoryName = drpPropertyService.getProperty(LOG4J_CATEGORY + "." + categoryStr + ".name", null);
	    if (categoryName == null){
		logger.error("Property missing in DB:" + LOG4J_CATEGORY + "." + categoryStr + ".name");
		return;
	    }
	    category.setName(categoryName);
	    String additivity = drpPropertyService.getProperty(LOG4J_CATEGORY + "." + categoryStr + ".additivity",
		    "false");
	    category.setAdditivity(Boolean.parseBoolean(additivity));

	    PriorityComplexType priority = new PriorityComplexType();

	    String priorityVal = drpPropertyService.getProperty(LOG4J_CATEGORY + "." + categoryStr + ".priority",
		    "info");
	    priority.setValue(priorityVal);
	    category.setPriority(priority);

	    String appenders = drpPropertyService.getProperty(LOG4J_CATEGORY + "." + categoryStr + ".appenders",
		    "ApplicationLogAppender");
	    StringTokenizer stringTokenizer = new StringTokenizer(appenders, ",");
	    while(stringTokenizer.hasMoreTokens()){
		addAppender(stringTokenizer.nextToken(), category.getAppenderRef());
	    }
	}catch(Exception e){
	    logger.error("Error in added category:" + categoryStr, e);
	    return;
	}
	categoryList.add(category);
    }

    private void addAppender(String appenderRefVal, List<AppenderRefComplexType> appenderRef) {
	AppenderRefComplexType catAppendRef = new AppenderRefComplexType();
	catAppendRef.setRef(appenderRefVal);
	appenderRef.add(catAppendRef);
    }

    private void addStaticContent(Configuration configuration) {

	List<Configuration.Appender> appenderList = configuration.getAppender();

	Configuration.Appender appLogAppender = new Configuration.Appender();
	appLogAppender.setName("ApplicationLogAppender");
	appLogAppender.setClazz("org.apache.log4j.DailyRollingFileAppender");
	List<Configuration.Appender.Param> paramList = appLogAppender.getParam();
	Configuration.Appender.Param appLogAppenderParam1 = new Configuration.Appender.Param();
	appLogAppenderParam1.setName("Append");
	appLogAppenderParam1.setValue("true");
	paramList.add(appLogAppenderParam1);
	Configuration.Appender.Param appLogAppenderParam2 = new Configuration.Appender.Param();
	appLogAppenderParam2.setName("DatePattern");
	appLogAppenderParam2.setValue("'.'yyyy-MM-dd");
	paramList.add(appLogAppenderParam2);
	Configuration.Appender.Param appLogAppenderParam3 = new Configuration.Appender.Param();
	appLogAppenderParam3.setName("File");
	appLogAppenderParam3.setValue("${logFilePath}/ISEApplication.Log");
	paramList.add(appLogAppenderParam3);
	Configuration.Appender.Layout appLogAppenderLayout = new Configuration.Appender.Layout();
	appLogAppenderLayout.setClazz("org.apache.log4j.PatternLayout");
	Configuration.Appender.Layout.Param appLogAppenderLayoutParam1 = new Configuration.Appender.Layout.Param();
	appLogAppenderLayoutParam1.setName("ConversionPattern");
	appLogAppenderLayoutParam1.setValue("%d{yyyy.MM.dd HH:mm:ss:SSS} %X{sessionId} %5p %-20c{1} > %m%n");
	appLogAppenderLayout.setParam(appLogAppenderLayoutParam1);
	appLogAppender.setLayout(appLogAppenderLayout);
	appenderList.add(appLogAppender);

	Configuration.Appender caLogAppender = new Configuration.Appender();
	caLogAppender.setName("CA");
	caLogAppender.setClazz("org.apache.log4j.ConsoleAppender");
	Configuration.Appender.Layout caLogAppenderLayout = new Configuration.Appender.Layout();
	caLogAppenderLayout.setClazz("org.apache.log4j.PatternLayout");
	Configuration.Appender.Layout.Param caLogAppenderLayoutParam1 = new Configuration.Appender.Layout.Param();
	caLogAppenderLayoutParam1.setName("ConversionPattern");
	caLogAppenderLayoutParam1.setValue("%-4r [%t] %-5p %c %x - %m%n");
	caLogAppenderLayout.setParam(caLogAppenderLayoutParam1);
	caLogAppender.setLayout(caLogAppenderLayout);
	appenderList.add(caLogAppender);
    }

    private String marshalToXML(Object object) throws JAXBException {
	if (object == null){
	    return "";
	}
	try{
	    // now do some magic to convert the object hierarchy to XML
	    JAXBContext jc = JAXBContext.newInstance(object.getClass().getPackage().getName());
	    Marshaller m = jc.createMarshaller();
	    m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
	    StringWriter sw = new StringWriter();
	    m.marshal(object, sw);
	    return sw.toString();
	}catch(JAXBException je){
	    logger.error("Error in marshalling object", je);
	    throw je;
	}
    }

    @Override
    public void setLog4jPath(String filePath) {
	this.filePath = filePath;
    }
}
