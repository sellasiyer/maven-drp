package com.bestbuy.bbym.ise.drp.helpers;

public class PhoneSearchCriteria extends PhoneModelSearchCriteria {

    private static final long serialVersionUID = 1L;

    private String last4SerialNo;

    private Boolean checkedOut;

    private String last4ServiceOrderNo;

    public String getLast4SerialNo() {
	return last4SerialNo;
    }

    public void setLast4SerialNo(String last4SerialNo) {
	this.last4SerialNo = last4SerialNo;
    }

    public Boolean getCheckedOut() {
	return checkedOut;
    }

    public void setCheckedOut(Boolean checkedOut) {
	this.checkedOut = checkedOut;
    }

    public String getLast4ServiceOrderNo() {
	return last4ServiceOrderNo;
    }

    public void setLast4ServiceOrderNo(String last4ServiceOrderNo) {
	this.last4ServiceOrderNo = last4ServiceOrderNo;
    }

}
