package com.mooip.video.lending;

import com.mooip.person.PersonDAO;
import com.mooip.util.control.Handler;
import com.mooip.util.LoggerAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class LendingRequestHandler implements Handler {

    private static final LoggerAdapter log = LoggerAdapter.getLogger(LendingRequestHandler.class);
    
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request);
        return FORWARD;
    }

    public void processRequest(HttpServletRequest request) {
        log.warn("process request lending request");
        String lentTo = getPrimaryKey(request, "lentTo");
        if (lentTo != null) {
            request.setAttribute("personLentTo", PersonDAO.getPerson(lentTo));
            request.setAttribute("lendingHistory", LendingDAO.getLendingHistory(lentTo));
         } else {
           request.setAttribute("currentLending", LendingDAO.getCurrentLending());           
         }
    }

    private String getPrimaryKey(HttpServletRequest request, final String PRIMARY_KEY_NAME) {
        String primaryKey = request.getParameter(PRIMARY_KEY_NAME);
        if (primaryKey != null && primaryKey.equals("") == false) {
            return primaryKey;
        } else {
            return null;
        }
    }

}
