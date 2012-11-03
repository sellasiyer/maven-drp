package com.bestbuy.bbym.ise.drp.service;

import java.io.ByteArrayOutputStream;

import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * 
 * @author a904002
 */
public interface EmailService {

    public boolean sendEmail(ByteArrayOutputStream pdfOutputStream, String emailAddress) throws ServiceException;
}
