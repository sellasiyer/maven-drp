package com.bestbuy.bbym.ise.drp.domain;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Message wrapper for summary of available customers for transfer
 * 
 * @author a175157
 */
@XmlRootElement(name = "DataTransferSummaryList")
public class DataTransferSummaryList {

    private List<DataTransferSummary> dataTransferSummary;

    public List<DataTransferSummary> getDataTransferSummary() {
	return dataTransferSummary;
    }

    public void setDataTransferSummary(List<DataTransferSummary> dataTransferSummary) {
	this.dataTransferSummary = dataTransferSummary;
    }

    @Override
    public String toString() {
	return "DataTransferSummaryList [dataTransferSummary=" + dataTransferSummary + "]";
    }

}
