/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.dao;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.Config;

/**
 * Interface for configuration database operations.
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 24
 */
public interface ConfigDao {

    final static String TRIAGE_SCREEN_REPAIR_SKU = "TRIAGE_SCREEN_REPAIR_SKU";

    /**
     * Inserts new configuration object to repository.
     * @param config configuration object
     * @return returns inserted configuration object
     */
    Config persist(Config config);

    /**
     * Updates existing configuration object on repository.
     * @param config configuration object
     * @return returns updated configuration object
     */
    Config update(Config config);

    /**
     * Deletes configuration object from repository.
     * @param config configuration object
     */
    void delete(Config config);

    /**
     * Returns list of configuration objects for given configuration type.
     * @param configType configuration type
     * @return returns list of configuration objects
     */
    List<Config> getConfigItemsByType(String configType);

    /**
     * Returns list of configuration objects for given configuration type and parameter name.
     * @param configType configuration type
     * @param paramName parameter name
     * @return returns list of configuration objects
     */
    List<Config> getConfigItemsByTypeAndParamName(String configType, String paramName);

    /**
     * Returns list of configuration objects for given configuration type and parameter value.
     * @param configType configuration type
     * @param paramName parameter value
     * @return returns list of configuration objects
     */
    List<Config> getConfigItemsByTypeAndParamValue(String configType, String paramValue);

    /**
     * Returns configuration object for given configuration id
     * @param configId
     * @return returns single configuration object
     */
    Config getConfigItemById(String configId);

    /**
     * Returns list of configuration objects for given configuration type.
     * @param configType configuration type
     * @param orderByValue if true then orders list by param_value otherwise by param_name. 
     * @return returns list of configuration objects
     */
    List<Config> getConfigItemsByType(String configType, boolean orderByValue);
}
