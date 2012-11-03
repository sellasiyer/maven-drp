package com.bestbuy.bbym.ise.drp.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardStoreType;
import com.bestbuy.bbym.ise.drp.sao.OpenSSOSaoImpl.IseLdapRole;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("authenticationService2")
public class DummyAuthenticationService implements AuthenticationService {

    @Override
    public DrpUser getAuthenticatedUser(Map<String, String> cookieNameValueMap) throws ServiceException,
	    IseBusinessException {
	return null;
    }

    @Override
    public DrpUser authenticateUser(String userName, String password) throws ServiceException, IseBusinessException {
	return getDrpUser(userName);
    }

    @Override
    public boolean authenticateSharingKey(String dataSharingKey, String userId, String storeId, boolean isGspCancel) {
	return true;
    }

    private DrpUser getDrpUser(String userName) {
	DrpUser drpUser = new DrpUser();
	drpUser.setUserId(userName);
	drpUser.setFirstName("FirstName");
	drpUser.setFirstName("LastName");
	Store store = new Store();
	store.setId("0699");
	store.setStoreName("Lake Wobegon");
	store.setStoreType(ScoreboardStoreType.SWAS);
	drpUser.setStore(store);
	if ("ShippingUser".equalsIgnoreCase(userName)){
	    drpUser.getApplicationRoles().add(IseLdapRole.SHIPPING_USER.getIseRole());
	}else{
	    drpUser.getApplicationRoles().add(IseLdapRole.ADMININSTRATOR.getIseRole());
	}
	return drpUser;
    }

}
