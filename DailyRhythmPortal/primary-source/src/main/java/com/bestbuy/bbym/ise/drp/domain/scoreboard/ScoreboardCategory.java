/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.domain.scoreboard;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for score board sales and return category.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 25
 */
public class ScoreboardCategory extends BaseObject {

    private static final long serialVersionUID = -48322046699746824L;

    private String name;
    private List<ScoreboardDataItem> dataItems;
    private Long id;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<ScoreboardDataItem> getDataItems() {
	return dataItems;
    }

    public void setDataItems(List<ScoreboardDataItem> dataItems) {
	this.dataItems = dataItems;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
