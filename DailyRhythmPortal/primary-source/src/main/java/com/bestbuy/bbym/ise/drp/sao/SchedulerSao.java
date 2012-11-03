package com.bestbuy.bbym.ise.drp.sao;

import java.util.Date;
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
public interface SchedulerSao {

    /**
     * Gets the departments for the given store.
     * 
     * @param storeId
     *            the store Id
     * @return a {@code Map} whose keys contain the department Id and values
     *         contain the department name
     */
    public Map<String, String> getDepartments(String storeId) throws ServiceException, IseBusinessException;

    /**
     * Gets the appointment types.
     * 
     * @param storeId
     *            the store Id
     * @param departmentId
     *            the department Id
     * @return a {@code Map} whose keys contain the appointment type Id and
     *         values contain the appointment type name
     */
    public Map<String, String> getAppointmentTypes(String storeId, String departmentId) throws ServiceException,
	    IseBusinessException;

    /**
     * Gets the appointment reasons.
     * 
     * @param appointmentTypeId
     *            the appointment type
     * @return a {@code Map} whose keys contain the appointment reason Id and
     *         values contain the appointment reason name
     */
    public Map<String, String> getReasons(String appointmentTypeId) throws ServiceException, IseBusinessException;

    public AppointmentSlots getAppointmentSlots(String userId, String storeId, String departmentId, Date startDate,
	    Date endDate, String reasonTypeId) throws ServiceException, IseBusinessException;

    public boolean reserveSlot(String userId, String storeId, String departmentId, String reasonTypeId,
	    Date appointmentDateTime) throws ServiceException, IseBusinessException;

    public String createAppointment(SchedulerRequest shedulerReq) throws ServiceException, IseBusinessException;

    /**
     * Gets the appointment statuses.
     * 
     * @param storeId
     *            the storeId
     * @return a {@code Map} whose keys contain the appointment status Id and
     *         values contain the appointment status name
     */
    public Map<String, String> getStatuses(String storeId) throws ServiceException, IseBusinessException;

    public List<Appointment> searchAppointmentByCriteria(SchedulerRequest shedulerReq) throws ServiceException,
	    IseBusinessException;

    public String modifyAppointment(SchedulerRequest shedulerReq) throws ServiceException, IseBusinessException;

    public String updateStatus(String userId, String appointmentId, String statusId) throws ServiceException,
	    IseBusinessException;

    public Appointment searchAppointmentById(String appointmentId, String storeId) throws ServiceException,
	    IseBusinessException;
}
