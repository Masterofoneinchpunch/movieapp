package com.mooip.movie;

import com.mooip.util.ClobInputStream;
import com.mooip.util.LoggerAdapter;
import com.mooip.util.SQLSupport;
import com.mooip.util.SQLUtil;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class MovieDAO {
 
    private static final LoggerAdapter log = LoggerAdapter.getLogger(MovieDAO.class);

    private static final String MOVIE_YEAR_TOTAL = "SELECT year, COUNT(year) AS count, COUNT(myrating) AS watchedCount FROM movie GROUP BY year ORDER BY year";
    
    private static final String MOVIEREVIEWS = "select MOVIEREVIEWID, MOVIEID, REVIEW, LASTMODIFIED FROM MOVIEREVIEW WHERE MOVIEID = ?";

    private static final String MOVIEWATCHHISTORY =
        "select MOVIEWATCHHISTORYID, MOVIEID, WATCHED, WATCHTYPE, LASTMODIFIED from MOVIEWATCHHISTORY where MOVIEID = ? ORDER BY WATCHED DESC";
    
    private static final String MOVIE = 
        "select MOVIEID, LASTMODIFIED, LASTWATCHED, COUNTRY, LANGUAGE, MPAARATING, MYRATING, RUNTIME, TITLE, YEAR " +
        "from MOVIE where MOVIEID = ? ";
    
    private static final String INSERT_MOVIE =
    "INSERT INTO MOVIE (MOVIEID, TITLE, YEAR, RUNTIME, MPAARATING, MYRATING, LASTMODIFIED, COUNTRY, LANGUAGE, LASTWATCHED) " +
    "VALUES (?,?,?,?,?,?,?,?,?,?) ";

    private static final String UPDATE_MOVIE = "UPDATE MOVIE SET TITLE = ?, YEAR = ?, RUNTIME = ?, MPAARATING = ?, MYRATING = ?, COUNTRY = ?, LANGUAGE = ?, LASTWATCHED = ?, LASTMODIFIED = ? WHERE MOVIEID = ?";
    
    private static final String RECENTLYWATCHED =
        "select MOVIEID, LASTMODIFIED, LASTWATCHED, COUNTRY, LANGUAGE, MPAARATING, MYRATING, RUNTIME, TITLE, YEAR " +
        "from MOVIE " +
        "where LASTWATCHED is not null " +
        "order by LASTWATCHED desc ";

    private static final String GET_MOVIES =
        "select MOVIEID, LASTMODIFIED, LASTWATCHED, COUNTRY, LANGUAGE, MPAARATING, MYRATING, RUNTIME, TITLE, YEAR " +
        "from MOVIE " +
        "where UPPER(TITLE) LIKE UPPER(?) " + 
        "order by TITLE ";

    private static final String MOVIES_BY_COUNTRY =
        "select MOVIEID, LASTMODIFIED, LASTWATCHED, COUNTRY, LANGUAGE, MPAARATING, MYRATING, RUNTIME, TITLE, YEAR " +
        "from MOVIE " +
        "where UPPER(COUNTRY) LIKE UPPER(?) " + 
        "order by COUNTRY, REGEXP_REPLACE(TITLE, '^The ', '') ";
    
    private static final String MOVIES_BY_YEAR =
        "select MOVIEID, LASTMODIFIED, LASTWATCHED, COUNTRY, LANGUAGE, MPAARATING, MYRATING, RUNTIME, TITLE, YEAR " +
        "from MOVIE " +
        "where YEAR = ? " +
        "ORDER BY REGEXP_REPLACE(TITLE, '^The ', '') ";
    
    private MovieDAO () {        
    }
    
    public static MovieModel getMovie(Long movieId) {
        MovieModel movie = new MovieModel();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(MOVIE);
            SQLSupport.setLong(movieId, 1, stmt);
            result = stmt.executeQuery();
            if (result.next()) {
                movie.setTitle(SQLSupport.getString("TITLE", result));
                movie.setYear(SQLSupport.getInteger("YEAR", result));
                movie.setReview(getMovieReview(movieId));
                movie.setWatchHistory(getMovieWatchHistory(movieId));
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getMovie method." + se); 
        } finally {
            SQLUtil.close(result);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return movie;
    }

    public static List<MovieModel> getByYear(Integer year) {
        List<MovieModel> moviesByYear = new ArrayList();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(MOVIES_BY_YEAR);
            SQLSupport.setInteger(year, 1, stmt);
            
            results = stmt.executeQuery();
            while (results.next()) {
                MovieModel movie = getMovieModel(results);
                
                moviesByYear.add(movie);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getByYear method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
               
        return moviesByYear;
    }
    
    public static Long insertMovie(MovieModel movieModel) {
        log.warn("insert movie " + movieModel);
        movieModel.setLastModified(new Date());
        Connection conn = null;
        PreparedStatement stmt = null;
        
        //Get and use the biggest value from VIDEOMOVIE and MOVIE and then use the biggest of those two.
        Long videoMovieId = SQLUtil.getNextTablePrimaryKey("VIDEOMOVIE");
        Long movieId = SQLUtil.getNextTablePrimaryKey("MOVIE");
        if (videoMovieId > movieId) {
            movieId = videoMovieId;
        }
        log.warn("new pk: " + movieId);

        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(INSERT_MOVIE);
            SQLSupport.setLong(movieId, 1, stmt);
            SQLSupport.setString(movieModel.getTitle(), 2, stmt);
            SQLSupport.setInteger(movieModel.getYear(), 3, stmt);
            SQLSupport.setInteger(movieModel.getRuntime(), 4, stmt);
            SQLSupport.setString(movieModel.getMpaaRating(), 5, stmt);
            SQLSupport.setString(movieModel.getMyRating(), 6, stmt);
            SQLSupport.setTimestamp(movieModel.getLastModified(), 7, stmt);
            SQLSupport.setString(movieModel.getCountry(), 8, stmt);
            SQLSupport.setString(movieModel.getLanguage(), 9, stmt);
            SQLSupport.setDate(movieModel.getLastWatched(), 10, stmt);
            
            int resultCount = stmt.executeUpdate();
            if (resultCount == 0) {
                log.error("movieId of " + movieId + " failed to insert.");
                throw new RuntimeException("movieId of " + movieId + " failed to insert.");
            } 
            log.warn("ends insert");
            // create audit update 
        } catch(SQLException se) {
            log.error("SQLException in insertMovie method." + se);
            throw new RuntimeException("SQLException in insertMovie method." + se);
        } catch(Exception e) {
            log.error("We have a general exception: " + e);
            throw new RuntimeException("We have a general exception: " + e);
        } finally {
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return movieId;
    }
    
    public static void updateMovie(MovieModel movieModel) {
        movieModel.setLastModified(new Date());
        Long movieId = movieModel.getMovieId();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(UPDATE_MOVIE);
 
            SQLSupport.setString(movieModel.getTitle(), 1, stmt);
            SQLSupport.setInteger(movieModel.getYear(), 2, stmt);
            SQLSupport.setInteger(movieModel.getRuntime(), 3, stmt);
            SQLSupport.setString(movieModel.getMpaaRating(), 4, stmt);
            SQLSupport.setString(movieModel.getMyRating(), 5, stmt);
            SQLSupport.setString(movieModel.getCountry(), 6, stmt);
            SQLSupport.setString(movieModel.getLanguage(), 7, stmt);
            SQLSupport.setDate(movieModel.getLastWatched(), 8, stmt);
            SQLSupport.setTimestamp(movieModel.getLastModified(), 9, stmt);
            SQLSupport.setLong(movieId, 10, stmt);

            int resultCount = stmt.executeUpdate();
            if (resultCount == 0) {
                throw new IllegalArgumentException("movieId of " + movieId + " does not exist.");
            }            
        } catch(SQLException se) {
            log.error("SQLException in updateMovie method for movieId:" + movieId + ". " + se);
            throw new RuntimeException("SQLException in updateMovie method for movieId:" + movieId + ". " + se);
        } finally {
            SQLUtil.close(result);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }       
    }

    public static List<MovieModel> getByCountry(String country) {
        List<MovieModel> movies = new ArrayList();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(MOVIES_BY_COUNTRY);
            SQLSupport.setString("%" + country + "%", 1, stmt);
            
            results = stmt.executeQuery();
            while (results.next()) {
                MovieModel movie = getMovieModel(results);
                
                movies.add(movie);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getByCountry method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
               
        return movies;
    }
    
    public static List<MovieModel> getMovies(String title) {
        List<MovieModel> movies = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(GET_MOVIES);
            SQLSupport.setString("%" + title + "%", 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                MovieModel movie = getMovieModel(results);                
                movies.add(movie);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getMovies method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
       
        return movies;
    }
    
    public static List<MovieModel> getRecentlyWatched() {
        List<MovieModel> recentlyWatched = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(RECENTLYWATCHED);
            results = stmt.executeQuery();
            while (results.next()) {
                MovieModel movie = getMovieModel(results);                
                recentlyWatched.add(movie);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getRecentlyWatched method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
       
        return recentlyWatched;
    }

    public static List<Date> getMovieWatchHistory(Long movieId) {
        List<Date> watchHistory = new ArrayList();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(MOVIEWATCHHISTORY);
            SQLSupport.setLong(movieId, 1, stmt);
            result = stmt.executeQuery();
            while (result.next()) {
                watchHistory.add(SQLSupport.getDateFromSqlDate("WATCHED", result));
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getMovieWatchHistory method." + se); 
        } finally {
            SQLUtil.close(result);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        return watchHistory;
    }
    
    public static String getMovieReview(Long movieId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        String review = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(MOVIEREVIEWS);
            SQLSupport.setLong(movieId, 1, stmt);
            results = stmt.executeQuery();
            if (results.next()) {
                Clob clob = results.getClob("REVIEW");
                ClobInputStream cis = new ClobInputStream(clob);
                review = cis.toString();
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getMovieReview method." + se); 
        } catch (IOException io) {
            log.error(io);
            throw new RuntimeException("IOException in getMovieReview method." + io); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return review;
    }

    public static List<MovieYearCountModel> getMovieYearTotals() {
        List<MovieYearCountModel> movieYearCount = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(MOVIE_YEAR_TOTAL);
            results = stmt.executeQuery();
            while (results.next()) {
               movieYearCount.add(new MovieYearCountModel(SQLSupport.getInteger("YEAR", results), SQLSupport.getInteger("COUNT", results), SQLSupport.getInteger("WATCHEDCOUNT", results)));               
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getMovieWatchHistory method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return movieYearCount;
    }    
    
    private static MovieModel getMovieModel(ResultSet results) throws SQLException {
        MovieModel movie = new MovieModel();
        movie.setMovieId(SQLSupport.getLong("MOVIEID", results));
        movie.setLastModified(SQLSupport.getDateFromTimestamp("LASTMODIFIED", results));
        movie.setLastWatched(SQLSupport.getDateFromSqlDate("LASTWATCHED", results));
        movie.setCountry(SQLSupport.getString("COUNTRY", results));
        movie.setLanguage(SQLSupport.getString("LANGUAGE", results));
        movie.setMpaaRating(SQLSupport.getString("MPAARATING", results));
        movie.setMyRating(SQLSupport.getString("MYRATING", results));
        movie.setRuntime(SQLSupport.getInteger("RUNTIME", results));
        movie.setTitle(SQLSupport.getString("TITLE", results));
        movie.setYear(SQLSupport.getInteger("YEAR", results));
        movie.setReview(getMovieReview(movie.getMovieId()));
        
        return movie;
    }
}    