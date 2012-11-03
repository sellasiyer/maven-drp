package com.bestbuy.bbym.ise.drp.service;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.dao.BbyAccountDao;
import com.bestbuy.bbym.ise.drp.dao.DataTransferDao;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.sao.OpenSSOSao;

/**
 * JUnit test for {@link AuthenticationServiceImpl}.
 * 
 * @author a915726
 */
public class AuthenticationServiceImplTest {

    private AuthenticationServiceImpl service;
    private DrpPropertyService mockDrpPropertyService;
    private BbyAccountDao mockBbyAccountDao;
    private DataTransferDao mockDataTransferDao;
    private OpenSSOSao mockOpenSSOSao;

    @Before
    public void setUp() {
	service = new AuthenticationServiceImpl();
	mockDrpPropertyService = createStrictMock(DrpPropertyService.class);
	mockBbyAccountDao = createStrictMock(BbyAccountDao.class);
	mockDataTransferDao = createStrictMock(DataTransferDao.class);
	mockOpenSSOSao = createStrictMock(OpenSSOSao.class);
	service.setDrpPropertyService(mockDrpPropertyService);
	service.setBbyAccountDao(mockBbyAccountDao);
	service.setDataTransferDao(mockDataTransferDao);
	service.setOpenSSOSao(mockOpenSSOSao);
    }

    /**
     * Test {@link AuthenticationServiceImpl#getAuthenticateUser(String, String)}.
     */
    @Test
    public void testGetAuthenticatedUser() throws Exception {
	Map<String, String> cookieNameValueMap = new HashMap<String, String>();

	cookieNameValueMap.put(OpenSSOSao.COOKIE_NAME_OBLIX, "");
	cookieNameValueMap.put(OpenSSOSao.COOKIE_NAME_OPENSSO, "");

	DrpUser drpUser = new DrpUser();

	String oblixCookie = cookieNameValueMap.get(OpenSSOSao.COOKIE_NAME_OBLIX);
	String openSsoCookie = cookieNameValueMap.get(OpenSSOSao.COOKIE_NAME_OPENSSO);

	expect(mockOpenSSOSao.attributes(oblixCookie, openSsoCookie)).andReturn(drpUser);
	replay(mockOpenSSOSao);

	DrpUser expectResult = service.getAuthenticatedUser(cookieNameValueMap);
	assertEquals(drpUser, expectResult);
    }

    /**
     * Test {@link AuthenticationServiceImpl#authenticateUser(String, String, String, boolean)}.
     */
    @Ignore
    @Test
    public void testAuthenticateSharingKey() throws Exception {

	String dataSharingKey = "dataSharingKey";
	String storeId = "storeId";
	String userId = "a1234";
	boolean isGspCancel = true;
	BbyAccount account = new BbyAccount();

	expect(mockBbyAccountDao.getBbyAccount(dataSharingKey, storeId, userId)).andReturn(account);
	replay(mockBbyAccountDao);

	boolean expectResult = service.authenticateSharingKey(dataSharingKey, userId, storeId, isGspCancel);
    }

    /**
     * Test {@link AuthenticationServiceImpl#authenticateSharingKey(String, String, String, boolean)}.
     */
    @Test
    public void testAuthenticateSharingKeyNonGsp() throws Exception {

	String dataSharingKey = "dataSharingKey";
	String storeId = "storeId";
	String userId = "a1234";
	boolean isGspCancel = false;
	Date createdTime = new Date();

	expect(mockDataTransferDao.getDataTransferCreatedTime(dataSharingKey, storeId, userId)).andReturn(createdTime);
	replay(mockDataTransferDao);

	boolean expectResult = service.authenticateSharingKey(dataSharingKey, userId, storeId, isGspCancel);
	assertTrue(expectResult);
    }

}
