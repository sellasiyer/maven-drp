package com.bestbuy.bbym.ise.drp.service;

import java.util.List;
import java.util.Map;

import com.bestbuy.bbym.ise.drp.domain.Appointment;
import com.bestbuy.bbym.ise.drp.domain.AppointmentSlots;
import com.bestbuy.bbym.ise.drp.domain.SchedulerRequest;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a194869
 */
public interface SchedulerService {

    public Map<String, String> getDepartments(String storeId) throws ServiceException, IseBusinessException;

    public Map<String, String> getAppointmentTypes(String storeId, String departmentId) throws ServiceException,
	    IseBusinessException;

    public Map<String, String> getReasons(String appointmentTypeId) throws ServiceException, IseBusinessException;

    public AppointmentSlots getAppointmentSlots(SchedulerRequest shedulerReq) throws ServiceException,
	    IseBusinessException;

    public boolean reserveSlot(SchedulerRequest shedulerReq) throws ServiceException, IseBusinessException;

    public String createAppointment(SchedulerRequest shedulerReq) throws ServiceException, IseBusinessException;

    public Map<String, String> getStatuses(String storeId) throws ServiceException, IseBusinessException;

    public List<Appointment> searchAppointmentByCriteria(SchedulerRequest shedulerReq) throws ServiceException,
	    IseBusinessException;

    public String modifyAppointment(SchedulerRequest shedulerReq) throws ServiceException, IseBusinessException;

    public Map<String, String> updateStatus(String userId, Map<String, String> appointmentStatusMap)
	    throws ServiceException, IseBusinessException;

    public Appointment searchAppointmentById(String appointmentId, String storeId) throws ServiceException,
	    IseBusinessException;

}
