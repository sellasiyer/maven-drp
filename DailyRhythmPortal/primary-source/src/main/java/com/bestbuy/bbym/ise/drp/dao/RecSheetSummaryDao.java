package com.bestbuy.bbym.ise.drp.dao;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bestbuy.bbym.ise.drp.domain.RecSheetSummary;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * Interface for REC_SHEET_SUMMARY DB object
 * 
 * @author Sridhar Savaram
 * @version
 * @since
 */
public interface RecSheetSummaryDao {

    /**
     * 
     * @param recSheetSummary
     */
    public void persistRecSheetSummary(RecSheetSummary recSheetSummary) throws DataAccessException, RuntimeException;

    public RecSheetSummary getRecSheetSummary(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException;

    public void deleteRecSheetSummary(String dataSharingKey) throws DataAccessException, RuntimeException;

    public void updateRecSheetSummary(RecSheetSummary recSheetSummary) throws DataAccessException, RuntimeException;
}
