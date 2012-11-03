package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class RwzMemberCert implements Serializable {

    private String certId;
    private String certStatus;
    private int certAmount;
    private Date issuedDate;
    private Date expiredDate;

    public String getCertId() {
	return certId;
    }

    public void setCertId(String certId) {
	this.certId = certId;
    }

    public String getCertStatus() {
	return certStatus;
    }

    public void setCertStatus(String certStatus) {
	this.certStatus = certStatus;
    }

    public int getCertAmount() {
	return certAmount;
    }

    public void setCertAmount(int certAmount) {
	this.certAmount = certAmount;
    }

    public Date getIssuedDate() {
	return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
	this.issuedDate = issuedDate;
    }

    public Date getExpiredDate() {
	return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
	this.expiredDate = expiredDate;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    /**
     * Gets a comparator that orders Rewardzone member certs in ascending
     * expiration date order.
     */
    public static final Comparator<RwzMemberCert> getExpiryDateComparator() {
	return new Comparator<RwzMemberCert>() {

	    public int compare(RwzMemberCert rwzMemberCert1, RwzMemberCert rwzMemberCert2) {
		{
		    if (rwzMemberCert1 != null && rwzMemberCert2 != null && rwzMemberCert1.getExpiredDate() != null
			    && rwzMemberCert2.getExpiredDate() != null){
			return rwzMemberCert1.getExpiredDate().compareTo(rwzMemberCert2.getExpiredDate());
		    }
		    return 0;
		}
	    }
	};
    }
}
