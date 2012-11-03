/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.bestbuy.bbym.ise.drp.dao.TriageDao;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.TriageAction;
import com.bestbuy.bbym.ise.drp.domain.TriageEvent;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;
import com.bestbuy.bbym.ise.drp.domain.TriageRecommendation;
import com.bestbuy.bbym.ise.drp.domain.TriageResolution;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 21
 */
public class TriageServiceImplTest {

    private TriageServiceImpl service;
    private TriageDao triageDao;

    @Before
    public void setUp() throws Exception {
	service = new TriageServiceImpl();

	triageDao = EasyMock.createStrictMock(TriageDao.class);
	ReflectionTestUtils.setField(service, "triageDao", triageDao);
    }

    @Test
    public void testGetIssueList() throws ServiceException {
	List<TriageIssue> expect = new ArrayList<TriageIssue>();
	expect(triageDao.getTriageIssueActiveList()).andReturn(expect);
	replay(triageDao);
	List<TriageIssue> actual = service.getIssueList();
	assertEquals(expect, actual);
	verify(triageDao);
    }

    @Test(expected = ServiceException.class)
    public void testGetIssueListWithException() throws ServiceException {
	List<TriageIssue> expect = new ArrayList<TriageIssue>();
	expect(triageDao.getTriageIssueActiveList()).andThrow(new RuntimeException("Test exception"));
	replay(triageDao);
	List<TriageIssue> actual = service.getIssueList();
    }

    @Test
    public void testGetRecommendationTriageIssueString() throws ServiceException {

	String sku = "sku";
	TriageIssue triageIssue = new TriageIssue();
	TriageRecommendation rcmd = new TriageRecommendation();
	expect(triageDao.getTriageRecommendationBySku(eq(triageIssue), eq(sku))).andReturn(rcmd);
	replay(triageDao);
	TriageRecommendation actual = service.getRecommendation(triageIssue, sku);
	assertEquals(rcmd, actual);
	verify(triageDao);
    }

    @Test(expected = ServiceException.class)
    public void testGetRecommendationTriageIssueStringWithException() throws ServiceException {

	String sku = "sku";
	TriageIssue triageIssue = new TriageIssue();
	TriageRecommendation rcmd = new TriageRecommendation();
	expect(triageDao.getTriageRecommendationBySku(eq(triageIssue), eq(sku))).andThrow(
		new RuntimeException("Test exception"));
	replay(triageDao);
	TriageRecommendation actual = service.getRecommendation(triageIssue, sku);
    }

    @Test
    public void testGetRecommendationTriageIssue() throws ServiceException {

	TriageIssue triageIssue = new TriageIssue();
	TriageRecommendation rcmd = new TriageRecommendation();
	expect(triageDao.getTriageRecommendationBySku(eq(triageIssue), isNull(String.class))).andReturn(rcmd);
	replay(triageDao);
	TriageRecommendation actual = service.getRecommendation(triageIssue);
	assertEquals(rcmd, actual);
	verify(triageDao);
    }

    @Test
    public void testGetActions() throws ServiceException {
	TriageRecommendation rcmd = new TriageRecommendation();
	List<TriageAction> list = new ArrayList<TriageAction>();
	expect(triageDao.getTriageActionActiveListByRcmd(eq(rcmd))).andReturn(list);
	replay(triageDao);
	List actual = service.getActions(rcmd);
	assertEquals(list, actual);
	verify(triageDao);
    }

    @Test(expected = ServiceException.class)
    public void testGetActionsWithException() throws ServiceException {
	TriageRecommendation rcmd = new TriageRecommendation();
	List<TriageAction> list = new ArrayList<TriageAction>();
	expect(triageDao.getTriageActionActiveListByRcmd(eq(rcmd))).andThrow(new RuntimeException("Test exception"));
	replay(triageDao);
	List actual = service.getActions(rcmd);
    }

    @Test
    public void testAddTriageEvent() throws ServiceException {
	TriageEvent triageEvent = new TriageEvent();
	DrpUser user = new DrpUser();
	expect(triageDao.persist(eq(triageEvent))).andReturn(triageEvent);
	replay(triageDao);
	TriageEvent actual = service.addTriageEvent(triageEvent, user);
	assertEquals(triageEvent, actual);
	verify(triageDao);
    }

    @Test(expected = ServiceException.class)
    public void testAddTriageEventWithException() throws ServiceException {
	TriageEvent triageEvent = new TriageEvent();
	DrpUser user = new DrpUser();
	expect(triageDao.persist(eq(triageEvent))).andThrow(new RuntimeException("Test exception"));
	replay(triageDao);
	TriageEvent actual = service.addTriageEvent(triageEvent, user);
    }

    @Test
    public void testDeleteIssueComment() throws ServiceException {
	TriageEvent triageEvent = new TriageEvent();
	triageEvent.setIssueComment("issueComment");
	DrpUser user = new DrpUser();
	user.setUserId("userId");
	expect(triageDao.update(eq(triageEvent))).andReturn(triageEvent);
	replay(triageDao);
	TriageEvent actual = service.deleteIssueComment(triageEvent, user);
	assertEquals(triageEvent, actual);
	assertEquals(null, actual.getIssueComment());
	assertEquals("userId", actual.getModifiedBy());
    }

    @Test(expected = ServiceException.class)
    public void testDeleteIssueCommentWithException() throws ServiceException {
	TriageEvent triageEvent = new TriageEvent();
	triageEvent.setIssueComment("issueComment");
	DrpUser user = new DrpUser();
	user.setUserId("userId");
	expect(triageDao.update(eq(triageEvent))).andThrow(new RuntimeException("Test exception"));
	replay(triageDao);
	TriageEvent actual = service.deleteIssueComment(triageEvent, user);
    }

    @Test
    public void testDeleteResolutionComment() throws ServiceException {
	TriageEvent triageEvent = new TriageEvent();
	triageEvent.setResolutionComment("resolutionComment");
	DrpUser user = new DrpUser();
	user.setUserId("userId");
	expect(triageDao.update(eq(triageEvent))).andReturn(triageEvent);
	replay(triageDao);
	TriageEvent actual = service.deleteResolutionComment(triageEvent, user);
	assertEquals(triageEvent, actual);
	assertEquals(null, actual.getResolutionComment());
	assertEquals("userId", actual.getModifiedBy());
    }

    @Test(expected = ServiceException.class)
    public void testDeleteResolutionCommentWithException() throws ServiceException {
	TriageEvent triageEvent = new TriageEvent();
	triageEvent.setResolutionComment("resolutionComment");
	DrpUser user = new DrpUser();
	user.setUserId("userId");
	expect(triageDao.update(eq(triageEvent))).andThrow(new RuntimeException("Test exception"));
	replay(triageDao);
	TriageEvent actual = service.deleteResolutionComment(triageEvent, user);
    }

    @Test
    public void testGetResolutionList() throws ServiceException {
	List<TriageResolution> list = new ArrayList<TriageResolution>();
	expect(triageDao.getTriageResolutionActiveList()).andReturn(list);
	replay(triageDao);
	List<TriageResolution> actual = service.getResolutionList();
	assertEquals(list, actual);
	verify(triageDao);
    }

    @Test(expected = ServiceException.class)
    public void testGetResolutionListWithException() throws ServiceException {
	List<TriageResolution> list = new ArrayList<TriageResolution>();
	expect(triageDao.getTriageResolutionActiveList()).andThrow(new RuntimeException("Test exception"));
	replay(triageDao);
	List<TriageResolution> actual = service.getResolutionList();
    }

    @Test
    public void testGetTriageHistoryBySerialNumber() throws ServiceException {
	String serialNo = "serialNo";
	List<TriageEvent> expected = new ArrayList<TriageEvent>();
	expect(triageDao.getTriageEventByDeviceSerialNo(eq(serialNo))).andReturn(expected);
	replay(triageDao);
	List<TriageEvent> actual = service.getTriageHistoryBySerialNumber(serialNo);
	assertEquals(expected, actual);
	verify(triageDao);
    }

    @Test(expected = ServiceException.class)
    public void testGetTriageHistoryBySerialNumberWithException() throws ServiceException {
	String serialNo = "serialNo";
	List<TriageEvent> expected = new ArrayList<TriageEvent>();
	expect(triageDao.getTriageEventByDeviceSerialNo(eq(serialNo))).andThrow(new RuntimeException("Test exception"));
	replay(triageDao);
	List<TriageEvent> actual = service.getTriageHistoryBySerialNumber(serialNo);
    }

}
