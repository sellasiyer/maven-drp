package com.bestbuy.bbym.ise.drp.loanerphone;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.domain.PhoneModel;

public class CheckedOutModalPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(CheckedOutModalPanel.class);

    private AjaxFallbackDefaultDataTable<Phone> dataTable;

    public CheckedOutModalPanel(String id, PageParameters parameters, final PageReference modalWindowPage,
	    final CustomModalWindow window, final PhoneDataProvider phoneDataProvider, final PhoneModel phoneModel,
	    final Phone modalWindowPhone) {

	super(id);
	logger.info("in the checked out modal page........");

	logger.info("phone data provider .........." + phoneDataProvider);
	modalWindowPage.getPage();

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	Form<Object> checkedOutModalForm = new Form<Object>("checkedOutModalForm");
	checkedOutModalForm.setOutputMarkupPlaceholderTag(true);
	add(checkedOutModalForm);

	final List<IColumn<Phone>> columns = new ArrayList<IColumn<Phone>>();
	columns.add(new AbstractColumn<Phone>(new ResourceModel("lptable.available.carrier.column")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<Phone>> cellItem, String componentId, IModel<Phone> rowModel) {
		cellItem.add(new IconPanel<Phone>(componentId, rowModel));
	    }
	});
	columns.add(new PropertyColumn<Phone>(new ResourceModel("checkedOutModalForm.lptable.os.column"),
		"operatingSystem.os"));
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.make.column"), "make"));
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.model.column"), "modelNumber"));
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.imei.column"), "serialNumber"));
	columns.add(new AbstractColumn<Phone>(null, "select") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<Phone>> cellItem, String componentId, IModel<Phone> rowModel) {

		Phone row = (Phone) rowModel.getObject();

		cellItem.add(new SelectPhonePanel(componentId, rowModel, row, window, phoneModel, modalWindowPhone,
			feedbackPanel));
	    }

	});

	dataTable = new AjaxFallbackDefaultDataTable<Phone>("dataTable", columns, phoneDataProvider, Integer.MAX_VALUE);
	dataTable.setOutputMarkupPlaceholderTag(true);
	dataTable.setVisible(true);
	checkedOutModalForm.add(dataTable);

	AjaxButton cancelButton = new AjaxButton("cancelButton", checkedOutModalForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		if (target != null){
		    modalWindowPhone.setId(null);
		    modalWindowPhone.setSerialNumber(null);
		    window.close(target);
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	cancelButton.setOutputMarkupPlaceholderTag(true);
	checkedOutModalForm.add(cancelButton);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		logger.info("invoking javascript onDomReady...");
		response.renderOnDomReadyJavaScript("setupTables();");
	    }
	});

    }

    class SelectPhonePanel extends Panel {

	private static final long serialVersionUID = 1L;

	public SelectPhonePanel(String id, final IModel<Phone> model, final Phone row, final CustomModalWindow window,
		final PhoneModel phoneModel, final Phone modalWindowPhone, final FeedbackPanel feedbackPanel) {
	    super(id, model);

	    AjaxLink<Phone> selectLink = new AjaxLink<Phone>("selectLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.info("in select phone panel on click...." + row);
		    if (row != null && row.getCondition() != null && LoanerPhoneCondition.DAMAGED == row.getCondition()
			    || LoanerPhoneCondition.LOST == row.getCondition()){
			logger.error("You cannot check out a damaged or lost phone");
			feedbackPanel.error(StringUtils.replace(
				getString("loanerPhoneCheckedOutForm.checkOut.error.label"), "${condition}", row
					.getCondition().toString()));
			return;
		    }
		    // setting phone , phoneModel parameters
		    mapPhoneParameters(modalWindowPhone, row);
		    phoneModel.setCarrier(row.getCarrier());
		    phoneModel.setOs(row.getOperatingSystem());
		    phoneModel.setMake(row.getMake());
		    phoneModel.setModel(row.getModelNumber());
		    logger.info("instruction in modal window...." + row.getOperatingSystem().getInstruction());
		    window.close(target);
		}
	    };
	    add(selectLink);
	}

	private void mapPhoneParameters(Phone modalWindowPhone, Phone row) {
	    modalWindowPhone.setOperatingSystem(row.getOperatingSystem());
	    modalWindowPhone.setCarrier(row.getCarrier());
	    modalWindowPhone.setId(row.getId());
	    modalWindowPhone.setStoreId(row.getStoreId());
	    modalWindowPhone.setSerialNumber(row.getSerialNumber());
	    modalWindowPhone.setMake(row.getMake());
	    modalWindowPhone.setModelNumber(row.getModelNumber());
	    modalWindowPhone.setCondition(row.getCondition());
	    modalWindowPhone.setConditionComment(row.getConditionComment());
	    modalWindowPhone.setLastActionUserId(row.getLastActionUserId());
	    modalWindowPhone.setSku(row.getSku());
	    modalWindowPhone.setDeletedReason(row.getDeletedReason());
	    modalWindowPhone.setActive(row.isActive());
	    modalWindowPhone.setCreatedBy(row.getCreatedBy());
	    modalWindowPhone.setCreatedOn(row.getCreatedOn());
	    modalWindowPhone.setModifiedBy(row.getModifiedBy());
	    modalWindowPhone.setModifiedOn(row.getModifiedOn());
	    modalWindowPhone.setFirstName(row.getFirstName());
	    modalWindowPhone.setLastName(row.getLastName());
	}
    }

}
