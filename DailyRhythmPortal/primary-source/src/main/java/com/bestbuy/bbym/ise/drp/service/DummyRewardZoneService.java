package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.RewardZone;
import com.bestbuy.bbym.ise.drp.domain.RwzMemberCert;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("rewardZoneService2")
public class DummyRewardZoneService implements RewardZoneService {

    enum RewardZoneStatus {
	ACTIVE, PENDING, CLOSED
    }

    enum RewardZoneMemberTier {
	MEMBER, SILVER, PREMIUM_SILVER
    }

    @Override
    public String getRewardZoneMemberNumber(String customerFirstName, String customerLastName, String phoneNumber,
	    String email) throws ServiceException, IseBusinessException {
	DummyData.sleep(1);
	DummyData.throwIseBusinessException(70);
	DummyData.throwServiceException(70);

	return DummyData.getAcctId();

    }

    @Override
    public RewardZone getRewardZonePointsAndCerts(String memberNumber) throws ServiceException, IseBusinessException {
	DummyData.sleep(1);
	DummyData.throwIseBusinessException(70);
	DummyData.throwServiceException(70);

	RewardZone rz = new RewardZone();
	rz.setAccountStatus(getDummyStatus());
	rz.setMemberNumber(DummyData.getAcctId());
	rz.setMemberTier(getDummyMemberTier());
	rz.setPointsBalance(DummyData.getRandomIndex(100000));

	rz.setRwzMemberCertList(makeDummyCertList(10));

	return rz;
    }

    private static String getDummyStatus() {
	RewardZoneStatus[] statusTypes = RewardZoneStatus.values();
	int i = new Random().nextInt(statusTypes.length - 1);
	return statusTypes[i].name();
    }

    private static String getDummyMemberTier() {

	RewardZoneMemberTier[] tiers = RewardZoneMemberTier.values();
	int i = new Random().nextInt(tiers.length - 1);
	return tiers[i].name();
    }

    private static RwzMemberCert makeDummyCert() {
	RwzMemberCert cert = new RwzMemberCert();
	cert.setCertAmount(DummyData.getRandomIndex(2000));
	cert.setExpiredDate(DummyData.getDate());

	// unused so far.
	cert.setIssuedDate(new Date());
	return cert;
    }

    private static List<RwzMemberCert> makeDummyCertList(int upToLength) {
	int length = new Random().nextInt(upToLength);

	List<RwzMemberCert> list = new ArrayList<RwzMemberCert>(length);
	for(int i = 0; i < length; i++){
	    list.add(makeDummyCert());
	}
	return list;
    }

    @Override
    public RewardZone validateRewardZoneMember(String memberNumber) throws ServiceException, IseBusinessException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<RewardZone> retrieveCustomerRewardZone(String customerFirstName, String customerLastName,
	    String phoneNumber, String email) throws ServiceException, IseBusinessException {
	DummyData.sleep(1);
	DummyData.throwIseBusinessException(30);
	DummyData.throwServiceException(30);
	List<RewardZone> rewardZoneList = new ArrayList<RewardZone>();
	int numRewardZone = DummyData.getRandomIndex(7);
	for(int i = 1; i <= numRewardZone; i++){
	    RewardZone rz = new RewardZone();
	    rz.setMemberNumber(DummyData.getRewardZoneNumber());
	    rz.setAccountStatus(DummyData.getRewardZoneStatus());
	    rewardZoneList.add(rz);
	}
	return rewardZoneList;
    }
}
