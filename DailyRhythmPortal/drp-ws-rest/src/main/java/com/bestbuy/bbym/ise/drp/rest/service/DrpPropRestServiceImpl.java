package com.bestbuy.bbym.ise.drp.rest.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;

/**
 * 
 * @author a904002
 */
@Path("/property")
@Component
@Scope("singleton")
public class DrpPropRestServiceImpl implements DrpPropRestService {

    private static final Logger log = LoggerFactory.getLogger(DrpPropRestServiceImpl.class);

    @Autowired
    private DrpPropertyService drpPropertyService;

    @Path("/refresh")
    @POST
    @Override
    public void refreshProperties() {
	drpPropertyService.refreshPropertyMap();
    }
}
