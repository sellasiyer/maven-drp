package com.bestbuy.bbym.ise.drp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.Appointment;
import com.bestbuy.bbym.ise.drp.domain.AppointmentSlots;
import com.bestbuy.bbym.ise.drp.domain.SchedulerRequest;
import com.bestbuy.bbym.ise.drp.sao.SchedulerSao;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Responsible for Appointment Scheduler Service
 * 
 * @author a194869
 */
@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

    private static Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    @Autowired
    private SchedulerSao schedulerSao;

    @Override
    public Map<String, String> getDepartments(String storeId) throws ServiceException, IseBusinessException {
	return schedulerSao.getDepartments(storeId);
    }

    @Override
    public AppointmentSlots getAppointmentSlots(SchedulerRequest shedulerReq) throws ServiceException,
	    IseBusinessException {
	return schedulerSao.getAppointmentSlots(shedulerReq.getUser().getUserId(), shedulerReq.getUser().getStoreId(),
		shedulerReq.getDepartment().keySet().iterator().next(), shedulerReq.getStartDate(), shedulerReq
			.getEndDate(), shedulerReq.getAppointmentType().keySet().iterator().next());
    }

    @Override
    public boolean reserveSlot(SchedulerRequest shedulerReq) throws ServiceException, IseBusinessException {

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyyhh:mma");
	Date date = shedulerReq.getAppointmentTime().keySet().iterator().next();
	String strDate = dateFormat.format(date);
	String strTime = shedulerReq.getAppointmentTime().get(date).getTime();
	Date appointmentDate;
	try{
	    appointmentDate = dateTimeFormat.parse(strDate + strTime);
	    return schedulerSao.reserveSlot(shedulerReq.getUser().getUserId(), shedulerReq.getUser().getStoreId(),
		    shedulerReq.getDepartment().keySet().iterator().next(), shedulerReq.getAppointmentType().keySet()
			    .iterator().next(), appointmentDate);
	}catch(ParseException e){
	    logger.info("Unable to parse date from appointment " + e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, "Appointment has Invalid date format ");
	}

    }

    @Override
    public String createAppointment(SchedulerRequest shedulerReq) throws ServiceException, IseBusinessException {
	return schedulerSao.createAppointment(shedulerReq);
    }

    @Override
    public Map<String, String> getStatuses(String storeId) throws ServiceException, IseBusinessException {
	return schedulerSao.getStatuses(storeId);
    }

    @Override
    public List<Appointment> searchAppointmentByCriteria(SchedulerRequest shedulerReq) throws ServiceException,
	    IseBusinessException {
	return schedulerSao.searchAppointmentByCriteria(shedulerReq);
    }

    @Override
    public Appointment searchAppointmentById(String appointmentId, String storeId) throws ServiceException,
	    IseBusinessException {
	return schedulerSao.searchAppointmentById(appointmentId, storeId);
    }

    @Override
    public String modifyAppointment(SchedulerRequest shedulerReq) throws ServiceException, IseBusinessException {
	return schedulerSao.modifyAppointment(shedulerReq);
    }

    @Override
    public Map<String, String> updateStatus(String userId, Map<String, String> appointmentStatusMap)
	    throws ServiceException, IseBusinessException {
	Map<String, String> returnMap = new HashMap<String, String>();
	Set<String> appointmentIDs = appointmentStatusMap.keySet();
	for(String appointmentId: appointmentIDs){
	    returnMap.put(appointmentId, schedulerSao.updateStatus(userId, appointmentId, appointmentStatusMap
		    .get(appointmentId)));
	}
	return returnMap;
    }

    @Override
    public Map<String, String> getAppointmentTypes(String storeId, String departmentId) throws ServiceException,
	    IseBusinessException {
	return schedulerSao.getAppointmentTypes(storeId, departmentId);
    }

    @Override
    public Map<String, String> getReasons(String appointmentTypeId) throws ServiceException, IseBusinessException {
	return schedulerSao.getReasons(appointmentTypeId);
    }

}
