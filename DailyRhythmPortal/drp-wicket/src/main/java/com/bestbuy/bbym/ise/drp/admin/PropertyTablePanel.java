package com.bestbuy.bbym.ise.drp.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.DrpProperty;
import com.bestbuy.bbym.ise.drp.loanerphone.CustomModalWindow;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.TruncateFormatter;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class PropertyTablePanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(PropertyTablePanel.class);

    private AddEditPropertyPanel addEditPropertyPanel;

    @SpringBean(name = "drpPropertyService")
    private DrpPropertyService drpPropertyService;

    public PropertyTablePanel(String id) {
	super(id);

	PropertyTableDataProvider propertyTableProvider = null;
	try{
	    propertyTableProvider = new PropertyTableDataProvider(drpPropertyService.getAllProperties());
	}catch(ServiceException e){
	    String message = "An unexpected exception occured while attempting to get system properties";
	    processException(message, e, new PageParameters());
	}

	final List<IColumn<DrpProperty>> columns = new ArrayList<IColumn<DrpProperty>>();
	columns.add(new AbstractColumn<DrpProperty>(new Model<String>(getString("propertyTablePanel.action.column")),
		"name") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		DrpProperty row = (DrpProperty) rowModel.getObject();
		cellItem.add(new PropertyTablePanel.EditLinkPanel(componentId, rowModel, row,
			getString("propertyTablePanel.editLink.label"),
			getString("propertyTablePanel.editLink.labelD"), null));

	    }
	});
	columns
		.add(new PropertyColumn<DrpProperty>(new ResourceModel("propertyTablePanel.name.column"), "name",
			"name"));
	columns.add(new FormatPropertyColumn<DrpProperty, String>(new ResourceModel("propertyTablePanel.value.column"),
		"value", "value", new TruncateFormatter<String>(80)));

	columns.add(new PropertyColumn<DrpProperty>(new ResourceModel("propertyTablePanel.wicket.column"),
		"wicketProperty", "wicketProperty"));

	columns.add(new PropertyColumn<DrpProperty>(new ResourceModel("propertyTablePanel.description.column"),
		"description", "description"));

	final DefaultDataTable<DrpProperty> propertyTable = new DefaultDataTable<DrpProperty>("propertyTable", columns,
		propertyTableProvider, 200);
	propertyTable.setOutputMarkupPlaceholderTag(true);
	add(propertyTable);

	PagingNavigator pager = new PagingNavigator("pager", propertyTable);
	add(pager);

    }

    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);
	response.renderOnDomReadyJavaScript("setupPropertyTable();");
    }

    class EditLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public EditLinkPanel(String id, final IModel<?> model, final DrpProperty row, final String label,
		final String labelD, final List<DrpProperty> propertyList) {
	    super(id, model);

	    final CustomModalWindow deleteModalWindow = new CustomModalWindow("deleteModalWindow");

	    add(deleteModalWindow);

	    deleteModalWindow.setContent(new DeleteModalWindowPanel(deleteModalWindow.getContentId(), null,
		    PropertyTablePanel.this.getPageReference(), deleteModalWindow, getString("confirmationLabel"),
		    drpPropertyService, row));

	    AjaxLink<Object> link = new AjaxLink<Object>("link") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.info("in edit onClick, name=" + row.getName() + " id=" + row.getId());
		    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		    session.getAddEditDrpProperty().copy(row);
		    target.add(PropertyTablePanel.this);
		    target.add(addEditPropertyPanel);
		    addEditPropertyPanel.targetComponents(target);
		}

		@Override
		public boolean isEnabled() {
		    if (row.getId() == null || row.getId().longValue() < 0L){
			return false;
		    }
		    return !ManagePropertiesPage.isInAddEditMode(getDailyRhythmPortalSession());
		}

		@Override
		protected IAjaxCallDecorator getAjaxCallDecorator() {
		    return new AjaxCallDecorator() {

			private static final long serialVersionUID = 1L;

			@Override
			public CharSequence decorateScript(Component component, CharSequence script) {
			    return "handleAddPropertyButtonEnabling(false);" + script;
			}
		    };
		}

	    };

	    AjaxLink<Object> deleteLink = new AjaxLink<Object>("deleteLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    deleteModalWindow.show(target);
		    target.add(deleteModalWindow);
		}

		@Override
		public boolean isEnabled() {
		    if (row.getId() == null || row.getId().longValue() < 0L){
			return false;
		    }
		    return !ManagePropertiesPage.isInAddEditMode(getDailyRhythmPortalSession());
		}

	    };

	    link.setOutputMarkupPlaceholderTag(true);
	    link.add(new Label("label", label));
	    add(link);

	    deleteLink.setOutputMarkupPlaceholderTag(true);
	    deleteLink.add(new Label("labelD", labelD));
	    add(deleteLink);
	}
    }

    public void setAddEditPropertyPanel(final AddEditPropertyPanel addEditPropertyPanel) {
	this.addEditPropertyPanel = addEditPropertyPanel;
    }

    public PageReference getPageReference() {

	return null;
    }

}
