package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem.OSTypes;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.domain.PhoneModel;
import com.bestbuy.bbym.ise.drp.helpers.PhoneModelSearchCriteria;
import com.bestbuy.bbym.ise.drp.helpers.PhoneSearchCriteria;
import com.bestbuy.bbym.ise.exception.ServiceException;

// change to "loanerPhoneService" for spring to pick it up.
@Service("loanerPhoneService2")
public class DummyLoanerPhoneService implements LoanerPhoneService {

    @Override
    public List<Phone> getPhones(String storeId, PhoneSearchCriteria criteria) throws ServiceException {
	List<Phone> list = new ArrayList<Phone>();

	for(int i = 0; i < 10; i++){
	    list.add(this.makeDummyPhone());
	}
	return list;
    }

    @Override
    public List<PhoneModel> getPhoneModels(String storeId, PhoneModelSearchCriteria criteria) throws ServiceException {
	List<PhoneModel> list = new ArrayList<PhoneModel>();

	for(int i = 0; i < 10; i++){
	    list.add(this.makeDummyPhoneModel());
	}
	return list;

    }

    @Override
    public void createPhone(Phone phone) throws ServiceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void updatePhone(Phone phone) throws ServiceException {

    }

    @Override
    public void checkOutPhone(CheckOutCheckInHistory history) throws ServiceException {
    }

    private Phone makeDummyPhone() {
	Phone p = new Phone();
	String[] make = {"samsung", "sanyo", "blackberry", "apple", "LG", "Motorola" };
	String[] model = {"Galaxy S II", "Vero", "Torch", "iPhone 4", "EVO 3d", "Marquee", "Conquer 4G", "Galaxy" };
	OSTypes[] ostypes = OperatingSystem.OSTypes.values();
	Carrier[] carriers = Carrier.values();
	Random rand = new Random();

	//carrier
	com.bestbuy.bbym.ise.drp.domain.Carrier carrier = new com.bestbuy.bbym.ise.drp.domain.Carrier();
	carrier.setCarrier(carriers[rand.nextInt(carriers.length)].toString());
	p.setCarrier(carrier);

	//os
	OperatingSystem os = new OperatingSystem();
	os.setOs(ostypes[rand.nextInt(ostypes.length)].toString());
	p.setOperatingSystem(os);

	p.setMake(make[rand.nextInt(make.length)]);
	p.setCondition(LoanerPhoneCondition.GOOD);
	p.setModelNumber(model[rand.nextInt(model.length)]);

	CheckOutCheckInHistory checkHistory = new CheckOutCheckInHistory();
	checkHistory.setFirstName("Jon");
	checkHistory.setLastName("Smith");
	checkHistory.setServiceOrderNo("" + rand.nextInt(1000000));
	checkHistory.setCheckInEmployee("user");
	checkHistory.setCheckOutTime(new Date());
	p.setLatestCheckOutCheckInHistory(checkHistory);
	p.setSerialNumber("t" + new Integer(rand.nextInt(100000)).toString());

	return p;

    }

    private PhoneModel makeDummyPhoneModel() {
	String[] make = {"samsung", "sanyo", "blackberry", "apple", "LG", "Motorola" };
	String[] model = {"Galaxy S II", "Vero", "Torch", "iPhone 4", "EVO 3d", "Marquee", "Conquer 4G", "Galaxy" };
	OSTypes[] ostypes = OperatingSystem.OSTypes.values();
	Carrier[] carriers = Carrier.values();

	Random rand = new Random();

	PhoneModel p = new PhoneModel();
	//carrier
	com.bestbuy.bbym.ise.drp.domain.Carrier carrier = new com.bestbuy.bbym.ise.drp.domain.Carrier();
	carrier.setCarrier(carriers[rand.nextInt(carriers.length)].toString());
	p.setCarrier(carrier);

	p.setMake(make[rand.nextInt(make.length)]);
	p.setModel(model[rand.nextInt(model.length)]);

	//os
	OperatingSystem os = new OperatingSystem();
	os.setOs(ostypes[rand.nextInt(ostypes.length)].toString());
	p.setOs(os);

	return p;
    }

    @Override
    public List<com.bestbuy.bbym.ise.drp.domain.Carrier> getCarriers() throws ServiceException {
	return new ArrayList<com.bestbuy.bbym.ise.drp.domain.Carrier>();
    }

    @Override
    public List<OperatingSystem> getOperatingSystems() throws ServiceException {
	return new ArrayList<OperatingSystem>();
    }

    public String[] getDistinctMakes(String stor_id) throws ServiceException {
	return null;
    }

    public String[] getDistinctModels(String stor_id) throws ServiceException {
	return null;
    }

    @Override
    public void checkInPhone(CheckOutCheckInHistory history) throws ServiceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void updateHistory(CheckOutCheckInHistory history) throws ServiceException {
	// TODO Auto-generated method stub

    }
}
