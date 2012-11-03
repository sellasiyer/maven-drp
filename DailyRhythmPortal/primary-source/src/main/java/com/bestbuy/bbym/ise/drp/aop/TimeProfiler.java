package com.bestbuy.bbym.ise.drp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.util.StopWatch;

import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

public class TimeProfiler implements Ordered {

    private Logger logger = LoggerFactory.getLogger(TimeProfiler.class);
    private int order;

    public int getOrder() {
	return this.order;
    }

    public void setOrder(int order) {
	this.order = order;
    }

    public Object profile(ProceedingJoinPoint call) throws Throwable {
	Object returnValue;
	StopWatch clock = new StopWatch(getClass().getName());
	try{
	    clock.start(call.toShortString());
	    returnValue = call.proceed();
	}finally{
	    clock.stop();
	    logger.debug(DrpConstants.TIME_PROFILER + call.toShortString() + " - " + clock.getTotalTimeSeconds()
		    + " Seconds");
	    if (clock.getTotalTimeSeconds() > 60){
		logger.warn(DrpConstants.TIME_PROFILER + call.toShortString() + " - " + clock.getTotalTimeSeconds()
			+ " Seconds");
	    }
	}
	return returnValue;
    }
}
