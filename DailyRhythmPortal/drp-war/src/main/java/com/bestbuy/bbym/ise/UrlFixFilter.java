package com.bestbuy.bbym.ise;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hack to fix sporadic 404 errors found when navigating from ETK to BEAST
 * portal.
 * <p>
 * Intercepts all the requests and checks whether the requested URL contains a
 * dot that causes Wicket to skip processing the request. <br/>
 * e.g.
 * {@code /drp-war/DailyRhythmPortal/.;jsessionid=TgqpPM1c5Gj22cgTQZkb4ytQGhtCYb3BgJsgnTbsbn1W2nXmSTQt!-606905053}
 */
public class UrlFixFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(UrlFixFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Strips the dot that's causing 404s from the request URL.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
	    throws IOException, ServletException {

	HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
	HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

	String requestUri = httpRequest.getRequestURI();

	if (requestUri.endsWith("DailyRhythmPortal/.")){
	    // Handle when accessed via Weblogic secure URL (jsessionid has
	    // already been stripped out
	    String fixedUrl = StringUtils.removeEnd(requestUri, ".");
	    logger.warn("Removed dot from end of URI, redirecting client to " + fixedUrl);
	    httpResponse.sendRedirect(fixedUrl);
	    return;
	}
	if (requestUri.contains("DailyRhythmPortal/.;jsessionid=")){
	    // Handle when accessed via Tomcat unsecured URL
	    String fixedUrl = requestUri.replace("DailyRhythmPortal/.;jsessionid=", "DailyRhythmPortal/;jsessionid=");
	    logger.warn("Redirecting client to " + fixedUrl);
	    httpResponse.sendRedirect(fixedUrl);
	    return;
	}

	filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
