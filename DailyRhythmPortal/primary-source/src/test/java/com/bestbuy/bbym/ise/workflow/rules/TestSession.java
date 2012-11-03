package com.bestbuy.bbym.ise.workflow.rules;

import java.util.Collection;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.command.Command;
import org.drools.event.process.ProcessEventListener;
import org.drools.event.rule.AgendaEventListener;
import org.drools.event.rule.WorkingMemoryEventListener;
import org.drools.runtime.Calendars;
import org.drools.runtime.Channel;
import org.drools.runtime.Environment;
import org.drools.runtime.ExitPoint;
import org.drools.runtime.Globals;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.ObjectFilter;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkItemManager;
import org.drools.runtime.rule.Agenda;
import org.drools.runtime.rule.AgendaFilter;
import org.drools.runtime.rule.FactHandle;
import org.drools.runtime.rule.LiveQuery;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.ViewChangedEventListener;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.drools.time.SessionClock;
import org.junit.Ignore;

import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;

@Ignore
public class TestSession implements StatefulKnowledgeSession {

    private TestRulesWorkflowState state;

    @Override
    public int fireAllRules() {
	Dialog dialog;
	if (state != null){
	    state.setStep(state.getStep() + 1);
	    switch ((int) state.getWorkflowId()) {
		// start workflow
		case 1:
		    dialog = new Dialog();
		    try{
			state.setDialog(dialog);
		    }catch(ServiceException e){
		    }
		    break;
		// continue workflow
		case 2:
		    dialog = new Dialog();
		    try{
			state.setDialog(dialog);
		    }catch(ServiceException e){
		    }
		    break;
		// end workflow
		case 3:
		    try{
			state.setDialog(null);
		    }catch(ServiceException e){
		    }
		    break;
	    }
	}
	return 0;
    }

    @Override
    public int fireAllRules(int arg0) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int fireAllRules(AgendaFilter arg0) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int fireAllRules(AgendaFilter arg0, int arg1) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public void fireUntilHalt() {
	// TODO Auto-generated method stub

    }

    @Override
    public void fireUntilHalt(AgendaFilter arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public <T> T execute(Command<T> arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Calendars getCalendars() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Map<String, Channel> getChannels() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Environment getEnvironment() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Object getGlobal(String arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Globals getGlobals() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public KnowledgeBase getKnowledgeBase() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public <T extends SessionClock> T getSessionClock() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public KnowledgeSessionConfiguration getSessionConfiguration() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void registerChannel(String arg0, Channel arg1) {
	// TODO Auto-generated method stub

    }

    @Override
    public void registerExitPoint(String arg0, ExitPoint arg1) {
	// TODO Auto-generated method stub

    }

    @Override
    public void setGlobal(String arg0, Object arg1) {
	// TODO Auto-generated method stub

    }

    @Override
    public void unregisterChannel(String arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void unregisterExitPoint(String arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public Agenda getAgenda() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public QueryResults getQueryResults(String arg0, Object... arg1) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public WorkingMemoryEntryPoint getWorkingMemoryEntryPoint(String arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<? extends WorkingMemoryEntryPoint> getWorkingMemoryEntryPoints() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void halt() {
	// TODO Auto-generated method stub

    }

    @Override
    public LiveQuery openLiveQuery(String arg0, Object[] arg1, ViewChangedEventListener arg2) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getEntryPointId() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public long getFactCount() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public FactHandle getFactHandle(Object arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public <T extends FactHandle> Collection<T> getFactHandles() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public <T extends FactHandle> Collection<T> getFactHandles(ObjectFilter arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Object getObject(FactHandle arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<Object> getObjects() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<Object> getObjects(ObjectFilter arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public FactHandle insert(Object arg0) {
	if (arg0 instanceof TestRulesWorkflowState){
	    state = (TestRulesWorkflowState) arg0;
	}
	return null;
    }

    @Override
    public void retract(FactHandle arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(FactHandle arg0, Object arg1) {
	// TODO Auto-generated method stub

    }

    @Override
    public void abortProcessInstance(long arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public ProcessInstance createProcessInstance(String arg0, Map<String, Object> arg1) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ProcessInstance getProcessInstance(long arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<ProcessInstance> getProcessInstances() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public WorkItemManager getWorkItemManager() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void signalEvent(String arg0, Object arg1) {
	// TODO Auto-generated method stub

    }

    @Override
    public void signalEvent(String arg0, Object arg1, long arg2) {
	// TODO Auto-generated method stub

    }

    @Override
    public ProcessInstance startProcess(String arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ProcessInstance startProcess(String arg0, Map<String, Object> arg1) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ProcessInstance startProcessInstance(long arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void addEventListener(WorkingMemoryEventListener arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void addEventListener(AgendaEventListener arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public Collection<AgendaEventListener> getAgendaEventListeners() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<WorkingMemoryEventListener> getWorkingMemoryEventListeners() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void removeEventListener(WorkingMemoryEventListener arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void removeEventListener(AgendaEventListener arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void addEventListener(ProcessEventListener arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public Collection<ProcessEventListener> getProcessEventListeners() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void removeEventListener(ProcessEventListener arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
	// TODO Auto-generated method stub

    }

    @Override
    public int getId() {
	// TODO Auto-generated method stub
	return 0;
    }

}
