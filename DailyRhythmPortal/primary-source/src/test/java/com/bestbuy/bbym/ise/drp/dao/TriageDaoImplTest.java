package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.TriageAction;
import com.bestbuy.bbym.ise.drp.domain.TriageEvent;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;
import com.bestbuy.bbym.ise.drp.domain.TriageRecommendation;
import com.bestbuy.bbym.ise.drp.domain.TriageResolution;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;

public class TriageDaoImplTest extends BaseDaoTest {

    TriageDaoImpl dao = new TriageDaoImpl();

    @Before
    public void setUp() throws Exception {
	dao.setUseNextSequenceOnCreate(true);
	dao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	dao.setJdbcTemplate(new JdbcTemplate(db));
	DatabaseScript.execute("ise_db_scripts/add_triage_data.sql", db);
    }

    private static TriageAction buildTriageAction() {
	TriageAction triageAction = new TriageAction();
	triageAction.setId(null);
	triageAction.setAction("action");
	triageAction.setActiveFlag(true);
	triageAction.setCreatedBy("created_by");
	java.sql.Date date = new java.sql.Date(new Date().getTime());
	triageAction.setCreatedOn(date);
	triageAction.setModifiedBy("amended_by");
	triageAction.setModifiedOn(date);
	return triageAction;
    }

    @Test
    public void testPersistAction() {
	TriageAction action = buildTriageAction();
	action = dao.persist(action);
	assertNotNull(action.getId());
    }

    @Test
    public void testUpdateAction() {
	TriageAction action = buildTriageAction();
	action = dao.persist(action);
	assertNotNull(action.getId());

	action.setAction("new action");
	action.setActiveFlag(false);
	action.setModifiedBy("modifiedBy1");
	dao.update(action);
    }

    @Test
    public void testDeleteAction() {
	TriageAction action = buildTriageAction();
	action = dao.persist(action);
	assertNotNull(action.getId());

	dao.delete(action);
    }

    @Test
    public void testGetTriageActionActiveListByRcmd() {
	TriageRecommendation rcmd = new TriageRecommendation();
	rcmd.setId(21L);
	List<TriageAction> list = dao.getTriageActionActiveListByRcmd(rcmd);
	assertEquals(2, list.size());

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String currentDate = formatter.format(new Date());

	assertEquals("TriageAction[id=4,action=Action4,activeFlag=true,createdOn=" + currentDate + ",modifiedOn="
		+ currentDate + ",createdBy=a1003810,modifiedBy=a1003810]", ReflectionToStringBuilder.toString(list
		.get(0), ToStringStyle.SHORT_PREFIX_STYLE));
	assertEquals("TriageAction[id=6,action=Action6,activeFlag=true,createdOn=" + currentDate + ",modifiedOn="
		+ currentDate + ",createdBy=a1003810,modifiedBy=a1003810]", ReflectionToStringBuilder.toString(list
		.get(1), ToStringStyle.SHORT_PREFIX_STYLE));
    }

    private static TriageEvent buildTriageEvent() {
	java.sql.Date date = new java.sql.Date(new Date().getTime());
	TriageResolution resolution = new TriageResolution();
	resolution.setId(21L);
	TriageRecommendation recommendation = new TriageRecommendation();
	recommendation.setId(21L);
	TriageEvent triageEvent = new TriageEvent(date, date, "createdBy", "modifiedBy", null, resolution,
		recommendation, "serialno", "issue comment", "resolution comment", "GPSid", "techchecker", "benefit");
	return triageEvent;
    }

    @Test
    public void testPersistTriageEvent() throws DataAccessException {
	TriageEvent triageEvent = buildTriageEvent();
	triageEvent = dao.persist(triageEvent);
	assertNotNull(triageEvent.getId());

	TriageEvent actual = dao.getTriageEventByDeviceSerialNo("serialno").get(0);
	triageEvent.setModifiedBy(null);
	triageEvent.setModifiedOn(null);
	assertEquals(triageEvent.getTriageRecommendation().getId(), actual.getTriageRecommendation().getId());
	assertEquals(triageEvent.getTriageResolution().getId(), actual.getTriageResolution().getId());
	actual.setTriageRecommendation(triageEvent.getTriageRecommendation());
	actual.setTriageResolution(triageEvent.getTriageResolution());
	assertEquals(ReflectionToStringBuilder.toString(triageEvent, ToStringStyle.SHORT_PREFIX_STYLE),
		ReflectionToStringBuilder.toString(actual, ToStringStyle.SHORT_PREFIX_STYLE));
    }

    @Test
    public void testUpdateTriageEvent() {
	TriageEvent triageEvent = buildTriageEvent();
	triageEvent = dao.persist(triageEvent);

	java.sql.Date date = new java.sql.Date(new Date().getTime());
	//triageEvent.setCreatedBy("a100");
	//triageEvent.setCreatedOn(date);
	triageEvent.setDeviceSerialNo("newserial1");
	triageEvent.setIssueComment("newcomment");
	triageEvent.setModifiedBy("a200");
	triageEvent.setModifiedOn(date);
	triageEvent.setProtectionPlanId("newplan");
	triageEvent.setResolutionComment("newrcomment");
	triageEvent.setTechCheckerIssues("newtech");

	TriageRecommendation triageRecommendation = new TriageRecommendation();
	triageRecommendation.setId(22L);
	triageEvent.setTriageRecommendation(triageRecommendation);
	TriageResolution triageResolution = new TriageResolution();
	triageResolution.setId(22L);
	triageEvent.setTriageResolution(triageResolution);

	dao.update(triageEvent);

	List<TriageEvent> list = dao.getTriageEventByDeviceSerialNo("newserial1");
	TriageEvent actual = list.get(0);
	assertEquals(triageEvent.getTriageRecommendation().getId(), actual.getTriageRecommendation().getId());
	assertEquals(triageEvent.getTriageResolution().getId(), actual.getTriageResolution().getId());
	actual.setTriageRecommendation(triageEvent.getTriageRecommendation());
	actual.setTriageResolution(triageEvent.getTriageResolution());
	assertEquals(ReflectionToStringBuilder.toString(triageEvent, ToStringStyle.SHORT_PREFIX_STYLE),
		ReflectionToStringBuilder.toString(actual, ToStringStyle.SHORT_PREFIX_STYLE));
    }

    @Test
    public void testDeleteTriageEvent() {
	TriageEvent triageEvent = buildTriageEvent();
	triageEvent.setDeviceSerialNo("delete");
	triageEvent = dao.persist(triageEvent);

	TriageEvent actual = dao.getTriageEventByDeviceSerialNo("delete").get(0);
	assertNotNull(actual);

	dao.delete(triageEvent);

	List list = dao.getTriageEventByDeviceSerialNo("delete");
	assertEquals(0, list.size());
    }

    private static TriageIssue buildTriageIssue() {
	TriageIssue triageIssue = new TriageIssue();
	triageIssue.setId(null);
	triageIssue.setIssueDesc("issu_desc");
	triageIssue.setTooltip("tooltip");
	triageIssue.setDisplayOrder(1);
	triageIssue.setActiveFlag(true);
	triageIssue.setCreatedBy("created_by");
	java.sql.Date date = new java.sql.Date(new Date().getTime());
	triageIssue.setCreatedOn(date);
	triageIssue.setModifiedBy("amended_by");
	triageIssue.setModifiedOn(date);
	return triageIssue;
    }

    @Test
    public void testPersistTriageIssue() {
	TriageIssue triageIssue = buildTriageIssue();
	triageIssue = dao.persist(triageIssue);

	TriageIssue actual = dao.getTriageIssueActiveList().get(0);
	triageIssue.setModifiedBy(null);
	triageIssue.setModifiedOn(null);
	assertEquals(ReflectionToStringBuilder.toString(triageIssue, ToStringStyle.SHORT_PREFIX_STYLE),
		ReflectionToStringBuilder.toString(actual, ToStringStyle.SHORT_PREFIX_STYLE));

    }

    @Test
    public void testUpdateTriageIssue() {
	TriageIssue triageIssue = buildTriageIssue();
	triageIssue = dao.persist(triageIssue);

	triageIssue.setIssueDesc("issu_desc1");
	triageIssue.setTooltip("tooltip1");
	triageIssue.setDisplayOrder(2);
	triageIssue.setActiveFlag(true);
	//triageIssue.setCreatedBy("created_by1");
	java.sql.Date date = new java.sql.Date(new Date().getTime());
	//triageIssue.setCreatedOn(date);
	triageIssue.setModifiedBy("amended_by1");
	triageIssue.setModifiedOn(date);

	triageIssue = dao.update(triageIssue);

	TriageIssue actual = dao.getTriageIssueActiveList().get(0);
	assertEquals(ReflectionToStringBuilder.toString(triageIssue, ToStringStyle.SHORT_PREFIX_STYLE),
		ReflectionToStringBuilder.toString(actual, ToStringStyle.SHORT_PREFIX_STYLE));
    }

    @Test
    public void testDeleteTriageIssue() {
	TriageIssue triageIssue = buildTriageIssue();
	triageIssue = dao.persist(triageIssue);

	TriageIssue actual = dao.getTriageIssueActiveList().get(0);
	assertEquals(triageIssue.getId().intValue(), actual.getId().intValue());

	dao.delete(triageIssue);

	actual = dao.getTriageIssueActiveList().get(0);
	assertNotSame(triageIssue.getId().intValue(), actual.getId().intValue());
    }

    @Test
    public void testGetTriageIssueActiveList() {
	List<TriageIssue> list = dao.getTriageIssueActiveList();
	assertEquals(21, list.get(0).getDisplayOrder());
	assertEquals(22, list.get(1).getDisplayOrder());
	assertEquals(2, list.size());
    }

    @Test
    public void testGetTriageRecommendationBySku() {
	TriageIssue trIssue = new TriageIssue();
	trIssue.setId(21L);
	TriageRecommendation rcmd = dao.getTriageRecommendationBySku(trIssue, "1");
	assertEquals(21, rcmd.getId().intValue());

	trIssue.setId(22L);
	rcmd = dao.getTriageRecommendationBySku(trIssue, "1");
	assertEquals(22, rcmd.getId().intValue());

	rcmd = dao.getTriageRecommendationBySku(trIssue, "2");
	assertEquals(22, rcmd.getId().intValue());

	rcmd = dao.getTriageRecommendationBySku(trIssue, null);
	assertEquals(22, rcmd.getId().intValue());

	trIssue.setId(3L);
	rcmd = dao.getTriageRecommendationBySku(trIssue, "1");
	assertEquals(null, rcmd);

	rcmd = dao.getTriageRecommendationBySku(trIssue, null);
	assertEquals(null, rcmd);
    }

    @Test
    public void testPersistTriageResolution() {
	TriageResolution rsln = buildTriageResolution();
	rsln = dao.persist(rsln);
	assertNotNull(rsln.getId());

	TriageResolution actual = dao.getTriageResolutionActiveList().get(0);
	rsln.setModifiedBy(null);
	rsln.setModifiedOn(null);
	assertEquals(ReflectionToStringBuilder.toString(rsln, ToStringStyle.SHORT_PREFIX_STYLE),
		ReflectionToStringBuilder.toString(actual, ToStringStyle.SHORT_PREFIX_STYLE));
    }

    private TriageResolution buildTriageResolution() {
	TriageResolution rsln = new TriageResolution();
	rsln.setActiveFlag(true);
	rsln.setCreatedBy("createdBy");
	java.sql.Date date = new java.sql.Date(new Date().getTime());
	rsln.setCreatedOn(date);
	rsln.setDisplayOrder(1);
	rsln.setId(null);
	rsln.setModifiedBy(null);
	rsln.setModifiedOn(null);
	rsln.setResolutionDesc("resolutionDesc");
	return rsln;
    }

    @Test
    public void testUpdateTriageResolution() {
	TriageResolution rsln = buildTriageResolution();
	rsln = dao.persist(rsln);

	rsln.setActiveFlag(true);
	java.sql.Date date = new java.sql.Date(new Date().getTime());
	rsln.setDisplayOrder(0);
	rsln.setModifiedBy("modifiedBy1");
	rsln.setModifiedOn(date);
	rsln.setResolutionDesc("resolutionDesc1");

	rsln = dao.update(rsln);

	TriageResolution actual = dao.getTriageResolutionActiveList().get(0);
	assertEquals(ReflectionToStringBuilder.toString(rsln, ToStringStyle.SHORT_PREFIX_STYLE),
		ReflectionToStringBuilder.toString(actual, ToStringStyle.SHORT_PREFIX_STYLE));
    }

    @Test
    public void testDeleteTriageResolution() {
	TriageResolution rsln = buildTriageResolution();
	rsln = dao.persist(rsln);
	assertNotNull(rsln.getId());

	List<TriageResolution> list = dao.getTriageResolutionActiveList();
	assertEquals(3, list.size());

	dao.delete(rsln);
	list = dao.getTriageResolutionActiveList();
	assertEquals(2, list.size());
    }

    @Test
    public void testGetTriageResolutionActiveList() {
	TriageResolution rsln1 = buildTriageResolution();
	rsln1.setActiveFlag(true);
	rsln1.setDisplayOrder(1);
	rsln1 = dao.persist(rsln1);

	TriageResolution rsln2 = buildTriageResolution();
	rsln2.setActiveFlag(false);
	rsln2.setDisplayOrder(2);
	rsln2 = dao.persist(rsln2);

	TriageResolution rsln3 = buildTriageResolution();
	rsln3.setActiveFlag(true);
	rsln3.setDisplayOrder(3);
	rsln2 = dao.persist(rsln3);

	List<TriageResolution> list = dao.getTriageResolutionActiveList();
	assertEquals(4, list.size());
	assertEquals(ReflectionToStringBuilder.toString(rsln1, ToStringStyle.SHORT_PREFIX_STYLE),
		ReflectionToStringBuilder.toString(list.get(0), ToStringStyle.SHORT_PREFIX_STYLE));
	assertEquals(ReflectionToStringBuilder.toString(rsln3, ToStringStyle.SHORT_PREFIX_STYLE),
		ReflectionToStringBuilder.toString(list.get(1), ToStringStyle.SHORT_PREFIX_STYLE));
    }

}
