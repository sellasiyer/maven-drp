package com.bestbuy.bbym.ise.drp.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.common.ExceptionPageHandler;
import com.bestbuy.bbym.ise.drp.reporting.JasperReportsPDFResource;
import com.bestbuy.bbym.ise.drp.util.CreditCardUrlFormatter;
import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;

public class DashboardQuickActionsPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(DashboardQuickActionsPanel.class);

    private SelectItem<String> selectedItem;

    public DashboardQuickActionsPanel(String id) {
	super(id);

	final DailyRhythmPortalSession session = (DailyRhythmPortalSession) getSession();

	final Form<Object> quickActionsForm = new Form<Object>("quickActionsForm");
	quickActionsForm.setOutputMarkupPlaceholderTag(true);
	add(quickActionsForm);

	final List<SelectItem<String>> quickActionSelections = new ArrayList<SelectItem<String>>();
	quickActionSelections.add(new SelectItem<String>("UPCC", getString("dashboardQuickActions.UPDATE_CC")));
	quickActionSelections.add(new SelectItem<String>("BBTI", getString("dashboardQuickActions.BUY_BACK_TRADE_IN")));
	quickActionSelections.add(new SelectItem<String>("PRSU", getString("dashboardQuickActions.PRINT_SUMMARY")));
	selectedItem = quickActionSelections.get(0);

	ChoiceRenderer<SelectItem<String>> choiceRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");
	DropDownChoice<SelectItem<String>> quickActionsSelect = new DropDownChoice<SelectItem<String>>(
		"quickActionsSelect", new PropertyModel<SelectItem<String>>(this, "selectedItem"),
		quickActionSelections, choiceRenderer);
	quickActionsSelect.setOutputMarkupPlaceholderTag(true);
	quickActionsForm.add(quickActionsSelect);

	final AjaxButton qaButton = new AjaxButton("quickActionsButton", new ResourceModel("goLabel"), quickActionsForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		if (selectedItem == null){
		    return;
		}
		if ("BBTI".equals(selectedItem.getKey())){
		    // FIXME these link URLs should be system properties
		    String buyback_url = "https://tradein.na.bestbuy.com/";
		    target.appendJavaScript("window.open('" + buyback_url + "')");

		}else if ("PRSU".equals(selectedItem.getKey())){
		    String uuidString = UUID.randomUUID().toString().replaceAll("-", "");
		    ResourceReference ref = new ResourceReference(uuidString) {

			private static final long serialVersionUID = 1L;

			@Override
			public IResource getResource() {
			    logger.info("Generating print summary for customer " + session.getCustomer().getId());
			    JasperReportsPDFResource jrpdf = null;
			    try{
				Customer[] customers = new Customer[] {session.getCustomer() };
				JasperPrint jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader()
					.getResourceAsStream("CarrierCustomerInfo.jasper"), null,
					new JRBeanArrayDataSource(customers));
				logger.info("Generated report");
				jrpdf = new JasperReportsPDFResource(jasperPrint);
			    }catch(Exception e){
				String message = "Unexpected Exception while rendering customer subscription information PDF";
				logger.error(message, e);
				ExceptionPageHandler.processException(message, IseExceptionCodeEnum.ReportError,
					getPage().getPageParameters(), getSession());
			    }
			    return jrpdf;
			}
		    };
		    if (ref.canBeRegistered()){
			getApplication().getResourceReferenceRegistry().registerResourceReference(ref);
			target.appendJavaScript("window.open('" + urlFor(ref, null) + "')");
		    }

		}else if ("UPCC".equals(selectedItem.getKey())){
		    Customer c = session.getCustomer();
		    final String cc_url = CreditCardUrlFormatter.formatURL(c.getFirstName(), c.getLastName());
		    target.appendJavaScript("window.open('" + cc_url + "')");
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
	    }
	};
	quickActionsForm.add(qaButton);

    }
}
