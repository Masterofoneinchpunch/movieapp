package com.mooip.movie;

import com.mooip.util.control.Handler;
import com.mooip.util.DateUtil;
import com.mooip.util.LoggerAdapter;
import com.mooip.util.NumberUtil;
import com.mooip.util.RequestUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class MovieRequestHandler implements Handler {
 
    private static final LoggerAdapter log = LoggerAdapter.getLogger(MovieRequestHandler.class);
    
    @Override
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request);
        return FORWARD;
    }
    
    @Override
    public void processRequest(HttpServletRequest request) {      
        String actionType = request.getParameter("actionType");
        log.warn("calling movie request handler: " + actionType + " " + request.getPathInfo());
        Long movieId = RequestUtil.getPrimaryKey(request, "movieId");
        if (actionType != null && actionType.equals("Save")) {
            RequestUtil.traverseParameterNames(request);
            MovieModel movieModel = new MovieModel();
            movieModel.setMovieId(movieId);
            movieModel.setTitle(request.getParameter("title"));
            movieModel.setYear(NumberUtil.parseInteger(request.getParameter("year")));
            movieModel.setRuntime(NumberUtil.parseInteger(request.getParameter("runtime")));
            movieModel.setCountry(request.getParameter("country"));
            movieModel.setLanguage(request.getParameter("language"));
            movieModel.setMpaaRating(request.getParameter("mpaaRating"));
            movieModel.setMyRating(request.getParameter("myRating"));
            movieModel.setLastWatched(DateUtil.parseDate(request.getParameter("lastWatched")));
            
            MovieDAO.updateMovie(movieModel);
        } else if (actionType != null && actionType.equals("Load")) {
            request.setAttribute("movie", MovieDAO.getMovie(movieId));        
        } else {
            final List<MovieModel> movies;
            
            final String year = request.getParameter("year");
            final String country = request.getParameter("country");
            if (year != null) {
                log.warn("Year: " + year);
                movies = MovieDAO.getByYear(new Integer(year));
                request.setAttribute("movies", movies);
                request.setAttribute("title", "Year: " + year);
            } else if (country != null) {
                movies = MovieDAO.getByCountry(country);
                request.setAttribute("movies", movies);
                request.setAttribute("title", "Country: " + country);
            } else {
                movies = MovieDAO.getRecentlyWatched();
                request.setAttribute("movies", movies);
            }
            request.setAttribute("movieCount", movies.size());
        }
    }    

}
