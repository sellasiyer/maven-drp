package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Carrier;

/**
 * @author JAM0314
 */
@Service("dailyRhythmPortalService")
public class DailyRhythmPortalServiceImpl implements DailyRhythmPortalService {

    @Override
    public List<Carrier> getSupportedCarriers() {
	List<Carrier> carriers = new ArrayList<Carrier>();
	for(Carrier carrier: Carrier.values()){
	    if (!carrier.isLoanerPhoneOnly()){
		carriers.add(carrier);
	    }
	}
	return carriers;
    }
}
