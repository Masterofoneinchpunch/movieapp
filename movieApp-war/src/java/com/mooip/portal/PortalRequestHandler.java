package com.mooip.portal;

import com.mooip.util.control.Handler;
import com.mooip.person.PersonDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class PortalRequestHandler implements Handler {
 
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request);
        return FORWARD;
    }
    
    public void processRequest(HttpServletRequest request) {
        request.setAttribute("futureBirthdays", PersonDAO.getFutureBirthdays());
    }

}
