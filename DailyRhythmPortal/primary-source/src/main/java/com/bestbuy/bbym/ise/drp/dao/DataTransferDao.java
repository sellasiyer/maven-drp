package com.bestbuy.bbym.ise.drp.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * Interface for DATA_TRANSFER DB Object
 * 
 * @author Sridhar Savaram
 * @version 1.0
 * @since
 */
public interface DataTransferDao {

    public void persistDataTransfer(String dataSharingKey, String storeId, String userId, boolean transferFlag,
	    String sourceSystem, String capTransId, String workFlowType) throws DataAccessException, RuntimeException;

    public List<DataTransferSummary> getDataTransferSummaryList(String storeId) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException;

    public DataTransferSummary getDataTransferSummary(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException;

    public void updateTransferFlag(String dataSharingKey) throws DataAccessException, RuntimeException;

    public void updateAmmendedOn(String dataSharingKey) throws DataAccessException, RuntimeException;

    public void update(String dataSharingKey, String capTransId, String modifiedBy) throws DataAccessException,
	    RuntimeException;

    public Date getDataTransferCreatedTime(String dataSharingKey, String storeId, String userId)
	    throws DataAccessException, EmptyResultDataAccessException, RuntimeException;

    DataTransferSummary getDataTransferRow(String dataSharingKey) throws DataAccessException;
}
