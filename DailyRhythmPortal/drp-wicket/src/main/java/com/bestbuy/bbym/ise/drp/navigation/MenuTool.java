package com.bestbuy.bbym.ise.drp.navigation;

import java.io.Serializable;
import java.util.List;
import java.util.MissingResourceException;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.User;

public abstract class MenuTool implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(MenuTool.class);

    private User user;

    public MenuItem generateMenuBar(Component component) {
	MenuItem menuBar = new MenuItem(), menuItem;
	for(int i = 1; i <= 20; i++){
	    menuItem = generateMenuItem(component, ".", i);
	    if (menuItem != null){
		menuBar.add(menuItem);
	    }
	}
	return menuBar;
    }

    private MenuItem generateMenuItem(Component component, String level, int id) {
	if (component == null){
	    logger.warn("Invalid input component");
	    return null;
	}
	String propRoot = "menu" + level + id;
	String name = getPropertyValue(component, propRoot + ".name");
	if (name == null){
	    return null;
	}
	// Check if menu item disabled
	String enabled = getPropertyValue(component, propRoot + ".enabled");
	if (enabled != null){
	    if (enabled.equalsIgnoreCase("no") || enabled.equalsIgnoreCase("false")){
		return null;
	    }
	}
	// Only check role if present
	String role = getPropertyValue(component, propRoot + ".role");
	if (role != null){
	    if (user == null){
		return null;
	    }
	    String[] roles = StringUtils.split(role, ",");
	    boolean hasRole = false;
	    // Search for matching roles
	    for(String roleName: roles){
		if (user.hasRole(roleName)){
		    hasRole = true;
		    break;
		}
	    }
	    if (!hasRole){
		return null;
	    }
	}
	MenuItem mi = new MenuItem();
	mi.setName(name);
	String url = getPropertyValue(component, propRoot + ".url");
	if (url != null){
	    mi.setUrl(url);
	    // Ignore page if URL is set
	}else{
	    try{
		mi.setPage(getPropertyValue(component, propRoot + ".page"));
		if (mi.getPage() != null){
		    @SuppressWarnings("unchecked")
		    Class<? extends Page> pageClass = (Class<? extends Page>) Class.forName(mi.getPage());
		    if (pageClass == null){
			logger.warn("Invalid Page class for " + propRoot);
			return null;
		    }

		    Url relativeUrl = Url.parse(component.getPage().urlFor(pageClass, null).toString());
		    String fullUrl = RequestCycle.get().getUrlRenderer().renderFullUrl(relativeUrl);
		    mi.setUrl(fullUrl);

		    if (mi.getUrl() == null){
			logger.warn("Invalid URL generated from Page class for " + propRoot);
			return null;
		    }
		}
	    }catch(RuntimeException e){
		logger.warn("Invalid Page class for " + propRoot, e);
		return null;
	    }catch(ClassNotFoundException e){
		logger.warn("Invalid Page class for " + propRoot, e);
		return null;
	    }
	}

	mi.setItemListFactory(getPropertyValue(component, propRoot + ".itemListFactory"));
	mi.setPreImage(getPropertyValue(component, propRoot + ".preImage"));
	mi.setTarget(getPropertyValue(component, propRoot + ".target"));

	String subLevel = level + id + ".";
	MenuItem subMenuItem;
	for(int i = 1; i <= 20; i++){
	    subMenuItem = generateMenuItem(component, subLevel, i);
	    if (subMenuItem != null){
		mi.add(subMenuItem);
	    }
	}
	return mi;
    }

    public String generateMenuBarHtml(MenuItem menuBar) {
	if (menuBar == null || menuBar.getSubItems() == null || menuBar.getSubItems().isEmpty()){
	    return null;
	}
	int numTopLevelItems = menuBar.getSubItems().size();

	StringBuilder sb = new StringBuilder();
	sb.append("<ul>\n");
	int i = 0;
	String listItemClass;
	for(MenuItem mi: menuBar.getSubItems()){
	    i++;
	    listItemClass = null;
	    if (i == 1){
		listItemClass = "menu_first";
	    }else if (i == numTopLevelItems){
		listItemClass = "menu_last";
	    }
	    sb.append(generateMenuItemHtml(mi, listItemClass));
	}
	sb.append("</ul><br clear=\"all\" />\n");
	return sb.toString();
    }

    private String generateMenuItemHtml(MenuItem menuItem) {
	return generateMenuItemHtml(menuItem, null);
    }

    private String generateMenuItemHtml(MenuItem menuItem, String listItemClass) {
	if (menuItem == null){
	    return "";
	}
	StringBuilder sb = new StringBuilder();

	// Generate dynamic list of links
	if (menuItem.getItemListFactory() != null){
	    List<MenuItem> genList = getMenuItemList(menuItem.getItemListFactory());
	    if (genList != null){
		for(MenuItem mi: genList){
		    if (mi.getName() != null && mi.getUrl() != null){
			sb.append("<li>\n");
			sb.append("<a href=\"");
			sb.append(mi.getUrl());
			sb.append("\" target=\"_blank\">");
			sb.append(mi.getName());
			sb.append("</a>\n");
			sb.append("</li>\n");
		    }
		}
	    }
	    return sb.toString();
	}

	if (listItemClass == null){
	    sb.append("<li>\n");
	}else{
	    sb.append("<li class=\"");
	    sb.append(listItemClass);
	    sb.append("\">\n");
	}
	sb.append("<a href=\"");
	if (menuItem.getUrl() == null){
	    sb.append("#");
	}else{
	    sb.append(menuItem.getUrl());
	}
	if (menuItem.getTarget() != null){
	    sb.append("\" target=\"");
	    sb.append(menuItem.getTarget());
	}
	sb.append("\">");
	if (menuItem.getPreImage() != null){
	    sb.append("<img align=\"absmiddle\" src=\"");
	    sb.append(menuItem.getPreImage());
	    sb.append("\"/> ");
	}
	sb.append(menuItem.getName());
	sb.append("</a>\n");

	if (menuItem.getSubItems() != null && !menuItem.getSubItems().isEmpty()){
	    sb.append("<ul>\n");
	    for(MenuItem mi: menuItem.getSubItems()){
		sb.append(generateMenuItemHtml(mi));
	    }
	    sb.append("</ul>\n");
	}

	sb.append("</li>\n");
	return sb.toString();
    }

    public abstract List<MenuItem> getMenuItemList(String itemListFactory);

    private String getPropertyValue(Component component, String propertyName) {
	try{
	    return component.getString(propertyName);
	}catch(MissingResourceException e){
	    return null;
	}
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }
}
