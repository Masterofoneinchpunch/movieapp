package com.mooip.reports;

import com.mooip.movie.MovieDAO;
import com.mooip.util.control.Handler;
import com.mooip.util.LoggerAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ReportsRequestHandler implements Handler {

    private static final LoggerAdapter log = LoggerAdapter.getLogger(ReportsRequestHandler.class);
    
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request);
        return FORWARD;
    }

    public void processRequest(HttpServletRequest request) {
        log.warn("reports request call");
        
        request.setAttribute("movieList", MovieDAO.getMovieYearTotals());
    }

}

