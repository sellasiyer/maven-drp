package com.bestbuy.bbym.ise.drp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.exception.IseBaseExceptionTypeEnum;
import com.bestbuy.bbym.ise.exception.IseExceptionCategoryEnum;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 *
 * @author a904002
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private DrpPropertyService drpPropertyService;
    @Autowired
    private JavaMailSender mailSender;
    private MimeMessageHelper messageHelper;
    private Resource emailBody;
    private Resource bottom;
    private Resource top;
    private Resource logo;
    private Resource twitter;
    private Resource share;
    private Resource facebook;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl() {

	emailBody = new ClassPathResource("/emailBody.html");
	bottom = new ClassPathResource("images/BottomBlueBar2.png");
	logo = new ClassPathResource("images/BBYMlogot16b13.png");
	top = new ClassPathResource("images/TopperBlueBar2.png");
	twitter = new ClassPathResource("images/twitter.png");
	share = new ClassPathResource("images/share.png");
	facebook = new ClassPathResource("images/facebook.png");
    }

    @Override
    public boolean sendEmail(ByteArrayOutputStream pdfOutputStream, String emailAddress) throws ServiceException {
	try{
	    DataSource pdfDataSource = new ByteArrayDataSource(pdfOutputStream.toByteArray(), "application/pdf");
	    messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(),
		    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
	    messageHelper.addAttachment("RecommendationSheet.pdf", pdfDataSource);
	    messageHelper.setTo(emailAddress);
	    messageHelper.setFrom(drpPropertyService.getProperty("BEAST_PORTAL_EMAIL_SENDER"), drpPropertyService
		    .getProperty("BEAST_PORTAL_EMAIL_SENDER_NAME"));

	    messageHelper.setSubject(drpPropertyService.getProperty("BEAST_PORTAL_RECSHEET_EMAIL_SUBJECT"));
	    messageHelper.setText(IOUtils.toString(emailBody.getInputStream()), true);

	    messageHelper.addInline("bottomBlue", bottom);
	    messageHelper.addInline("bbLogo", logo);
	    messageHelper.addInline("topBlue", top);
	    messageHelper.addInline("twitter", twitter);
	    messageHelper.addInline("share", share);
	    messageHelper.addInline("facebook", facebook);

	    mailSender.send(messageHelper.getMimeMessage());
	}catch(IOException ex){

	    ServiceException se = new ServiceException(IseExceptionCodeEnum.GeneralSystem);
	    se.setCategory(IseExceptionCategoryEnum.Service);
	    se.setExceptionType(IseBaseExceptionTypeEnum.Recoverable);
	    se.setStackTrace(ex.getStackTrace());
	    throw se;
	}catch(MessagingException ex){
	    ServiceException se = new ServiceException(IseExceptionCodeEnum.GeneralSystem);
	    se.setCategory(IseExceptionCategoryEnum.Service);
	    se.setExceptionType(IseBaseExceptionTypeEnum.Recoverable);
	    se.setStackTrace(ex.getStackTrace());
	    throw se;
	}
	return true;
    }

}
