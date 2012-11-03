package com.bestbuy.bbym.ise.drp.entitlement;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.springframework.beans.BeanUtils;

import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.dashboard.PurchaseHistoryDataProvider;
import com.bestbuy.bbym.ise.drp.dashboard.PurchaseHistoryPanel.TabId;
import com.bestbuy.bbym.ise.drp.dashboard.PurchaseHistoryTabPanel;
import com.bestbuy.bbym.ise.drp.domain.EntitlementLookup;
import com.bestbuy.bbym.ise.drp.loanerphone.CustomModalWindow;
import com.bestbuy.bbym.ise.drp.util.FeedbackMessageFilter;

public class EntitlementPurchaseModalPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    private CustomModalWindow purchaseTransactionWindow = null;

    public EntitlementPurchaseModalPanel(CustomModalWindow purchaseTransactionModal, EntitlementLookup entilement) {
	super(purchaseTransactionModal.getContentId());
	this.purchaseTransactionWindow = purchaseTransactionModal;

	final Form<Object> purchaseHistoryForm = new Form<Object>("purchaseHistoryForm");
	purchaseHistoryForm.setOutputMarkupPlaceholderTag(true);
	add(purchaseHistoryForm);

	final FeedbackMessageFilter filter = new FeedbackMessageFilter("PurchaseHistory");
	filter.setAcceptAllUnspecifiedComponents(false);
	filter.addFilterInComponent(purchaseHistoryForm);

	final FeedbackPanel purchaseHistoryFeedback = new FeedbackPanel("purchaseHistoryFeedback", filter);
	purchaseHistoryFeedback.setOutputMarkupPlaceholderTag(true);
	add(purchaseHistoryFeedback);

	purchaseHistoryForm.add(new PurchaseHistoryTabPanel("gspPurchaseHistoryTabPanel", purchaseHistoryFeedback,
		purchaseHistoryForm, filter, TabId.ALL));

	PurchaseSelectTabPanel purchaseSelectTabPanel = new PurchaseSelectTabPanel("purchaseSelectTabPanel", entilement);
	purchaseHistoryForm.add(purchaseSelectTabPanel);

    }

    class PurchaseSelectTabPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public PurchaseSelectTabPanel(String id, final EntitlementLookup entilement) {
	    super(id);

	    final List<IColumn<Product>> purchaseHistoryColumns = new ArrayList<IColumn<Product>>();

	    purchaseHistoryColumns.add(new AbstractColumn<Product>(null, "selectLinkColumn") {

		private static final long serialVersionUID = 1L;

		@Override
		public void populateItem(Item<ICellPopulator<Product>> cellItem, String componentId,
			IModel<Product> rowModel) {
		    Product row = (Product) rowModel.getObject();
		    cellItem.add(new SelectLinkPanel(componentId, rowModel, row, entilement));

		}

	    });

	    final PurchaseHistoryDataProvider purchasesDataProvider = new PurchaseHistoryDataProvider();
	    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	    purchasesDataProvider.setPurchaseHistoryList(session.getAllPurchaseHistory());

	    final AjaxFallbackDefaultDataTable<Product> entitlementPurchasesTable = new AjaxFallbackDefaultDataTable<Product>(
		    "entitlementPurchasesTable", purchaseHistoryColumns, purchasesDataProvider, 200) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		    return session.getAllPurchaseHistory() != null && !session.getAllPurchaseHistory().isEmpty();

		}
	    };
	    entitlementPurchasesTable.setMarkupId("entitlementPurchasesTable");
	    entitlementPurchasesTable.setOutputMarkupId(true);
	    entitlementPurchasesTable.setOutputMarkupPlaceholderTag(true);
	    add(entitlementPurchasesTable);

	} // End Panel Constructor
    } // End Panel Class    

    class SelectLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public SelectLinkPanel(String id, IModel<Product> model, final Product row, final EntitlementLookup entilement) {
	    super(id, model);

	    add(new AjaxLink<Product>("selectLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    Product product = new Product();
		    BeanUtils.copyProperties(row, product);
		    com.bestbuy.bbym.ise.domain.Item item = new com.bestbuy.bbym.ise.domain.Item();
		    item.setTransactionId(product.getTransactionId());
		    item.setTransactionKeyType(product.getTransactionKeyType());
		    item.setTransactionType(product.getTransactionType());
		    //item.setDescription(product.getDescription());
		    entilement.setProductSku(product.getSku());
		    entilement.setItem(item);
		    purchaseTransactionWindow.close(target);
		}
	    });

	}
    }

}
