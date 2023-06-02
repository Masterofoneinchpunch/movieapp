package com.mooip.video;

import com.mooip.collectionType.CollectionTypeRequestHandler;
import com.mooip.genre.GenreRequestHandler;
import com.mooip.movie.MovieRequestHandler;
import com.mooip.movie.MovieSearchRequestHandler;
import com.mooip.portal.PortalRequestHandler;
import com.mooip.reports.ReportsRequestHandler;
import com.mooip.studio.StudioRequestHandler;
import com.mooip.util.control.Handler;
import com.mooip.util.LoggerAdapter;
import com.mooip.utilities.InsertStatementsHandler;
import com.mooip.video.lending.LendingRequestHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for Controlling flow also known as a Front Controller.
 *
 * @author  Shawn McKenna
 * @version 1.0
 */
public final class ControllerServlet extends HttpServlet {
    
    private static final LoggerAdapter log = LoggerAdapter.getLogger(ControllerServlet.class);
    
    private static final VideoRequestHandler videoRequestHandler = new VideoRequestHandler();
    private static final CollectionTypeRequestHandler collectionTypeRequestHandler = new CollectionTypeRequestHandler();
    private static final LendingRequestHandler lendingRequestHandler = new LendingRequestHandler();
    private static final MovieRequestHandler movieRequestHandler = new MovieRequestHandler();
    private static final MovieSearchRequestHandler moviesSearchRequestHandler = new MovieSearchRequestHandler();
    private static final InsertStatementsHandler insertStatementsHandler = new InsertStatementsHandler();
    private static final PortalRequestHandler portalRequestHandler = new PortalRequestHandler();
    private static final StudioRequestHandler studioRequestHandler = new StudioRequestHandler();
    private static final GenreRequestHandler genreRequestHandler = new GenreRequestHandler();
    private static final ReportsRequestHandler reportsRequestHandler = new ReportsRequestHandler();
    private static final VideoExtraRequestHandler videoExtraRequestHandler = new VideoExtraRequestHandler();
    private static final Map<String, Handler> requestHandlers = new HashMap();
    
    static {
        requestHandlers.put("/video.jsp", videoRequestHandler);
        requestHandlers.put("/video/extra.jsp", videoExtraRequestHandler);
        requestHandlers.put("/videoCollection.jsp", videoRequestHandler);
        requestHandlers.put("/videoSummary.jsp", videoRequestHandler);
        requestHandlers.put("/videoCollectionType.jsp", collectionTypeRequestHandler);
        requestHandlers.put("/currentLending.jsp", lendingRequestHandler);
        requestHandlers.put("/lendingHistory.jsp", lendingRequestHandler);
        requestHandlers.put("/utilities/insertStatements.jsp", insertStatementsHandler);
        requestHandlers.put("/portal.jsp", portalRequestHandler);
        requestHandlers.put("/studio.jsp", studioRequestHandler);
        requestHandlers.put("/genre.jsp", genreRequestHandler);
        requestHandlers.put("/recentlyWatched.jsp", movieRequestHandler);
        requestHandlers.put("/movie/movie.jsp", movieRequestHandler);
        requestHandlers.put("/movie/movies.jsp", movieRequestHandler); 
        requestHandlers.put("/movie/movieReview.jsp", movieRequestHandler);
        requestHandlers.put("/movie/searchMovie.jsp", moviesSearchRequestHandler);
        requestHandlers.put("/reports/yearlyMovieTotals.jsp", reportsRequestHandler);
    }
    
    /**
     * Gets the servlet information.
     *
     * @return servletInfo The information about this servlet.
     */
    @Override
    public String getServletInfo() {
        return "This is a servlet for the Front Controller (MVC).";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String selectedURL = request.getPathInfo();
        Handler handler = requestHandlers.get(selectedURL);
        final boolean forward = (handler != null) ? handler.processRequest(request, response) : true;       
        if (forward) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(selectedURL);
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            } else { 
                response.getWriter().println(selectedURL);
            }
        }
    }
    
}
