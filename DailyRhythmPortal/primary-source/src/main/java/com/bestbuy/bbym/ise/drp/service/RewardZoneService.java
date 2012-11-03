package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.RewardZone;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public interface RewardZoneService {

    public String getRewardZoneMemberNumber(String customerFirstName, String customerLastName, String phoneNumber,
	    String email) throws ServiceException, IseBusinessException;

    public RewardZone getRewardZonePointsAndCerts(String memberNumber) throws ServiceException, IseBusinessException;

    public RewardZone validateRewardZoneMember(String memberNumber) throws ServiceException, IseBusinessException;

    public List<RewardZone> retrieveCustomerRewardZone(String customerFirstName, String customerLastName,
	    String phoneNumber, String email) throws ServiceException, IseBusinessException;

}
