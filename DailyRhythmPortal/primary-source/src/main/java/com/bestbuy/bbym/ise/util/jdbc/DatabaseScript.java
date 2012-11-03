package com.bestbuy.bbym.ise.util.jdbc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bestbuy.bbym.ise.exception.DataAccessException;

public class DatabaseScript {

    private static Logger logger = LoggerFactory.getLogger(DatabaseScript.class);

    private DatabaseScript() {
    }

    public static void execute(String sqlFile, DataSource dataSource) throws DataAccessException {
	String error;
	logger.debug("Executing SQL file " + sqlFile);
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	BufferedReader fis = null;
	String line, cmd;
	StringBuilder command = null;
	int lineNum = 0;
	try{
	    fis = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(sqlFile)));
	    while(fis.ready()){
		lineNum++;
		line = fis.readLine();
		if (line == null)
		    break;
		line.trim();
		if (line.endsWith(";")){
		    if (command == null){
			command = new StringBuilder();
		    }else{
			command.append(" ");
		    }
		    command.append(line.substring(0, line.length() - 1));
		    cmd = StringUtils.trim(command.toString());
		    if (matches(cmd, "select")){
			logger.trace("SQL " + lineNum + " E " + cmd);
			jdbcTemplate.execute(command.toString());
		    }else{
			logger.trace("SQL " + lineNum + " U " + cmd);
			jdbcTemplate.update(command.toString());
		    }
		    command = null;
		}else{
		    if (command == null){
			command = new StringBuilder();
		    }else{
			command.append(" ");
		    }
		    command.append(line);
		}
	    }
	}catch(FileNotFoundException fnfe){
	    error = "Failed to execute SQL command on line " + lineNum + " for file " + sqlFile;
	    logger.error(error, fnfe);
	    throw new DataAccessException(error);
	}catch(IOException ioe){
	    error = "Failed to execute SQL command on line " + lineNum + " for file " + sqlFile;
	    logger.error(error, ioe);
	    throw new DataAccessException(error);
	}catch(Exception e){
	    error = "Failed to execute SQL command on line " + lineNum + " for file " + sqlFile;
	    logger.error(error, e);
	    throw new DataAccessException(error);
	}finally{
	    try{
		if (fis != null)
		    fis.close();
	    }catch(IOException ioe){
		logger.warn("IOException closing file " + ioe.getMessage());
	    }
	}
    }

    private static boolean matches(String command, String operation) {
	if (command == null || operation == null){
	    return false;
	}
	if (command.length() > operation.length()){
	    return command.substring(0, operation.length()).equalsIgnoreCase(operation);
	}
	return false;
    }
}
