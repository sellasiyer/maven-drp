/*
 * Copyright 2012 a904002.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.bestbuy.bbym.ise.drp.admin.hotlinks;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.Hotlink;
import com.bestbuy.bbym.ise.drp.service.HotlinkService;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class HotlinkTablePanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(HotlinkTablePanel.class);

    private FeedbackPanel feedbackPanel;
    private AddEditHotlinkPanel addEditHotlinkPanel;
    private HotlinkTablePanel hotlinkTablePanel;
    private HotlinkTableDataProvider hotlinkTableProvider = null;

    @SpringBean(name = "hotlinkService")
    private HotlinkService hotlinkService;

    public HotlinkTablePanel(FeedbackPanel feedbackPanel, String id) {
	super(id);

	this.feedbackPanel = feedbackPanel;
	hotlinkTablePanel = this;

	try{
	    hotlinkTableProvider = new HotlinkTableDataProvider(hotlinkService
		    .getHotlinkListByUserId(getDailyRhythmPortalSession().getDrpUser().getUserId()));
	}catch(ServiceException e){
	    String message = "An unexpected exception occured while attempting to get user hotlinks";
	    processException(message, e, new PageParameters());
	}

	final List<IColumn<Hotlink>> columns = new ArrayList<IColumn<Hotlink>>();
	columns.add(new PropertyColumn<Hotlink>(new ResourceModel("hotlinkTablePanel.name.column"), "urlAlias",
		"urlAlias"));
	columns.add(new PropertyColumn<Hotlink>(new ResourceModel("hotlinkTablePanel.value.column"), "url", "url"));
	columns.add(new AbstractColumn<Hotlink>(new ResourceModel("hotlinkTablePanel.action.column"), "id") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Hotlink row = (Hotlink) rowModel.getObject();
		cellItem.add(new HotlinkTablePanel.EditLinkPanel(componentId, rowModel, row));
	    }
	});
	columns.add(new AbstractColumn<Hotlink>(new ResourceModel("hotlinkTablePanel.order.column"), "id") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Hotlink row = (Hotlink) rowModel.getObject();
		cellItem.add(new HotlinkTablePanel.UpDownLinkPanel(componentId, rowModel, row));
	    }
	});

	final DefaultDataTable<Hotlink> hotLinkTable = new DefaultDataTable<Hotlink>("hotlinkTable", columns,
		hotlinkTableProvider, 10);
	add(hotLinkTable);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);
	response.renderOnDomReadyJavaScript("setupHotlinkTable();");
    }

    class EditLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public EditLinkPanel(String id, final IModel<?> model, final Hotlink row) {
	    super(id, model);

	    AjaxLink<Object> editLink = new AjaxLink<Object>("editLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.info("in edit onClick, alias=" + row.getUrlAlias() + " id=" + row.getId());
		    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		    session.getAddEditHotlink().copy(row);
		    target.add(hotlinkTablePanel);
		    target.add(addEditHotlinkPanel);
		    addEditHotlinkPanel.targetComponents(target);
		}

		@Override
		public boolean isEnabled() {
		    return !ManageHotlinksPage.isInAddEditMode(getDailyRhythmPortalSession());
		}

		@Override
		protected IAjaxCallDecorator getAjaxCallDecorator() {
		    return new AjaxCallDecorator() {

			private static final long serialVersionUID = 1L;

			@Override
			public CharSequence decorateScript(Component component, CharSequence script) {
			    return "handleAddHotlinkButtonEnabling(false);" + script;
			}
		    };
		}

	    };
	    editLink.setOutputMarkupPlaceholderTag(true);
	    add(editLink);

	    AjaxLink<Object> deleteLink = new AjaxLink<Object>("deleteLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    try{
			hotlinkService.deleteLink(row);
			hotlinkTableProvider.removeRow(row);
			DailyRhythmPortalSession session = getDailyRhythmPortalSession();
			session.info("Hotlink named \"" + row.getUrlAlias() + "\" deleted");
			target.add(hotlinkTablePanel);
			target.add(feedbackPanel);
			ManageHotlinksPage.updateSessionMenuBarHtml(session, hotlinkService);
		    }catch(ServiceException se){
			String message = "An unexpected exception occured while attempting to delete hotlink";
			logger.error(message, se);
			processException(message, se, getPage().getPageParameters());
		    }
		}

		@Override
		public boolean isEnabled() {
		    return !ManageHotlinksPage.isInAddEditMode(getDailyRhythmPortalSession());
		}

	    };
	    deleteLink.setOutputMarkupPlaceholderTag(true);
	    add(deleteLink);
	}
    }

    class UpDownLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public UpDownLinkPanel(String id, final IModel<?> model, final Hotlink row) {
	    super(id, model);

	    Link<Object> upLink = new Link<Object>("upLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick() {
		    try{
			DailyRhythmPortalSession session = getDailyRhythmPortalSession();
			hotlinkService.moveLinkUp(row, session.getDrpUser());
			session.info("Hotlink named \"" + row.getUrlAlias() + "\" moved up in display order");
			ManageHotlinksPage.updateSessionMenuBarHtml(session, hotlinkService);
			setResponsePage(ManageHotlinksPage.class);
		    }catch(ServiceException se){
			String message = "An unexpected exception occured while attempting to change display order of hotlinks";
			logger.error(message, se);
			processException(message, se, getPage().getPageParameters());
		    }catch(IseBusinessException be){
			error(be.getFullMessage());
			setResponsePage(ManageHotlinksPage.class);
		    }
		}

		@Override
		public boolean isEnabled() {
		    return !ManageHotlinksPage.isInAddEditMode(getDailyRhythmPortalSession());
		}
	    };
	    add(upLink);

	    Link<Object> downLink = new Link<Object>("downLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick() {
		    try{
			DailyRhythmPortalSession session = getDailyRhythmPortalSession();
			hotlinkService.moveLinkDown(row, session.getDrpUser());
			session.info("Hotlink named \"" + row.getUrlAlias() + "\" moved down in display order");
			ManageHotlinksPage.updateSessionMenuBarHtml(session, hotlinkService);
			setResponsePage(ManageHotlinksPage.class);
		    }catch(ServiceException se){
			String message = "An unexpected exception occured while attempting to change display order of hotlinks";
			logger.error(message, se);
			processException(message, se, getPage().getPageParameters());
		    }catch(IseBusinessException be){
			error(be.getFullMessage());
			setResponsePage(ManageHotlinksPage.class);
		    }
		}

		@Override
		public boolean isEnabled() {
		    return !ManageHotlinksPage.isInAddEditMode(getDailyRhythmPortalSession());
		}
	    };
	    add(downLink);
	}
    }

    public void setAddEditHotlinkPanel(AddEditHotlinkPanel addEditHotlinkPanel) {
	this.addEditHotlinkPanel = addEditHotlinkPanel;
    }

}
