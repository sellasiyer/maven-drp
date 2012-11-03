/**
 * Best Buy Inc (c) 2011
 */

package com.bestbuy.bbym.ise.drp.triage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.drp.workflow.triage.TriageWorkflowState;
import com.bestbuy.bbym.ise.drp.workflow.triage.TriageWorkflowState.PROBLEM_TYPE;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;
import com.bestbuy.bbym.ise.workflow.rules.DialogItem;
import com.bestbuy.bbym.ise.workflow.rules.DialogSelectItem;
import com.bestbuy.bbym.ise.workflow.rules.WorkflowDialog;
import org.junit.*;

/**
 * Tests Drools Workflow Service Object
 * 
 * @author Animesh Banerjee
 */
@Ignore
public class TriageRulesTest {

    static KnowledgeBase kbase;
    StatefulKnowledgeSession session;
    TriageWorkflowState state;
    WorkflowDialog dialog;
    DialogItem dialogItem;

    // KnowledgeRuntimeLogger logger;

    @BeforeClass
    public static void readKnowledgeBase() throws Exception {
	KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	kbuilder.add(ResourceFactory.newClassPathResource("triage.drl"), ResourceType.DRL);
	KnowledgeBuilderErrors errors = kbuilder.getErrors();
	if (errors.size() > 0){
	    for(KnowledgeBuilderError error: errors){
		System.err.println(error);
	    }
	    throw new IllegalArgumentException("Could not parse knowledge.");
	}
	kbase = KnowledgeBaseFactory.newKnowledgeBase();
	kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    }

    @Before
    public void setup() throws ServiceException {
	// setup the audit logging
	session = kbase.newStatefulKnowledgeSession();
	// logger = KnowledgeRuntimeLoggerFactory.newFileLogger(session,
	// "c:/temp/log/triage.log");

	state = new TriageWorkflowState();
	state.setDevice(new Device());
	dialog = new Dialog();
	state.setDialog(dialog);

    }

    @After
    public void teardown() {
	session.dispose();
	state = null;
	// logger.close();
    }

    /**
     * Entry point into triage workflow. Takes user to first screen
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    // if it takes longer, there is likely a problem with the rules (e.g.
    // infinite loop)
    public void testGoToScreen1() throws ServiceException {
	session = kbase.newStatefulKnowledgeSession();
	session.insert(state);
	session.fireAllRules();
	verifyState(1, "issue_type", Arrays.asList("physical_damage", "battery_issue", "software_issue",
		"hardware_issue"), null, null);
    }

    /******************************************************************************
     * PHYSICAL DAMAGE TESTS *
     ******************************************************************************/

    /**
     * Customer device has physical damage but don't know if she has Geek Squad
     * Black Tie Protection plan
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen101from1() throws ServiceException {
	setupState(1, null, "issue_type", "physical_damage", null, null);
	session.fireAllRules();
	verifyState(101, "do_u_have_black_tie", PROBLEM_TYPE.PHYSICAL_DAMAGE);
    }

    /**
     * Customer device has physical damage and we know she does not have Geek
     * Squad Black Tie Protection plan
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen102() throws ServiceException {
	setupState(1, null, "issue_type", "physical_damage", null, false);
	session.fireAllRules();
	verifyState(102, "no_gsbtp_next_steps", Arrays.asList("eligible_upgrade", "purchase_replacement", "other"),
		PROBLEM_TYPE.PHYSICAL_DAMAGE, false);
    }

    /**
     * Customer device has physical damage and she has just told us she does not
     * have Geek Squad Black Tie Protection plan
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen102from101() throws ServiceException {
	setupState(101, PROBLEM_TYPE.PHYSICAL_DAMAGE, "do_u_have_black_tie", "N", null);
	session.fireAllRules();
	verifyState(102, "no_gsbtp_next_steps", Arrays.asList("eligible_upgrade", "purchase_replacement", "other"),
		PROBLEM_TYPE.PHYSICAL_DAMAGE, false);
    }

    /**
     * Customer device has physical damage, does not have Geek Squad Protection
     * and she has just told us which option to pursue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from102() throws ServiceException {
	setupState(102, PROBLEM_TYPE.PHYSICAL_DAMAGE, "no_gsbtp_next_steps", "eligible_upgrade", null);
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /**
     * Customer device has physical damage and we know she has Geek Squad Black
     * Tie Protection plan
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen103() throws ServiceException {
	state.setStep(1);
	state.getDevice().setBlackTieProtection(true);

	dialogItem = dialog.addDialogSelectItem("issue_type");
	dialogItem.setAnswer("physical_damage");
	session.insert(state);

	session.fireAllRules();
	assertEquals(103, state.getStep());

	// the problem type should be set
	assertEquals(TriageWorkflowState.PROBLEM_TYPE.PHYSICAL_DAMAGE, state.getIssue());

	// only one question is expected
	assertEquals(1, state.getDialog().getDialogItems().size());

	// and it should contain the following question
	assertEquals("gsbtp_next_steps", (state.getDialog().getDialogItems().get(0)).getName());
    }

    /**
     * Customer device has physical damage and she has just told us she has Geek
     * Squad Black Tie Protection plan
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen103from101() throws ServiceException {
	state.setStep(101);
	state.setIssue(TriageWorkflowState.PROBLEM_TYPE.PHYSICAL_DAMAGE);
	dialogItem = dialog.addDialogSelectItem("do_u_have_black_tie");
	dialogItem.setAnswer("Y");
	session.insert(state);

	session.fireAllRules();
	assertEquals(103, state.getStep());

	// only one question is expected
	assertEquals(1, state.getDialog().getDialogItems().size());

	// make sure device protection status has been updated
	assertTrue("expecting device GSBTP flag to be true", state.getDevice().getBlackTieProtection());

	// and it should contain the following question
	assertEquals("gsbtp_next_steps", (state.getDialog().getDialogItems().get(0)).getName());
    }

    /******************************************************************************
     * SOFTWARE ISSUE TESTS *
     ******************************************************************************/

    /**
     * Customer device has battery issue but don't know device OS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen201Batt() throws ServiceException {
	state.setStep(1);
	state.getDevice().setOs(null);

	dialogItem = dialog.addDialogSelectItem("issue_type");
	dialogItem.setAnswer("battery_issue");
	session.insert(state);

	session.fireAllRules();
	assertEquals(201, state.getStep());

	// the problem type should be set
	assertEquals(TriageWorkflowState.PROBLEM_TYPE.BATTERY_ISSUE, state.getIssue());

	// only one question is expected
	assertEquals(1, state.getDialog().getDialogItems().size());
	assertEquals("os_type", state.getDialog().getDialogItems().get(0).getName());

	// and it should contain the following options
	ArrayList<String> expectedOptions = new ArrayList<String>(Arrays.asList("ios", "blackberry", "android",
		"windows", "other"));
	assertEquals(expectedOptions, ((DialogSelectItem) (state.getDialog().getDialogItems().get(0))).getOptions());
    }

    /**
     * Customer device has battery issue and we know the OS of the device to be
     * iOS a priori
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen203iOS() throws ServiceException {
	state.setStep(1);
	state.getDevice().setOs("ios");

	dialogItem = dialog.addDialogSelectItem("issue_type");
	dialogItem.setAnswer("battery_issue");
	session.insert(state);

	session.fireAllRules();
	assertEquals(203, state.getStep());

	// the problem type should be set
	assertEquals(TriageWorkflowState.PROBLEM_TYPE.BATTERY_ISSUE, state.getIssue());

	// only one question is expected
	assertEquals(1, state.getDialog().getDialogItems().size());

	// and it should contain the following question
	assertEquals("have_battery_save_settings", (state.getDialog().getDialogItems().get(0)).getName());
    }

    /**
     * Customer device has battery issue and we have just been told the OS of
     * the device is iOS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen203from201() throws ServiceException {
	setupState(201, PROBLEM_TYPE.BATTERY_ISSUE, "os_type", "ios", null);
	session.fireAllRules();
	verifyState(203, "have_battery_save_settings", PROBLEM_TYPE.BATTERY_ISSUE);

	// make sure os has been updated as specified
	assertEquals("ios", state.getDevice().getOs());
    }

    /**
     * Customer device has battery issue and we know the OS of the device to be
     * other than iOS a priori
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen202() throws ServiceException {
	setupState(1, null, "issue_type", "battery_issue", "android", null);
	session.fireAllRules();
	verifyState(202, "battery_condition_choices", Arrays.asList("battery_physical_damage",
		"liquid_damage_indicator", "battery_no_physical_damage"), PROBLEM_TYPE.BATTERY_ISSUE, null);
    }

    /**
     * Customer device has battery issue and we have just been told the OS of
     * the device is other than iOS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen202from201() throws ServiceException {
	setupState(201, PROBLEM_TYPE.BATTERY_ISSUE, "os_type", "blackberry", null);
	session.fireAllRules();
	verifyState(202, "battery_condition_choices", Arrays.asList("battery_physical_damage",
		"liquid_damage_indicator", "battery_no_physical_damage"), PROBLEM_TYPE.BATTERY_ISSUE, null);

	// make sure os has been updated as specified
	assertEquals("blackberry", state.getDevice().getOs());
    }

    /**
     * Customer device has battery issue, non-iOS operating system and battery
     * has physical damage. We know a priori she has Geek Squad Protection.
     * 
     * @throws ServiceException
     */
    @Test
    // (timeout = 5000)
    public void testGotoScreen211from202() throws ServiceException {
	setupState(202, PROBLEM_TYPE.BATTERY_ISSUE, "battery_condition_choices", "battery_physical_damage", "android",
		true);
	session.fireAllRules();
	verifyState(211, "gsbtp_battery_options");
    }

    /**
     * Customer device has battery issue, non-iOS operating system, and battery
     * has physical damage. We know a priori she does not have Geek Squad
     * Protection.
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGotoScreen212from202() throws ServiceException {
	setupState(202, PROBLEM_TYPE.BATTERY_ISSUE, "battery_condition_choices", "liquid_damage_indicator", "android",
		false);
	session.fireAllRules();
	verifyState(212, "no_gsbtp_next_steps", Arrays.asList("eligible_upgrade", "purchase_replacement",
		"purchase_new_battery", "other"), PROBLEM_TYPE.BATTERY_ISSUE, false);
    }

    /**
     * Customer device has battery issue, non iOS operating system and no
     * visible signs of battery damage
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen204() throws ServiceException {
	state.setStep(202);
	state.getDevice().setOs("android");
	state.setIssue(PROBLEM_TYPE.BATTERY_ISSUE);
	dialogItem = dialog.addDialogItem("battery_condition_choices");
	dialogItem.setAnswer("battery_no_physical_damage");
	session.insert(state);

	session.fireAllRules();
	assertEquals(204, state.getStep());

	// the problem type should be set
	assertEquals(TriageWorkflowState.PROBLEM_TYPE.BATTERY_ISSUE, state.getIssue());

	// only one question is expected
	assertEquals(1, state.getDialog().getDialogItems().size());

	// and it should contain the following question
	assertEquals("have_battery_save_settings", (state.getDialog().getDialogItems().get(0)).getName());
    }

    /**
     * Customer device has battery issues, is non iOS, and we have confirmed
     * physical damage. We don't know if device has Geek Squad Protection
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen101from202a() throws ServiceException {
	setupState(202, PROBLEM_TYPE.BATTERY_ISSUE, "battery_condition_choices", "battery_physical_damage", "windows");
	session.fireAllRules();
	verifyState(101, "do_u_have_black_tie");
    }

    /**
     * Customer device has battery issues, is non iOS, and we have confirmed
     * liquid_damage_indicator has been tripped. We don't know if device has
     * Geek Squad Protection
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen101from202b() throws ServiceException {
	setupState(202, PROBLEM_TYPE.BATTERY_ISSUE, "battery_condition_choices", "liquid_damage_indicator", "other");
	session.fireAllRules();
	verifyState(101, "do_u_have_black_tie");
    }

    /**
     * Customer device has battery issue, non iOS operating system and no
     * visible signs of battery damage, and battery saving settings are in place
     * We don't know if she has Geek Squad Protection however
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen101From204() throws ServiceException {
	state.setStep(204);
	state.getDevice().setOs("blackberry");
	state.setIssue(PROBLEM_TYPE.BATTERY_ISSUE);
	state.getDevice().setBlackTieProtection(null);
	dialogItem = dialog.addDialogItem("have_battery_save_settings");
	dialogItem.setAnswer("Y");
	session.insert(state);

	session.fireAllRules();
	assertEquals(101, state.getStep());

	// only one question is expected
	assertEquals(1, state.getDialog().getDialogItems().size());

	// and it should contain the following question
	assertEquals("do_u_have_black_tie", (state.getDialog().getDialogItems().get(0)).getName());
    }

    /**
     * Customer device has battery issue, iOS operating system and battery
     * saving settings are in place We don't know if she has Geek Squad
     * Protection however.
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGotoScreen101from203() throws ServiceException {
	state.setStep(203);
	state.getDevice().setOs("ios");
	state.setIssue(PROBLEM_TYPE.BATTERY_ISSUE);
	state.getDevice().setBlackTieProtection(null);
	dialogItem = dialog.addDialogItem("have_battery_save_settings_iphone");
	dialogItem.setAnswer("Y");
	session.insert(state);

	session.fireAllRules();
	assertEquals(101, state.getStep());

	// only one question is expected
	assertEquals(1, state.getDialog().getDialogItems().size());

	// and it should contain the following question
	assertEquals("do_u_have_black_tie", (state.getDialog().getDialogItems().get(0)).getName());
    }

    /**
     * Customer device has battery issue, battery saving settings are not in
     * place, and device is iOS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen207from203() throws ServiceException {
	setupState(203, PROBLEM_TYPE.BATTERY_ISSUE, "have_battery_save_settings_iphone", "N", "ios");
	session.fireAllRules();
	verifyState(207, "adjust_battery_save_settings");
    }

    /**
     * Customer device has battery issue, battery saving settings are not in
     * place, and device is not iOS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen207from204() throws ServiceException {
	setupState(204, PROBLEM_TYPE.BATTERY_ISSUE, "have_battery_save_settings", "N", "blackberry");
	session.fireAllRules();
	verifyState(207, "adjust_battery_save_settings");
    }

    /**
     * Customer device has battery issue, iOS operating system and battery
     * saving settings are in place. She has just told us she has Geek Squad
     * Protection.
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGotoScreen208from101() throws ServiceException {
	state.setStep(101);
	state.getDevice().setOs("ios");
	state.setIssue(PROBLEM_TYPE.BATTERY_ISSUE);
	dialogItem = dialog.addDialogItem("do_u_have_black_tie");
	dialogItem.setAnswer("Y");
	session.insert(state);

	session.fireAllRules();
	assertEquals(208, state.getStep());

	// only one question is expected
	assertEquals(1, state.getDialog().getDialogItems().size());

	// and it should contain the following question
	assertEquals("gsbtp_next_steps", (state.getDialog().getDialogItems().get(0)).getName());
    }

    /**
     * Customer device has battery issue, non-iOS operating system and battery
     * saving settings are in place. She has just told us she has Geek Squad
     * Protection.
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGotoScreen211from101() throws ServiceException {
	state.setStep(101);
	state.getDevice().setOs("windows");
	state.setIssue(PROBLEM_TYPE.BATTERY_ISSUE);
	dialogItem = dialog.addDialogItem("do_u_have_black_tie");
	dialogItem.setAnswer("Y");
	session.insert(state);

	session.fireAllRules();
	assertEquals(211, state.getStep());

	// only one question is expected
	assertEquals(1, state.getDialog().getDialogItems().size());

	// and it should contain the following question
	assertEquals("gsbtp_battery_options", (state.getDialog().getDialogItems().get(0)).getName());
    }

    /**
     * Customer device has battery issue, iOS operating system and battery
     * saving settings are in place. She has just told us she does not have Geek
     * Squad Protection.
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGotoScreen209from101() throws ServiceException {
	setupState(101, PROBLEM_TYPE.BATTERY_ISSUE, "do_u_have_black_tie", "N", "ios", null);
	session.fireAllRules();
	verifyState(209, "no_gsbtp_next_steps", Arrays.asList("eligible_upgrade", "purchase_replacement", "other"),
		PROBLEM_TYPE.BATTERY_ISSUE, false);
    }

    /**
     * Customer device has battery issue, non-iOS operating system and battery
     * saving settings are in place. She has just told us she does not have Geek
     * Squad Protection.
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGotoScreen212from101() throws ServiceException {
	setupState(101, PROBLEM_TYPE.BATTERY_ISSUE, "do_u_have_black_tie", "N", "other", null);
	session.fireAllRules();
	verifyState(212, "no_gsbtp_next_steps", Arrays.asList("eligible_upgrade", "purchase_replacement",
		"purchase_new_battery", "other"), PROBLEM_TYPE.BATTERY_ISSUE, false);
    }

    /**
     * Customer device has battery issue, iOS operating system and battery
     * saving settings are in place. We know a priori she has Geek Squad
     * Protection.
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGotoScreen208() throws ServiceException {
	setupState(203, PROBLEM_TYPE.BATTERY_ISSUE, "have_battery_save_settings_iphone", "Y", "ios", true);
	session.fireAllRules();
	verifyState(208, "gsbtp_next_steps");
    }

    /**
     * Customer device has battery issue, non-iOS operating system and battery
     * saving settings are in place. We know a priori she has Geek Squad
     * Protection.
     * 
     * @throws ServiceException
     */
    @Test
    // (timeout = 5000)
    public void testGotoScreen211from204() throws ServiceException {
	setupState(204, PROBLEM_TYPE.BATTERY_ISSUE, "have_battery_save_settings", "Y", "windows", true);
	session.fireAllRules();
	verifyState(211, "gsbtp_battery_options");
    }

    /**
     * Customer device has battery issue, iOS operating system and battery
     * saving settings are in place. We know a priori she does not have Geek
     * Squad Protection.
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGotoScreen209() throws ServiceException {
	setupState(203, PROBLEM_TYPE.BATTERY_ISSUE, "have_battery_save_settings_iphone", "Y", "ios", false);
	session.fireAllRules();
	verifyState(209, "no_gsbtp_next_steps", Arrays.asList("eligible_upgrade", "purchase_replacement", "other"),
		PROBLEM_TYPE.BATTERY_ISSUE, false);
    }

    /**
     * Customer device has battery issue, non-iOS operating system and battery
     * saving settings are in place. We know a priori she does not have Geek
     * Squad Protection.
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGotoScreen212from204() throws ServiceException {
	setupState(204, PROBLEM_TYPE.BATTERY_ISSUE, "have_battery_save_settings", "Y", "android", false);
	session.fireAllRules();
	verifyState(212, "no_gsbtp_next_steps", Arrays.asList("eligible_upgrade", "purchase_replacement",
		"purchase_new_battery", "other"), PROBLEM_TYPE.BATTERY_ISSUE, false);
    }

    /**
     * Customer device has battery issue, is an iOS, does not have Geek Squad
     * Protection and she has just told us which option to pursue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from209() throws ServiceException {
	setupState(209, PROBLEM_TYPE.BATTERY_ISSUE, "no_gsbtp_battery_options_ios", "eligible_upgrade");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /**
     * Customer device has battery issue, is not an iOS, does not have Geek
     * Squad Protection and she has just told us which option to pursue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from212() throws ServiceException {
	setupState(212, PROBLEM_TYPE.BATTERY_ISSUE, "no_gsbtp_battery_options", "purchase_new_battery");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /******************************************************************************
     * SOFTWARE ISSUE TESTS *
     ******************************************************************************/

    /**
     * Customer device has software issue but don't know device OS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen201Sw() throws ServiceException {
	setupState(1, null, "issue_type", "software_issue");
	session.fireAllRules();
	verifyState(201, "os_type", Arrays.asList("ios", "blackberry", "android", "windows", "other"),
		PROBLEM_TYPE.SOFTWARE_ISSUE, null);
    }

    /**
     * Customer device has software issue and we know the OS of the device a
     * priori
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen305() throws ServiceException {
	setupState(1, null, "issue_type", "software_issue", "android");
	session.fireAllRules();
	verifyState(305, "have_updates_been_applied");
	assertEquals(PROBLEM_TYPE.SOFTWARE_ISSUE, state.getIssue());
    }

    /**
     * Customer device has software issue and we have just been told the OS of
     * the device
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen305from201() throws ServiceException {
	setupState(201, PROBLEM_TYPE.SOFTWARE_ISSUE, "os_type", "blackberry", null);
	session.fireAllRules();
	verifyState(305, "have_updates_been_applied", "blackberry");
    }

    /**
     * Customer device has software issue, the latest software updates have not
     * been applied and device OS is iOS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen315IOS() throws ServiceException {
	setupState(305, PROBLEM_TYPE.SOFTWARE_ISSUE, "have_updates_been_applied", "N", "ios");
	session.fireAllRules();
	verifyState(315, "ios_update_instructions");
    }

    /**
     * Customer device has software issue, the latest software updates have not
     * been applied and device OS is android
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen315Android() throws ServiceException {
	setupState(305, PROBLEM_TYPE.SOFTWARE_ISSUE, "have_updates_been_applied", "N", "android");
	session.fireAllRules();
	verifyState(315, "android_update_instructions");
    }

    /**
     * Customer device has software issue, the latest software updates have not
     * been applied and device OS is blackberry
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen315Blackberry() throws ServiceException {
	setupState(305, PROBLEM_TYPE.SOFTWARE_ISSUE, "have_updates_been_applied", "N", "blackberry");
	session.fireAllRules();
	verifyState(315, "blackberry_update_instructions");
    }

    /**
     * Customer device has software issue, the latest software updates have not
     * been applied and device OS is windows
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen315Windows() throws ServiceException {
	setupState(305, PROBLEM_TYPE.SOFTWARE_ISSUE, "have_updates_been_applied", "N", "windows");
	session.fireAllRules();
	verifyState(315, "windows_update_instructions");
    }

    /**
     * Customer device has software issue, the latest software updates have not
     * been applied and device OS is other
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen315Other() throws ServiceException {
	setupState(305, PROBLEM_TYPE.SOFTWARE_ISSUE, "have_updates_been_applied", "N", "other");
	session.fireAllRules();
	verifyState(315, "generic_update_instructions");
    }

    /**
     * Customer device has software issue, the latest software updates have not
     * fixed the problem, and OS is iOS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen310IOS() throws ServiceException {
	setupState(315, PROBLEM_TYPE.SOFTWARE_ISSUE, "ios_update_instructions", "N", "ios");
	session.fireAllRules();
	verifyState(310, "ios_soft_reset_instructions");
    }

    /**
     * Customer device has software issue, the latest software updates have not
     * fixed the problem, and OS is android
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen310Android() throws ServiceException {
	setupState(315, PROBLEM_TYPE.SOFTWARE_ISSUE, "android_update_instructions", "N", "android");
	session.fireAllRules();
	verifyState(310, "generic_soft_reset_instructions");
    }

    /**
     * Customer device has software issue, the latest software updates have not
     * fixed the problem, and OS is blackberry
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen310Blackberry() throws ServiceException {
	setupState(315, PROBLEM_TYPE.SOFTWARE_ISSUE, "blackberry_update_instructions", "N", "blackberry");
	session.fireAllRules();
	verifyState(310, "generic_soft_reset_instructions");
    }

    /**
     * Customer device has software issue, the latest software updates have not
     * fixed the problem, and OS is windows
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen310Windows() throws ServiceException {
	setupState(315, PROBLEM_TYPE.SOFTWARE_ISSUE, "windows_update_instructions", "N", "windows");
	session.fireAllRules();
	verifyState(310, "generic_soft_reset_instructions");
    }

    /**
     * Customer device has software issue, the latest software updates have not
     * fixed the problem, and OS is other
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen310Other() throws ServiceException {
	setupState(315, PROBLEM_TYPE.SOFTWARE_ISSUE, "generic_update_instructions", "N", "other");
	session.fireAllRules();
	verifyState(310, "generic_soft_reset_instructions");
    }

    /**
     * Customer device has software issue, the latest software updates have
     * resolved the issue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from315() throws ServiceException {
	setupState(315, PROBLEM_TYPE.SOFTWARE_ISSUE, "generic_update_instructions", "Y", "other");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /**
     * Customer device has software issue and a soft reset has resolved the
     * issue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from310() throws ServiceException {
	setupState(310, PROBLEM_TYPE.SOFTWARE_ISSUE, "generic_soft_reset_instructions", "Y", "other");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /**
     * Customer device has software issue and a soft reset has failed to
     * resolved the issue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen320() throws ServiceException {
	setupState(310, PROBLEM_TYPE.SOFTWARE_ISSUE, "generic_soft_reset_instructions", "N", "other");
	session.fireAllRules();
	verifyState(320, "hard_reset_permission");
    }

    /**
     * Customer wants a hard reset and and device OS is iOS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen330IOS() throws ServiceException {
	setupState(320, PROBLEM_TYPE.SOFTWARE_ISSUE, "hard_reset_permission", "Y", "ios");
	session.fireAllRules();
	verifyState(330, "ios_hard_reset_instructions");
    }

    /**
     * Customer wants a hard reset and device OS is android
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen330Android() throws ServiceException {
	setupState(320, PROBLEM_TYPE.SOFTWARE_ISSUE, "hard_reset_permission", "Y", "android");
	session.fireAllRules();
	verifyState(330, "android_hard_reset_instructions");
    }

    /**
     * Customer wants a hard reset and device OS is blackberry
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen330Blackberry() throws ServiceException {
	setupState(320, PROBLEM_TYPE.SOFTWARE_ISSUE, "hard_reset_permission", "Y", "blackberry");
	session.fireAllRules();
	verifyState(330, "blackberry_hard_reset_instructions");
    }

    /**
     * Customer wants a hard reset and device OS is windows
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen330Windows() throws ServiceException {
	setupState(320, PROBLEM_TYPE.SOFTWARE_ISSUE, "hard_reset_permission", "Y", "windows");
	session.fireAllRules();
	verifyState(330, "windows_hard_reset_instructions");
    }

    /**
     * Customer wants a hard reset and device OS is other
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen330Other() throws ServiceException {
	setupState(320, PROBLEM_TYPE.SOFTWARE_ISSUE, "hard_reset_permission", "Y", "other");
	session.fireAllRules();
	verifyState(330, "generic_hard_reset_instructions");
    }

    /**
     * Customer device has software issue and a hard reset has resolved the
     * issue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from330() throws ServiceException {
	setupState(330, PROBLEM_TYPE.SOFTWARE_ISSUE, "windows_hard_reset_instructions", "Y", "windows");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /**
     * Customer device has software issue, a hard reset has failed to resolve
     * the issue, and we know device has Geek Squad Protection a priori
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen350() throws ServiceException {
	setupState(330, PROBLEM_TYPE.SOFTWARE_ISSUE, "windows_hard_reset_instructions", "N", "other", true);
	session.fireAllRules();
	verifyState(350, "gsbtp_next_steps");
    }

    /**
     * Customer device has software issue, a hard reset has failed to resolve
     * the issue, and we know device does not have Geek Squad Protection a
     * priori
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen335from330() throws ServiceException {
	setupState(330, PROBLEM_TYPE.SOFTWARE_ISSUE, "windows_hard_reset_instructions", "N", "other", false);
	session.fireAllRules();
	verifyState(335, "check_device_manufacturer_warranty");
    }

    /**
     * Customer device has software issue, does not want a hard reset, and we
     * know device does not have Geek Squad Protection a priori
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen335from320() throws ServiceException {
	setupState(320, PROBLEM_TYPE.SOFTWARE_ISSUE, "hard_reset_permission", "N", "other", false);
	session.fireAllRules();
	verifyState(335, "check_device_manufacturer_warranty");
    }

    /**
     * Customer device has software issue, a hard reset has failed to resolve
     * the issue, and we don't know if device has Geek Squad Protection
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen101from330() throws ServiceException {
	setupState(330, PROBLEM_TYPE.SOFTWARE_ISSUE, "windows_hard_reset_instructions", "N", "other");
	session.fireAllRules();
	verifyState(101, "do_u_have_black_tie");
    }

    /**
     * Customer device has software issue and does not want a hard reset. We
     * don't know if device has Geek Squad Protection
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen101from320() throws ServiceException {
	setupState(320, PROBLEM_TYPE.SOFTWARE_ISSUE, "hard_reset_permission", "N", "ios");
	session.fireAllRules();
	verifyState(101, "do_u_have_black_tie");
    }

    /**
     * Customer device has software issue, a hard reset has failed to resolve
     * the issue or she does not want to attempt a hard reset, and has just told
     * us she has Geek Squad Protection
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen350from101() throws ServiceException {
	setupState(101, PROBLEM_TYPE.SOFTWARE_ISSUE, "do_u_have_black_tie", "Y", "other");
	session.fireAllRules();
	verifyState(350, "gsbtp_next_steps", true);
    }

    /**
     * Customer device has software issue, a hard reset has failed to resolve
     * the issue or she does not want to attempt a hard reset, and has just told
     * us she does not have have Geek Squad Protection
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen335from101() throws ServiceException {
	setupState(101, PROBLEM_TYPE.SOFTWARE_ISSUE, "do_u_have_black_tie", "N", "other");
	session.fireAllRules();
	verifyState(335, "check_device_manufacturer_warranty", false);
    }

    /**
     * Customer device has software issue, device is not under manufacturer
     * warranty
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen340() throws ServiceException {
	setupState(335, PROBLEM_TYPE.SOFTWARE_ISSUE, "check_device_manufacturer_warranty", "N", "windows", false);
	session.fireAllRules();
	verifyState(340, "no_gsbtp_next_steps", Arrays.asList("eligible_upgrade", "purchase_replacement", "other"),
		PROBLEM_TYPE.SOFTWARE_ISSUE, false);
    }

    /**
     * Customer device has software issue, is not under manufacturer warranty,
     * and she has just told us which option to pursue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from340() throws ServiceException {
	setupState(340, PROBLEM_TYPE.SOFTWARE_ISSUE, "out_of_warranty_options", "other");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /**
     * Customer device has software issue, device is under manufacturer warranty
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen345() throws ServiceException {
	setupState(335, PROBLEM_TYPE.SOFTWARE_ISSUE, "check_device_manufacturer_warranty", "Y", "windows", false);
	session.fireAllRules();
	verifyState(345, "no_gsbtp_next_steps", Arrays.asList("eligible_upgrade", "purchase_replacement",
		"send_to_manufacturer_for_repair", "other"), PROBLEM_TYPE.SOFTWARE_ISSUE, false);
    }

    /**
     * Customer device has software issue, is under manufacturer warranty, and
     * she has just told us which option to pursue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from345() throws ServiceException {
	setupState(340, PROBLEM_TYPE.SOFTWARE_ISSUE, "warranty_options", "send_to_manufacturer_for_repair");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /******************************************************************************
     * HARDWARE ISSUE TESTS *
     ******************************************************************************/
    /**
     * Customer device has hardware issue but don't know device OS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen201Hw() throws ServiceException {
	state.setStep(1);
	state.getDevice().setOs(null);

	dialogItem = dialog.addDialogSelectItem("issue_type");
	dialogItem.setAnswer("hardware_issue");
	session.insert(state);

	session.fireAllRules();
	assertEquals(201, state.getStep());

	// the problem type should be set
	assertEquals(TriageWorkflowState.PROBLEM_TYPE.HARDWARE_ISSUE, state.getIssue());

	// only one question is expected
	assertEquals(1, state.getDialog().getDialogItems().size());
	assertEquals("os_type", state.getDialog().getDialogItems().get(0).getName());

	// and it should contain the following options
	ArrayList<String> expectedOptions = new ArrayList<String>(Arrays.asList("ios", "blackberry", "android",
		"windows", "other"));
	assertEquals(expectedOptions, ((DialogSelectItem) (state.getDialog().getDialogItems().get(0))).getOptions());
    }

    /**
     * Customer device has hardware issue and we know the OS of the device is
     * android
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen405() throws ServiceException {
	setupState(1, null, "issue_type", "hardware_issue", "android");
	session.fireAllRules();
	verifyState(405, "run_hardware_checker_app");
	assertEquals(PROBLEM_TYPE.HARDWARE_ISSUE, state.getIssue());
    }

    /**
     * Customer device has hardware issue and we have just been told the OS of
     * the device is android
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen405from201() throws ServiceException {
	setupState(201, PROBLEM_TYPE.HARDWARE_ISSUE, "os_type", "android", null);
	session.fireAllRules();
	verifyState(405, "run_hardware_checker_app", "android");
    }

    /**
     * Customer device has hardware issue and hardware checker app could not
     * confirm the problem
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen410() throws ServiceException {
	setupState(405, PROBLEM_TYPE.HARDWARE_ISSUE, "run_hardware_checker_app", "N", "android");
	session.fireAllRules();
	verifyState(410, "has_hardware_checker_fixed_issue");
    }

    /**
     * Customer device has hardware issue and hardware checker app could not
     * confirm or fix the problem
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen305from410() throws ServiceException {
	setupState(410, PROBLEM_TYPE.HARDWARE_ISSUE, "has_hardware_checker_fixed_issue", "Y", "android");
	session.fireAllRules();
	verifyState(305, "have_updates_been_applied");
    }

    /**
     * Customer device has hardware issue that hardware checker app could not
     * confirm but fixed the problem all the same
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from410() throws ServiceException {
	setupState(410, PROBLEM_TYPE.HARDWARE_ISSUE, "has_hardware_checker_fixed_issue", "N", "android");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /**
     * Customer device has hardware issue and we know the OS of the device a
     * priori to be other than android
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen305Hw() throws ServiceException {
	setupState(1, null, "issue_type", "hardware_issue", "ios");
	session.fireAllRules();
	verifyState(305, "have_updates_been_applied");
	assertEquals(PROBLEM_TYPE.HARDWARE_ISSUE, state.getIssue());
    }

    /**
     * Customer device has hardware issue and we have just been told the OS of
     * the device is other than android
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen305from201Hw() throws ServiceException {
	setupState(201, PROBLEM_TYPE.HARDWARE_ISSUE, "os_type", "blackberry", null);
	session.fireAllRules();
	verifyState(305, "have_updates_been_applied", "blackberry");
    }

    /**
     * Customer device has hardware issue, the latest software updates have not
     * been applied and device OS is iOS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen315IOSHw() throws ServiceException {
	setupState(305, PROBLEM_TYPE.HARDWARE_ISSUE, "have_updates_been_applied", "N", "ios");
	session.fireAllRules();
	verifyState(315, "ios_update_instructions");
    }

    /**
     * Customer device has hardware issue, the latest software updates have not
     * been applied and device OS is android
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen315AndroidHw() throws ServiceException {
	setupState(305, PROBLEM_TYPE.HARDWARE_ISSUE, "have_updates_been_applied", "N", "android");
	session.fireAllRules();
	verifyState(315, "android_update_instructions");
    }

    /**
     * Customer device has hardware issue, the latest software updates have not
     * been applied and device OS is blackberry
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen315BlackberryHw() throws ServiceException {
	setupState(305, PROBLEM_TYPE.HARDWARE_ISSUE, "have_updates_been_applied", "N", "blackberry");
	session.fireAllRules();
	verifyState(315, "blackberry_update_instructions");
    }

    /**
     * Customer device has hardware issue, the latest software updates have not
     * been applied and device OS is windows
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen315WindowsHw() throws ServiceException {
	setupState(305, PROBLEM_TYPE.HARDWARE_ISSUE, "have_updates_been_applied", "N", "windows");
	session.fireAllRules();
	verifyState(315, "windows_update_instructions");
    }

    /**
     * Customer device has hardware issue, the latest software updates have not
     * been applied and device OS is other
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen315OtherHw() throws ServiceException {
	setupState(305, PROBLEM_TYPE.HARDWARE_ISSUE, "have_updates_been_applied", "N", "other");
	session.fireAllRules();
	verifyState(315, "generic_update_instructions");
    }

    /**
     * Customer device has hardware issue, the latest software updates have not
     * fixed the problem, and OS is iOS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen310IOSHw() throws ServiceException {
	setupState(315, PROBLEM_TYPE.HARDWARE_ISSUE, "ios_update_instructions", "N", "ios");
	session.fireAllRules();
	verifyState(310, "ios_soft_reset_instructions");
    }

    /**
     * Customer device has hardware issue, the latest software updates have not
     * fixed the problem, and OS is android
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen310AndroidHw() throws ServiceException {
	setupState(315, PROBLEM_TYPE.HARDWARE_ISSUE, "android_update_instructions", "N", "android");
	session.fireAllRules();
	verifyState(310, "generic_soft_reset_instructions");
    }

    /**
     * Customer device has hardware issue, the latest software updates have not
     * fixed the problem, and OS is blackberry
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen310BlackberryHw() throws ServiceException {
	setupState(315, PROBLEM_TYPE.HARDWARE_ISSUE, "blackberry_update_instructions", "N", "blackberry");
	session.fireAllRules();
	verifyState(310, "generic_soft_reset_instructions");
    }

    /**
     * Customer device has hardware issue, the latest software updates have not
     * fixed the problem, and OS is windows
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen310WindowsHw() throws ServiceException {
	setupState(315, PROBLEM_TYPE.HARDWARE_ISSUE, "windows_update_instructions", "N", "windows");
	session.fireAllRules();
	verifyState(310, "generic_soft_reset_instructions");
    }

    /**
     * Customer device has hardware issue, the latest software updates have not
     * fixed the problem, and OS is other
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen310OtherHw() throws ServiceException {
	setupState(315, PROBLEM_TYPE.HARDWARE_ISSUE, "generic_update_instructions", "N", "other");
	session.fireAllRules();
	verifyState(310, "generic_soft_reset_instructions");
    }

    /**
     * Customer device has hardware issue, the latest software updates have
     * resolved the issue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from315Hw() throws ServiceException {
	setupState(315, PROBLEM_TYPE.HARDWARE_ISSUE, "generic_update_instructions", "Y", "other");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /**
     * Customer device has hardware issue and a soft reset has resolved the
     * issue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from310Hw() throws ServiceException {
	setupState(310, PROBLEM_TYPE.HARDWARE_ISSUE, "generic_soft_reset_instructions", "Y", "other");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /**
     * Customer device has hardware issue and a soft reset has failed to
     * resolved the issue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen320Hw() throws ServiceException {
	setupState(310, PROBLEM_TYPE.HARDWARE_ISSUE, "generic_soft_reset_instructions", "N", "other");
	session.fireAllRules();
	verifyState(320, "hard_reset_permission");
    }

    /**
     * Customer wants a hard reset and and device OS is iOS
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen330IOSHw() throws ServiceException {
	setupState(320, PROBLEM_TYPE.HARDWARE_ISSUE, "hard_reset_permission", "Y", "ios");
	session.fireAllRules();
	verifyState(330, "ios_hard_reset_instructions");
    }

    /**
     * Customer wants a hard reset and device OS is android
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen330AndroidHw() throws ServiceException {
	setupState(320, PROBLEM_TYPE.HARDWARE_ISSUE, "hard_reset_permission", "Y", "android");
	session.fireAllRules();
	verifyState(330, "android_hard_reset_instructions");
    }

    /**
     * Customer wants a hard reset and device OS is blackberry
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen330BlackberryHw() throws ServiceException {
	setupState(320, PROBLEM_TYPE.HARDWARE_ISSUE, "hard_reset_permission", "Y", "blackberry");
	session.fireAllRules();
	verifyState(330, "blackberry_hard_reset_instructions");
    }

    /**
     * Customer wants a hard reset and device OS is windows
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen330WindowsHw() throws ServiceException {
	setupState(320, PROBLEM_TYPE.HARDWARE_ISSUE, "hard_reset_permission", "Y", "windows");
	session.fireAllRules();
	verifyState(330, "windows_hard_reset_instructions");
    }

    /**
     * Customer wants a hard reset and device OS is other
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen330OtherHw() throws ServiceException {
	setupState(320, PROBLEM_TYPE.HARDWARE_ISSUE, "hard_reset_permission", "Y", "other");
	session.fireAllRules();
	verifyState(330, "generic_hard_reset_instructions");
    }

    /**
     * Customer device has hardware issue and a hard reset has resolved the
     * issue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from330Hw() throws ServiceException {
	setupState(330, PROBLEM_TYPE.HARDWARE_ISSUE, "windows_hard_reset_instructions", "Y", "windows");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /**
     * Customer device has hardware issue, a hard reset has failed to resolve
     * the issue, and we know device has Geek Squad Protection a priori
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen350Hw() throws ServiceException {
	setupState(330, PROBLEM_TYPE.HARDWARE_ISSUE, "windows_hard_reset_instructions", "N", "other", true);
	session.fireAllRules();
	verifyState(350, "gsbtp_next_steps");
    }

    /**
     * Customer device has hardware issue, a hard reset has failed to resolve
     * the issue, and we know device does not have Geek Squad Protection a
     * priori
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen335from330Hw() throws ServiceException {
	setupState(330, PROBLEM_TYPE.HARDWARE_ISSUE, "windows_hard_reset_instructions", "N", "other", false);
	session.fireAllRules();
	verifyState(335, "check_device_manufacturer_warranty");
    }

    /**
     * Customer device has hardware issue, does not want a hard reset, and we
     * know device does not have Geek Squad Protection a priori
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen335from320Hw() throws ServiceException {
	setupState(320, PROBLEM_TYPE.HARDWARE_ISSUE, "hard_reset_permission", "N", "other", false);
	session.fireAllRules();
	verifyState(335, "check_device_manufacturer_warranty");
    }

    /**
     * Customer device has hardware issue, a hard reset has failed to resolve
     * the issue, and we don't know if device has Geek Squad Protection
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen101from330Hw() throws ServiceException {
	setupState(330, PROBLEM_TYPE.HARDWARE_ISSUE, "windows_hard_reset_instructions", "N", "other");
	session.fireAllRules();
	verifyState(101, "do_u_have_black_tie");
    }

    /**
     * Customer device has hardware issue and does not want a hard reset. We
     * don't know if device has Geek Squad Protection
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen101from320Hw() throws ServiceException {
	setupState(320, PROBLEM_TYPE.HARDWARE_ISSUE, "hard_reset_permission", "N", "ios");
	session.fireAllRules();
	verifyState(101, "do_u_have_black_tie");
    }

    /**
     * Customer device has hardware issue, a hard reset has failed to resolve
     * the issue or she does not want to attempt a hard reset, and has just told
     * us she has Geek Squad Protection
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen350from101Hw() throws ServiceException {
	setupState(101, PROBLEM_TYPE.HARDWARE_ISSUE, "do_u_have_black_tie", "Y", "other");
	session.fireAllRules();
	verifyState(350, "gsbtp_next_steps", true);
    }

    /**
     * Customer device has hardware issue, a hard reset has failed to resolve
     * the issue or she does not want to attempt a hard reset, and has just told
     * us she does not have have Geek Squad Protection
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen335from101Hw() throws ServiceException {
	setupState(101, PROBLEM_TYPE.HARDWARE_ISSUE, "do_u_have_black_tie", "N", "other");
	session.fireAllRules();
	verifyState(335, "check_device_manufacturer_warranty", false);
    }

    /**
     * Customer device has hardware issue, device is not under manufacturer
     * warranty
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen340Hw() throws ServiceException {
	setupState(335, PROBLEM_TYPE.HARDWARE_ISSUE, "check_device_manufacturer_warranty", "N", "windows", false);
	session.fireAllRules();
	verifyState(340, "no_gsbtp_next_steps", Arrays.asList("eligible_upgrade", "purchase_replacement", "other"),
		PROBLEM_TYPE.HARDWARE_ISSUE, false);
    }

    /**
     * Customer device has software issue, is not under manufacturer warranty,
     * and she has just told us which option to pursue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from340Hw() throws ServiceException {
	setupState(340, PROBLEM_TYPE.HARDWARE_ISSUE, "out_of_warranty_options", "other");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /**
     * Customer device has hardware issue, device is under manufacturer warranty
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen345Hw() throws ServiceException {
	setupState(335, PROBLEM_TYPE.HARDWARE_ISSUE, "check_device_manufacturer_warranty", "Y", "windows", false);
	session.fireAllRules();
	verifyState(345, "no_gsbtp_next_steps", Arrays.asList("eligible_upgrade", "purchase_replacement",
		"send_to_manufacturer_for_repair", "other"), PROBLEM_TYPE.HARDWARE_ISSUE, false);
    }

    /**
     * Customer device has hardware issue, is under manufacturer warranty, and
     * she has just told us which option to pursue
     * 
     * @throws ServiceException
     */
    @Test(timeout = 5000)
    public void testGoToScreen360from345Hw() throws ServiceException {
	setupState(340, PROBLEM_TYPE.HARDWARE_ISSUE, "warranty_options", "send_to_manufacturer_for_repair");
	session.fireAllRules();
	verifyState(360, "goto_dashboard");
    }

    /******************************************************************************
     * Utility methods for setting up and verifying state *
     ******************************************************************************/
    private void setupState(int step, PROBLEM_TYPE issue, String dialogName, String answer) {
	state.setStep(step);
	state.setIssue(issue);
	dialogItem = dialog.addDialogSelectItem(dialogName);
	dialogItem.setAnswer(answer);
	session.insert(state);
    }

    private void setupState(int step, PROBLEM_TYPE issue, String dialogName, String answer, String os) {
	state.getDevice().setOs(os);
	setupState(step, issue, dialogName, answer);
	session.insert(state);
    }

    private void setupState(int step, PROBLEM_TYPE issue, String dialogName, String answer, String os,
	    Boolean blackTieProtection) {
	state.getDevice().setBlackTieProtection(blackTieProtection);
	setupState(step, issue, dialogName, answer, os);
    }

    private void verifyState(int step, String dialogName) throws ServiceException {
	assertEquals(step, state.getStep());

	// only one question is expected
	assertEquals(1, state.getDialog().getDialogItems().size());

	// and it should contain the following question
	assertEquals(dialogName, (state.getDialog().getDialogItems().get(0)).getName());
    }

    private void verifyState(int step, String dialogName, PROBLEM_TYPE issue) throws ServiceException {
	// make sure the issue has been updated as specified
	assertEquals("Issue type:", issue, state.getIssue());
	verifyState(step, dialogName);
    }

    private void verifyState(int step, String dialogName, Boolean blackTieProtection) throws ServiceException {
	// make sure device protection status has been updated as specified
	assertEquals("Geek Squad Protection:", blackTieProtection, state.getDevice().getBlackTieProtection());
	verifyState(step, dialogName);
    }

    private void verifyState(int step, String dialogName, String os) throws ServiceException {
	// make sure os has been updated as specified
	assertEquals(os, state.getDevice().getOs());
	verifyState(step, dialogName);
    }

    private void verifyState(int step, String dialogName, List<String> expectedOptions, PROBLEM_TYPE issue,
	    Boolean blackTieProtection) throws ServiceException {
	verifyState(step, dialogName, blackTieProtection);
	// verify the choices in the select item are correct
	assertEquals(expectedOptions, ((DialogSelectItem) (state.getDialog().getDialogItems().get(0))).getOptions());
	// make sure the issue has been updated as specified
	assertEquals(issue, state.getIssue());
    }
}
