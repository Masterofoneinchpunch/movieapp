package com.mooip.util;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * A Utility class for the HttpServletRequest.
 * 
 * @author masterofoneinchpunch
 */
public final class RequestUtil {
    private static final LoggerAdapter log = LoggerAdapter.getLogger(RequestUtil.class);
    
    private RequestUtil() {
    }

    /**
     * Gets the primary key.
     * 
     * @param request The HttpServletRequest.
     * @param PRIMARY_KEY_NAME The name of the primary key.
     * @return a null if no primary key and a Long primary key if exists in request as a parameter.
     */
    public static Long getPrimaryKey(HttpServletRequest request, final String PRIMARY_KEY_NAME) {
        String primaryKey = request.getParameter(PRIMARY_KEY_NAME);
        if (primaryKey != null && primaryKey.equals("") == false) {
            return new Long(primaryKey);
        } else {
            return null;
        }
    }
    
    /**
     * Helper method to go through and log all the parameters.
     * 
     * @param request The HttpServletRequest.
     */
    public static void traverseParameterNames(HttpServletRequest request) {
        Enumeration parameterNames = request.getParameterNames();
        
        while (parameterNames.hasMoreElements()) {
            String parameterName = (String) parameterNames.nextElement();
            log.warn(parameterName + ": " + request.getParameter(parameterName));
        }        
    }    
}    