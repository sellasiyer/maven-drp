package com.bestbuy.bbym.ise.drp.domain.ui;

public class UILink extends UIBaseComponent {

    private static final long serialVersionUID = 1L;

    private UIRequest uiRequest;

    public UIRequest getUiRequest() {
	return uiRequest;
    }

    public void setUiRequest(UIRequest uiRequest) {
	this.uiRequest = uiRequest;
    }

    @Override
    public Type getType() {
	return UIComponent.Type.LINK;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("UILink[uiRequest=");
	sb.append(uiRequest);
	sb.append(" ");
	sb.append(super.toString());
	sb.append("]");
	return sb.toString();
    }
}
