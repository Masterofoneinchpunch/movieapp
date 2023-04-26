package com.mooip.movie;

import com.mooip.util.control.Handler;
import com.mooip.util.LoggerAdapter;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class MovieSearchRequestHandler implements Handler {
 
    private static final LoggerAdapter log = LoggerAdapter.getLogger(MovieSearchRequestHandler.class);
    
    @Override
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request);
        return FORWARD;
    }
    
    @Override
    public void processRequest(HttpServletRequest request) {      
        String actionType = request.getParameter("actionType");
        log.warn("calling movie search request handler: " + actionType + " " + request.getPathInfo());
        
        if (actionType != null && actionType.equals("Search")) {
             String title = request.getParameter("title");
             log.warn("calling search: " + title);
             if (title != null) {
                 title = title.trim();
             }
             final List<MovieModel> movies = MovieDAO.getMovies(title);
             request.setAttribute("movies", movies); 
             request.setAttribute("movieCount", movies.size());
        } 
    }
}

