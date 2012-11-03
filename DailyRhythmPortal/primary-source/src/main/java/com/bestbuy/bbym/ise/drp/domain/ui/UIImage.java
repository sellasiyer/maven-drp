package com.bestbuy.bbym.ise.drp.domain.ui;

public class UIImage extends UIBaseComponent {

    private static final long serialVersionUID = 1L;

    private String url;
    private String title;

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    @Override
    public Type getType() {
	return UIComponent.Type.IMAGE;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("UIImage[title=");
	sb.append(title);
	sb.append(" url=");
	sb.append(url);
	sb.append(" ");
	sb.append(super.toString());
	sb.append("]");
	return sb.toString();
    }
}
