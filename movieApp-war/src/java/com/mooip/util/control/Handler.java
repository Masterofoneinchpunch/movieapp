package com.mooip.util.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Handler {
    public final static boolean FORWARD = true;
    
    public void processRequest(HttpServletRequest request);
    
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response);
}
