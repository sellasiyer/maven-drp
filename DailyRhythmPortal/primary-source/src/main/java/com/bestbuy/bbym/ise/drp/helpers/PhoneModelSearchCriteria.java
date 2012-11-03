package com.bestbuy.bbym.ise.drp.helpers;

import java.io.Serializable;

import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;

public class PhoneModelSearchCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private Carrier carrier;

    private OperatingSystem operatingSystem;

    public Carrier getCarrier() {
	return carrier;
    }

    public void setCarrier(Carrier carrier) {
	this.carrier = carrier;
    }

    public OperatingSystem getOperatingSystem() {
	return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
	this.operatingSystem = operatingSystem;
    }

}
