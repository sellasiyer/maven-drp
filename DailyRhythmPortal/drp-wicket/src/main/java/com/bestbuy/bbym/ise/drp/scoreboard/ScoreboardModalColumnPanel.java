package com.bestbuy.bbym.ise.drp.scoreboard;

import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;

public abstract class ScoreboardModalColumnPanel extends Panel {

    private static final long serialVersionUID = 1L;

    protected PostMode postMode = PostMode.SALE;

    List<ScoreboardCategory> categoryList;

    public ScoreboardModalColumnPanel(String id, List<ScoreboardCategory> categoryList) {
	super(id);
	this.categoryList = categoryList;
    }

    public enum PostMode {
	RETURN("returnQuantity"), SALE("salesQuantity");

	String value;

	private PostMode(String value) {
	    this.value = value;
	}

	@Override
	public String toString() {
	    return value;
	}

    }

    public PostMode getPostMode() {
	return this.postMode;
    }

    public void setPostMode(PostMode postMode) {
	this.postMode = postMode;
    }

}
