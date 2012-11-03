/**
 * 
 */
package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

/**
 * @author a186288
 *
 */
public class OptInResponse implements Serializable {

    private static final long serialVersionUID = 6002741158957388661L;
    private int optInCount;

    public OptInResponse(int count) {
	optInCount = count;
    }

    /**
     * @param optInCount the optInCount to set
     */
    public void setOptInCount(int optInCount) {
	this.optInCount = optInCount;
    }

    /**
     * @return the optInCount
     */
    public int getOptInCount() {
	return optInCount;
    }

}
