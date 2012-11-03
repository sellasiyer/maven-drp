package com.bestbuy.bbym.ise.drp.rev;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;

public abstract class DeleteLineDialogPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;

    // Model Data
    private ManifestLine manifestLine = new ManifestLine();

    // State Data
    private boolean deleteSelected = false;
    private String deleteItemId;

    public DeleteLineDialogPanel(String id) {
	super(id);

	AjaxLink<Void> deleteLink = new AjaxLink<Void>("deleteLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		deleteSelected = true;
		close(target);
	    }
	};
	deleteLink.setMarkupId("deleteLink");
	deleteLink.setOutputMarkupId(true);
	deleteLink.setOutputMarkupPlaceholderTag(true);
	add(deleteLink);

	AjaxLink<Void> cancelLink = new AjaxLink<Void>("cancelLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		deleteSelected = false;
		close(target);
	    }
	};
	cancelLink.setMarkupId("cancelLink");
	cancelLink.setOutputMarkupId(true);
	cancelLink.setOutputMarkupPlaceholderTag(true);
	add(cancelLink);

	refresh();
    }

    public void setManifestLine(ManifestLine mLine) {
	this.manifestLine = mLine;
	refresh();
    }

    public ManifestLine getManifestLine() {
	return manifestLine;
    }

    public String getDeleteItemId() {
	return deleteItemId;
    }

    public void setDeleteItemId(String deleteItemId) {
	this.deleteItemId = deleteItemId;
    }

    private void refresh() {
	addOrReplace(new Label("itemTagLabel", new PropertyModel<String>(manifestLine, "itemTag")));
	addOrReplace(new Label("serialNumLabel", new PropertyModel<String>(manifestLine, "imeiesn")));
	addOrReplace(new Label("productDescriptionLabel", new PropertyModel<String>(manifestLine, "productDescription")));
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }

    public boolean isDelete() {
	return deleteSelected;
    }

    @Override
    public String getOpenModalJS() {
	return "setModalPanelFocus('#deleteLink');";
    }
}
