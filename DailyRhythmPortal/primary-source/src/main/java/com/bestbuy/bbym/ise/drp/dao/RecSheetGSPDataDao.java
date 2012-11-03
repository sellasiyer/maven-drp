/**
 * 
 */
package com.bestbuy.bbym.ise.drp.dao;

import java.util.Map;

import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * @author a175157
 *
 */
public interface RecSheetGSPDataDao {

    public Map<String, String> getGSPData(String essTypeId, int term, float price) throws DataAccessException;

    public Map<String, String> getGSPPrice(float price) throws DataAccessException;

    public String getGSPSKU(String type, int term, float price) throws DataAccessException;
}
