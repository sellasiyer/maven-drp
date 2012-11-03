package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.RewardZone;
import com.bestbuy.bbym.ise.drp.sao.RewardZoneServiceSao;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("rewardZoneService")
public class RewardZoneServiceImpl implements RewardZoneService {

    @Autowired
    private RewardZoneServiceSao rewardZoneServiceSao;

    @Override
    public String getRewardZoneMemberNumber(String customerFirstName, String customerLastName, String phoneNumber,
	    String email) throws ServiceException, IseBusinessException {
	return rewardZoneServiceSao.getRewardZoneMemberNumber(customerFirstName, customerLastName, phoneNumber, email);
    }

    @Override
    public RewardZone getRewardZonePointsAndCerts(String memberNumber) throws ServiceException, IseBusinessException {
	return rewardZoneServiceSao.getRewardZonePointsAndCerts(memberNumber);
    }

    @Override
    public RewardZone validateRewardZoneMember(String memberNumber) throws ServiceException, IseBusinessException {
	return rewardZoneServiceSao.validateRewardZoneMember(memberNumber);
    }

    @Override
    public List<RewardZone> retrieveCustomerRewardZone(String customerFirstName, String customerLastName,
	    String phoneNumber, String email) throws ServiceException, IseBusinessException {
	return rewardZoneServiceSao.retrieveCustomerRewardZone(customerFirstName, customerLastName, phoneNumber, email);
    }

}
