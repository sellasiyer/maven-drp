package com.bestbuy.bbym.ise.drp.domain;

/**
 * @author a218635
 */
import java.io.Serializable;

import com.bestbuy.bbym.ise.domain.User;

public class DrpUser extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String openSsoTokenId;

    // this was added to facilitate SSO workaround for BEAST. It should be
    // removed when the real SSO solution is in place
    private boolean beast;

    public String getOpenSsoTokenId() {
	return openSsoTokenId;
    }

    public void setOpenSsoTokenId(String openSsoTokenId) {
	this.openSsoTokenId = openSsoTokenId;
    }

    public void setBeast(boolean beast) {
	this.beast = beast;
    }

    public boolean isBeast() {
	return beast;
    }

}
