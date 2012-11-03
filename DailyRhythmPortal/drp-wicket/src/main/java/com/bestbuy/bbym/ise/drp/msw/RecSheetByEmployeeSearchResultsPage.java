package com.bestbuy.bbym.ise.drp.msw;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.LoadingModalAjaxLink;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.PdfByteArrayResource;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportingSearchResults;
import com.bestbuy.bbym.ise.drp.service.MobileSalesWorkspaceService;
import com.bestbuy.bbym.ise.drp.util.FormatModel;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class RecSheetByEmployeeSearchResultsPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(RecSheetByEmployeeSearchResultsPage.class);
    private static final long serialVersionUID = 1L;

    private DefaultDataTable<RecSheetReportingSearchResults> searchResultsTable;
    private SortableEmpRecSheetDataProvider searchResultsDataProvider;
    private RecSheetByEmployeeSearchPanel searchPanel;

    @SpringBean(name = "mobileSalesWorkspaceService")
    private MobileSalesWorkspaceService mswService;

    public RecSheetByEmployeeSearchResultsPage(final PageParameters parameters, RecSheetByEmployeePage mswPage,
	    SortableEmpRecSheetDataProvider searchResultsDataProvider,
	    RecSheetByEmployeeSearchPanel recSheetByEmployeeSearchPanel) {

	super(parameters);
	this.searchResultsDataProvider = searchResultsDataProvider;
	searchPanel = recSheetByEmployeeSearchPanel;

	final String na = getString("notApplicable.label");

	add(new Label("lastNameLabel", new FormatModel<String>(searchPanel.getLastName(), na)));

	add(new Label("empIdLabel", new FormatModel<String>(searchPanel.getAid(), na)));

	add(new Label("storeLabel", new FormatModel<String>(searchPanel.getStoreNumber(), na)));

	SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

	add(new Label("dateRangeLabel", df.format(searchPanel.getStartDateField()) + "-"
		+ df.format(searchPanel.getEndDateField())));
	AjaxLink<Object> newSearchLink = new AjaxLink<Object>("newSearchLink") {

	    private static final long serialVersionUID = -4891689786270670982L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("new search submit..");
		setResponsePage(RecSheetByEmployeePage.class);

	    }

	};
	add(newSearchLink);

	// rec sheet search results
	List<IColumn<RecSheetReportingSearchResults>> columns = new ArrayList<IColumn<RecSheetReportingSearchResults>>();

	columns.add(new PropertyColumn<RecSheetReportingSearchResults>(new Model<String>(getString("firstName")),
		"first", "firstName"));
	columns.add(new PropertyColumn<RecSheetReportingSearchResults>(new Model<String>(getString("lastName")),
		"last", "lastName"));
	columns.add(new PropertyColumn<RecSheetReportingSearchResults>(new Model<String>(getString("aid")), "aid",
		"aid"));
	columns.add(new PropertyColumn<RecSheetReportingSearchResults>(
		new Model<String>(getString("recSheetsCreated")), "sheetsNum", "sheetsNum"));

	// hyperlink to launch selected worksheet
	columns.add(new AbstractColumn<RecSheetReportingSearchResults>(null) {

	    private static final long serialVersionUID = 1L;

	    public void populateItem(Item<ICellPopulator<RecSheetReportingSearchResults>> cellItem, String componentId,
		    IModel<RecSheetReportingSearchResults> model) {
		cellItem.add(new ActionPanel(componentId, model));
	    }
	});

	searchResultsTable = new DefaultDataTable<RecSheetReportingSearchResults>("searchResultsTable", columns,
		this.searchResultsDataProvider, Integer.MAX_VALUE);
	searchResultsTable.setOutputMarkupPlaceholderTag(true);

	add(searchResultsTable);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);
	response.renderOnDomReadyJavaScript("setupRecSheetTable();");
    }

    class ActionPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public ActionPanel(String id, final IModel<RecSheetReportingSearchResults> model) {
	    super(id, model);

	    LoadingModalAjaxLink viewLink = new LoadingModalAjaxLink("viewLink", null, new ResourceModel(
		    "loadingModalMessage.generatingPdf")) {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {

		    final PdfByteArrayResource pdfResource = new PdfByteArrayResource() {

			private static final long serialVersionUID = 1L;

			@Override
			public String fetchData() {
			    setPdfByteArray(null);

			    try{
				byte[] byteArray = mswService.getRecSheetReportAsPDF(model.getObject().getRecs());
				logger.debug("byteArray :: ---------" + byteArray);
				if (byteArray != null)
				    setPdfByteArray(byteArray);
			    }catch(ServiceException se){
				logger.error("Error getting rec sheet report as PDF", se);
			    }
			    return null;
			}

		    };

		    pdfResource.fetchData();

		    String uidString = UUID.randomUUID().toString().replaceAll("-", "");
		    ResourceReference rr = new ResourceReference(uidString) {

			private static final long serialVersionUID = 1L;

			@Override
			public IResource getResource() {
			    return pdfResource;

			}
		    };

		    if (rr.canBeRegistered()){
			getApplication().getResourceReferenceRegistry().registerResourceReference(rr);
			target.appendJavaScript("window.open('" + urlFor(rr, null) + "')");
		    }

		}

	    };

	    add(viewLink);
	}
    }

}
