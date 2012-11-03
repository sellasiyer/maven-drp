package com.bestbuy.bbym.ise.drp.aop;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.error.ErrorMessageEnum;
import com.bestbuy.bbym.ise.drp.error.IseRuntimeException;
import com.bestbuy.bbym.ise.drp.error.IseServiceError;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Aspect
public class ServiceExceptionAspect {

    private static Logger logger = LoggerFactory.getLogger(ServiceExceptionAspect.class);

    @Around("testBeanExecution()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
	Object ret = null;
	try{
	    ret = pjp.proceed();
	}catch(Throwable t){

	    // TODO Remove once we've moved away from old exception hierarchy.
	    if (t instanceof IseBusinessException || t instanceof ServiceException){
		throw t;
	    }

	    if (t instanceof IseServiceError){
		throw t;
	    }else if (t instanceof IseRuntimeException){
		logger.error("Error occurred", t);
		throw t;
	    }
	    logger.error("Error occurred", t);
	    throw new IseRuntimeException(ErrorMessageEnum.UNKNOWN_ERROR, t);

	}
	return ret;

    }

    @PostConstruct
    public void initialize() {
	logger.debug("Initializing ServiceExceptionAspect .... ");
    }
}
