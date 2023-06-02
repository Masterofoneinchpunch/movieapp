package com.mooip.video;

import com.mooip.movie.MovieDAO;
import com.mooip.movie.MovieModel;
import com.mooip.movie.trailer.TrailerModel;
import com.mooip.util.SQLSupport;
import com.mooip.util.SQLUtil;
import com.mooip.util.ClobInputStream;
import com.mooip.util.LoggerAdapter;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class VideoDAO {
 
    private static final LoggerAdapter log = LoggerAdapter.getLogger(VideoDAO.class);
    private static final String DELETE_USER_ACTION = "D";
    private static final String INSERT_USER_ACTION = "I";
    private static final String VIDEO = "select VIDEOID, TITLE, VIDEOTYPE, YEAROFRELEASE, RATING, REGION, LABEL, COMMENTS, LASTMODIFIED, LOCATION, COST, OBTAINEDFROM, OBTAINEDTYPE, SLIPCOVERYN, SPINENUMBER, OOPYN, DISCCOUNT, CASETYPE, TOTALMINUTES from VIDEO where VIDEOID = ?";
    private static final String INSERT_VIDEO = 
        "INSERT INTO VIDEO (VIDEOID, TITLE, VIDEOTYPE, YEAROFRELEASE, RATING, REGION, LABEL, COMMENTS, LASTMODIFIED, COST, OBTAINEDFROM, OBTAINEDTYPE, SLIPCOVERYN, OOPYN, DISCCOUNT, CASETYPE, TOTALMINUTES, SPINENUMBER) " +
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";  
    private static final String INSERT_VIDEO_AUDIT = 
        "INSERT INTO VIDEOAUDIT (VIDEOID, TITLE, VIDEOTYPE, YEAROFRELEASE, RATING, REGION, LABEL, COMMENTS, LASTMODIFIED, COST, OBTAINEDFROM, OBTAINEDTYPE, SLIPCOVERYN, OOPYN, DISCCOUNT, CASETYPE, TOTALMINUTES, SPINENUMBER, USERID, AUDITDATE, AUDITACTION) " +
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";  
    private static final String INSERT_COMMENTARY =
        "INSERT INTO VIDEOCOMMENTARY (VIDEOCOMMENTARYID, VIDEOMOVIEID, COMMENTATORS, LASTMODIFIED) VALUES (?,?,?,?)";
    private static final String INSERT_VIDEOEXTRA =
        "INSERT INTO VIDEOEXTRA (VIDEOEXTRAID, VIDEOID, TITLE, DESCRIPTION, LASTMODIFIED) VALUES (?,?,?,?,?)";
    private static final String UPDATE_VIDEOEXTRA =
        "UPDATE VIDEOEXTRA SET TITLE = ?, DESCRIPTION = ?, LASTMODIFIED = ? WHERE VIDEOEXTRAID = ?";
    private static final String INSERT_VIDEOMOVIE =
        "INSERT INTO VIDEOMOVIE (VIDEOMOVIEID, VIDEOID, MOVIEID, LASTMODIFIED) VALUES (?,?,?,?)";   
    private static final String UPDATE_VIDEO = "UPDATE VIDEO SET TITLE = ?, VIDEOTYPE = ?, YEAROFRELEASE = ?, RATING = ?, REGION = ?, LABEL = ?, COMMENTS = ?, LASTMODIFIED = ?, COST = ?, OBTAINEDFROM = ?, OBTAINEDTYPE = ?,  SLIPCOVERYN = ?, OOPYN = ?, DISCCOUNT = ?, CASETYPE = ?, SPINENUMBER = ?, LOCATION = ?, TOTALMINUTES = ? WHERE VIDEOID = ?";
    private static final String VIDEO_LIST = "select VIDEOID, TITLE, VIDEOTYPE, YEAROFRELEASE, RATING, REGION, LABEL, COMMENTS, LASTMODIFIED, LOCATION, COST, OBTAINEDFROM, OBTAINEDTYPE, SLIPCOVERYN, OOPYN, DISCCOUNT from VIDEO ORDER BY REGEXP_REPLACE(TITLE, '^The ', '')";
    private static final String VIDEO_MOVIES =
        "select VM.VIDEOMOVIEID, M.MOVIEID, TITLE, YEAR, M.LASTMODIFIED, COUNTRY, LANGUAGE, MPAARATING, MYRATING, RUNTIME, M.LASTWATCHED " +
        "FROM VIDEOMOVIE VM, MOVIE M " +
        "WHERE VM.MOVIEID = M.MOVIEID " +
        "AND VM.VIDEOID = ? ORDER BY TITLE";

    private static final String COLLECTION = 
        "SELECT col.collectionName " + 
        "FROM videoCollection vc " + 
        "INNER JOIN collection col ON col.collectionId = vc.collectionId " +
        "WHERE vc.videoId = ? ";
               
    private static final String VIDEO_COLLECTION_TYPE =
        "select VIDEO.VIDEOID, VIDEO.TITLE, VIDEOTYPE, SPINENUMBER, YEAROFRELEASE, LABEL, OOPYN, DISCCOUNT " + 
        "from VIDEO VIDEO, VIDEOCOLLECTION VC, COLLECTION COL " +
        "where " +
        "VIDEO.VIDEOID = VC.VIDEOID " +
        "AND VC.COLLECTIONID = COL.COLLECTIONID " +
        "AND UPPER(COLLECTIONNAME) = ? " +
        "ORDER BY TO_NUMBER(REGEXP_SUBSTR(SPINENUMBER,'(^[[:digit:]]*)')) ";            

    private static final String VIDEO_LABEL =
        "select VIDEO.VIDEOID, VIDEO.TITLE, VIDEOTYPE, SPINENUMBER, YEAROFRELEASE, LABEL, OOPYN, DISCCOUNT " + 
        "from VIDEO VIDEO " +
        "where UPPER(LABEL) = ? " +
        "ORDER BY TO_NUMBER(REGEXP_SUBSTR(SPINENUMBER,'(^[[:digit:]]*)')), VIDEO.TITLE";

    private static final String VIDEO_BOXSET =
        "SELECT boxSetId, vbs.lastModified, videoBoxSetId, video.videoId, title " +
        "FROM videoBoxSet vbs " +
        "INNER JOIN video" +
        "    ON video.videoId = vbs.videoId " + 
        "WHERE boxSetId = ?";
    
    private static final String VIDEO_STUDIOS =
        "select DISTINCT VIDEO.* FROM VIDEO, VIDEOMOVIE, MOVIE, MOVIESTUDIO, STUDIO " + 
        "WHERE VIDEO.VIDEOID = VIDEOMOVIE.VIDEOID " +
        "AND VIDEOMOVIE.MOVIEID = MOVIE.MOVIEID " +
        "AND MOVIE.MOVIEID = MOVIESTUDIO.MOVIEID " + 
        "AND MOVIESTUDIO.STUDIOID = STUDIO.STUDIOID " +
        "AND UPPER(STUDIO) = ? " +
        "ORDER BY REGEXP_REPLACE(VIDEO.TITLE, '^The ', '')";

    private static final String VIDEO_GENRE =
        "select DISTINCT VIDEO.* FROM VIDEO, VIDEOMOVIE, MOVIE, MOVIEGENRE, GENRE " + 
        "WHERE VIDEO.VIDEOID = VIDEOMOVIE.VIDEOID " +
        "AND VIDEOMOVIE.MOVIEID = MOVIE.MOVIEID " +
        "AND MOVIE.MOVIEID = MOVIEGENRE.MOVIEID " + 
        "AND MOVIEGENRE.GENREID = GENRE.GENREID " +
        "AND UPPER(GENRE) = ? " +
        "ORDER BY REGEXP_REPLACE(VIDEO.TITLE, '^The ', '')";
    
    private static final String COMMENTARIES = "select COMMENTATORS from VIDEOCOMMENTARY WHERE VIDEOMOVIEID = ?";    
    private static final String VIDEOMISC = "select VIDEOMISCID, VIDEOMOVIEID, VIDEOMISC, WARNINGYN, LASTMODIFIED FROM VIDEOMISC WHERE VIDEOMOVIEID = ?";
    private static final String TRAILERS = "select TRAILERID, MOVIEID, EMBEDCODE, DESCRIPTION, LASTMODIFIED FROM TRAILER WHERE MOVIEID = ?";
    private static final String VIDEOEXTRAS = "select VIDEOEXTRAID, VIDEOID, TITLE, DESCRIPTION, LASTMODIFIED FROM VIDEOEXTRA WHERE VIDEOID = ?";
    private static final String MOVIEREVIEWS = "select MOVIEREVIEWID, MOVIEID, REVIEW, LASTMODIFIED FROM MOVIEREVIEW WHERE MOVIEID = ?";
    private static final String CURRENT_LENDING = "select LENTTO, LENDINGDATE, LASTMODIFIED from LENDINGHISTORY WHERE VIDEOID = ? AND RETURNDATE IS NULL AND RETURNEDYN <> 1";
    private static final String DELETE_VIDEO = "DELETE FROM video WHERE videoid = ?";
    
    private VideoDAO () {        
    }
    
    public static VideoModel getVideo(Long videoId) {
        VideoModel videoModel = new VideoModel();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(VIDEO);
            SQLSupport.setLong(videoId, 1, stmt);
            result = stmt.executeQuery();
            if (result.next()) {
                videoModel.setVideoId(SQLSupport.getLong("VIDEOID", result));
                videoModel.setTitle(SQLSupport.getString("TITLE", result));
                videoModel.setLabel(SQLSupport.getString("LABEL", result));
                videoModel.setLocation(SQLSupport.getString("LOCATION", result));
                videoModel.setLastModified(SQLSupport.getDateFromTimestamp("LASTMODIFIED", result));
                videoModel.setYearOfRelease(SQLSupport.getInteger("YEAROFRELEASE", result));
                videoModel.setComments(SQLSupport.getString("COMMENTS", result));
                videoModel.setVideoType(SQLSupport.getString("VIDEOTYPE", result));
                videoModel.setCost(SQLSupport.getFloat("COST", result));
                videoModel.setObtainedFrom(SQLSupport.getString("OBTAINEDFROM", result));
                videoModel.setObtainedType(SQLSupport.getString("OBTAINEDTYPE", result));
                videoModel.setSlipcoverYn(SQLSupport.getBoolean("SLIPCOVERYN", result));
                videoModel.setRegion(SQLSupport.getString("REGION", result));
                videoModel.setOopYn(SQLSupport.getBoolean("OOPYN", result));
                videoModel.setCaseType(SQLSupport.getString("CASETYPE", result));
                videoModel.setDiscCount(SQLSupport.getInteger("DISCCOUNT", result));
                videoModel.setSpineNumber(SQLSupport.getString("SPINENUMBER", result));
                videoModel.setCurrentlyLentTo(getCurrentlyLentTo(videoId));
                videoModel.setTotalMinutes(SQLSupport.getInteger("TOTALMINUTES", result));
                videoModel.setVideoBoxSet(getVideosByBoxSet(videoId));
                videoModel.setVideoMovies(getVideoMovies(videoId));
                videoModel.setVideoExtras(getVideoExtras(videoId));
                videoModel.setVideoCollections(getVideoCollections(videoId));
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getVideo method." + se); 
        } finally {
            SQLUtil.close(result);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return videoModel;
    }

    public static Long insertVideo(VideoModel videoModel) {
        videoModel.setLastModified(new Date());
        Connection conn = null;
        PreparedStatement stmt = null;
        Long videoId = SQLUtil.getNextTablePrimaryKey("VIDEO");
        videoModel.setVideoId(videoId);
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(INSERT_VIDEO);
            SQLSupport.setLong(videoId, 1, stmt);
            SQLSupport.setString(videoModel.getTitle(), 2, stmt);
            SQLSupport.setString(videoModel.getVideoType(), 3, stmt);
            SQLSupport.setInteger(videoModel.getYearOfRelease(), 4, stmt);
            SQLSupport.setString(videoModel.getRating(), 5, stmt);
            SQLSupport.setString(videoModel.getRegion(), 6, stmt);
            SQLSupport.setString(videoModel.getLabel(), 7, stmt);
            SQLSupport.setString(videoModel.getComments(), 8, stmt);
            SQLSupport.setTimestamp(videoModel.getLastModified(), 9, stmt);
            SQLSupport.setFloat(videoModel.getCost(), 10, stmt);
            SQLSupport.setString(videoModel.getObtainedFrom(), 11, stmt);
            SQLSupport.setString(videoModel.getObtainedType(), 12, stmt);
            SQLSupport.setBoolean(videoModel.getSlipcoverYn(), 13, stmt);
            SQLSupport.setBoolean(videoModel.getOopYn(), 14, stmt);
            SQLSupport.setInteger(videoModel.getDiscCount(), 15, stmt);
            SQLSupport.setString(videoModel.getCaseType(), 16, stmt);
            SQLSupport.setInteger(videoModel.getTotalMinutes(), 17, stmt);
            SQLSupport.setString(videoModel.getSpineNumber(), 18, stmt);
                        
            int resultCount = stmt.executeUpdate();
            if (resultCount == 0) {
                log.error("videoId of " + videoId + " failed to insert.");
                throw new RuntimeException("videoId of " + videoId + " failed to insert.");
            } 
            log.warn("ends insert");
            // create audit update 
            
            saveVideoMovies(videoModel);
            insertExtras(videoModel.getVideoExtras(), videoId);
        } catch(SQLException se) {
            log.error("SQLException in insertVideo method." + se);
            throw new RuntimeException("SQLException in insertVideo method." + se);
        } catch(Exception e) {
            log.error("We have a general exception: " + e);
            throw new RuntimeException("We have a general exception: " + e);
        } finally {
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return videoId;
    }

    public static void insertVideoAudit(VideoModel videoModel, final String userId, final String auditAction) {
        videoModel.setLastModified(new Date());
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(INSERT_VIDEO_AUDIT);
            SQLSupport.setLong(videoModel.getVideoId(), 1, stmt);
            SQLSupport.setString(videoModel.getTitle(), 2, stmt);
            SQLSupport.setString(videoModel.getVideoType(), 3, stmt);
            SQLSupport.setInteger(videoModel.getYearOfRelease(), 4, stmt);
            SQLSupport.setString(videoModel.getRating(), 5, stmt);
            SQLSupport.setString(videoModel.getRegion(), 6, stmt);
            SQLSupport.setString(videoModel.getLabel(), 7, stmt);
            SQLSupport.setString(videoModel.getComments(), 8, stmt);
            SQLSupport.setTimestamp(videoModel.getLastModified(), 9, stmt);
            SQLSupport.setFloat(videoModel.getCost(), 10, stmt);
            SQLSupport.setString(videoModel.getObtainedFrom(), 11, stmt);
            SQLSupport.setString(videoModel.getObtainedType(), 12, stmt);
            SQLSupport.setBoolean(videoModel.getSlipcoverYn(), 13, stmt);
            SQLSupport.setBoolean(videoModel.getOopYn(), 14, stmt);
            SQLSupport.setInteger(videoModel.getDiscCount(), 15, stmt);
            SQLSupport.setString(videoModel.getCaseType(), 16, stmt);
            SQLSupport.setInteger(videoModel.getTotalMinutes(), 17, stmt);
            SQLSupport.setString(videoModel.getSpineNumber(), 18, stmt);
            SQLSupport.setString(userId, 19,stmt);
            SQLSupport.setTimestamp(videoModel.getLastModified(), 20, stmt);
            SQLSupport.setString(auditAction, 21,stmt);
            
            int resultCount = stmt.executeUpdate();
            if (resultCount == 0) {
                log.error("videoId of " + videoModel.getVideoId() + " failed to insert for audit.");
                throw new RuntimeException("videoId of " + videoModel.getVideoId() + " failed to insert for audit.");
            } 
        } catch(SQLException se) {
            log.error("SQLException in insertVideoAudit method." + se);
            throw new RuntimeException("SQLException in insertVideo method." + se);
        } finally {
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
    }
    
    private static void insertExtras(List<VideoExtraModel> videoExtras, Long videoId) {
        for (VideoExtraModel videoExtra: videoExtras) {
            insertVideoExtra(videoExtra, videoId);
        }
    }
    
    private static void insertVideoExtra(VideoExtraModel videoExtra, Long videoId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Long videoExtraId = SQLUtil.getNextTablePrimaryKey("VIDEOEXTRA");
        videoExtra.setVideoExtraId(videoExtraId);
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(INSERT_VIDEOEXTRA);
            SQLSupport.setLong(videoExtra.getVideoExtraId(), 1, stmt);
            SQLSupport.setLong(videoId, 2, stmt);
            SQLSupport.setString(videoExtra.getTitle(), 3, stmt);
            SQLSupport.setString(videoExtra.getDescription(), 4, stmt);
            SQLSupport.setTimestamp(videoExtra.getLastModified(), 5, stmt);     
            
            int resultCount = stmt.executeUpdate();
            if (resultCount == 0) {
                log.error("videoId of " + videoId + " failed to insert extra.");
                throw new RuntimeException("videoId of " + videoId + " failed to insert extra.");
            } 
        } catch(SQLException se) {
            log.error("SQLException in insertVideoExtra method." + se);
            throw new RuntimeException("SQLException in insertVideoExtra method." + se);
        } finally {
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }       
    }
    
    private static void saveVideoMovies(VideoModel videoModel) {
        List<VideoMovieModel> videoMovies = videoModel.getVideoMovies();
        if (videoMovies != null && !videoMovies.isEmpty()) {
            for (VideoMovieModel videoMovieModel : videoMovies) {
                 if (videoMovieModel.getVideoMovieId() == null && videoMovieModel.getMovieId() == null) {
                     Long movieId = MovieDAO.insertMovie(videoMovieModel.getMovieModel());
                     log.warn("inserted movie with id of: " + movieId);
                     
                     //insert videoMovie connecting Video to Movie
                     //I like the videoMovieId to match up with the movieId in this case 
                     insertVideoMovie(movieId, videoModel.getVideoId(), movieId);
                     
                     List<String> commentaries = videoMovieModel.getCommentaries();
                     if (commentaries != null) {
                        for (String commentary : commentaries) {
                           insertCommentary(movieId, commentary);
                        }      
                     }
                 } else {
                     MovieDAO.updateMovie(videoMovieModel.getMovieModel());
                 }                 
            }
        }        
    }
    
    private static void insertCommentary(Long videoMovieId, String commentary) {
        log.warn("insert commentary for " + videoMovieId + " with " + commentary);
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(INSERT_COMMENTARY);
            
            Long videoCommentaryId = SQLUtil.getNextTablePrimaryKey("VIDEOCOMMENTARY");
            SQLSupport.setLong(videoCommentaryId, 1, stmt);
            SQLSupport.setLong(videoMovieId, 2, stmt);
            SQLSupport.setString(commentary, 3, stmt);
            SQLSupport.setTimestamp(new Date(), 4, stmt);
            
            int resultCount = stmt.executeUpdate();
            if (resultCount == 0) {
                log.error("videoMovieId of " + videoMovieId + " failed to insert commentary.");
                throw new RuntimeException("videoMovieId of " + videoMovieId + " failed to insert commentary.");
            } 
        } catch(SQLException se) {
            log.error("SQLException in insertCommentary method." + se);
            throw new RuntimeException("SQLException in insertCommentary method." + se);
        } finally {
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }       
    }
    
    private static void insertVideoMovie(Long videoMovieId, Long videoId, Long movieId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(INSERT_VIDEOMOVIE);
            
            SQLSupport.setLong(videoMovieId, 1, stmt); 
            SQLSupport.setLong(videoId, 2, stmt);
            SQLSupport.setLong(movieId, 3, stmt);
            SQLSupport.setTimestamp(new Date(), 4, stmt);
            int resultCount = stmt.executeUpdate();
            if (resultCount == 0) {
                log.error("videoId of " + videoId + " and movieId of " + movieId + " failed to insert.");
                throw new RuntimeException("videoId of " + videoId + " and movieId of " + movieId + " failed to insert.");
            }             
        } catch(SQLException se) {
            log.error("SQLException in insertVideoMovie method." + se);
            log.error("For videoId: " + videoId + " and movieId: " + movieId);
            throw new RuntimeException("SQLException in insertVideoMovie method." + se);
        } finally {
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }       
    }

    public static void updateVideoExtra(VideoExtraModel vem) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(UPDATE_VIDEOEXTRA);
            SQLSupport.setString(vem.getTitle(), 1, stmt);
            SQLSupport.setString(vem.getDescription(), 2, stmt);
            SQLSupport.setTimestamp(vem.getLastModified(), 3, stmt);     
            SQLSupport.setLong(vem.getVideoExtraId(), 4, stmt);
            
            int resultCount = stmt.executeUpdate();
            if (resultCount == 0) {
                throw new IllegalArgumentException("VideoExtraId of " + vem.getVideoExtraId() + " does not exist.");
            }             
        } catch(SQLException se) {
            log.error("SQLException in updateVideoExtra method." + se);
            throw new RuntimeException("SQLException in updateVideoExtra method." + se);
        } finally {
            SQLUtil.close(result);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
    }
    
    public static void updateVideo(VideoModel videoModel) {
        Long videoId = videoModel.getVideoId();
        videoModel.setLastModified(new Date());
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(UPDATE_VIDEO);
 
            SQLSupport.setString(videoModel.getTitle(), 1, stmt);
            SQLSupport.setString(videoModel.getVideoType(), 2, stmt);
            SQLSupport.setInteger(videoModel.getYearOfRelease(), 3, stmt);
            SQLSupport.setString(videoModel.getRating(), 4, stmt);
            SQLSupport.setString(videoModel.getRegion(), 5, stmt);
            SQLSupport.setString(videoModel.getLabel(), 6, stmt);
            SQLSupport.setString(videoModel.getComments(), 7, stmt);
            SQLSupport.setTimestamp(videoModel.getLastModified(), 8, stmt);
            SQLSupport.setFloat(videoModel.getCost(), 9, stmt);
            SQLSupport.setString(videoModel.getObtainedFrom(), 10, stmt);
            SQLSupport.setString(videoModel.getObtainedType(), 11, stmt);
            SQLSupport.setBoolean(videoModel.getSlipcoverYn(), 12, stmt);
            SQLSupport.setBoolean(videoModel.getOopYn(), 13, stmt);
            SQLSupport.setInteger(videoModel.getDiscCount(), 14, stmt);
            SQLSupport.setString(videoModel.getCaseType(), 15, stmt);
            SQLSupport.setString(videoModel.getSpineNumber(), 16, stmt);
            SQLSupport.setString(videoModel.getLocation(), 17, stmt);
            SQLSupport.setInteger(videoModel.getTotalMinutes(), 18, stmt);
            SQLSupport.setLong(videoId, 19, stmt);
            
            int resultCount = stmt.executeUpdate();
            if (resultCount == 0) {
                throw new IllegalArgumentException("videoId of " + videoId + " does not exist.");
            } 
            
            saveVideoMovies(videoModel);
            insertExtras(videoModel.getVideoExtras(), videoId);
        } catch(SQLException se) {
            log.error("SQLException in updateVideo method." + se);
            throw new RuntimeException("SQLException in updateVideo method." + se);
        } finally {
            SQLUtil.close(result);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
    }

    public static void deleteVideo(VideoModel videoModel) {
        if (videoModel == null) {
            throw new NullPointerException("videoModel parameter in deleteVideo method should not be null!");
        }
        final Long videoId = videoModel.getVideoId();
        log.warn("gets to delete videoId: " + videoId);
        log.warn(videoModel.toString());
        
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = SQLUtil.getDBConnection();
            ps = conn.prepareStatement(DELETE_VIDEO);
            SQLSupport.setLong(videoId, 1, ps);

            int resultCount = ps.executeUpdate();
            if (resultCount == 0) {
                throw new RuntimeException("failed to delete row: primary key = '" + videoId + "'");
            }

            insertVideoAudit(videoModel, "system", DELETE_USER_ACTION);
        } catch(SQLException sqle) {
            log.error("DAO Error" + sqle, sqle);
            throw new RuntimeException("SQLException while deleting row: primary key = '" + videoId + "'\n" + sqle);
        } finally {
            SQLUtil.close(ps);
            SQLUtil.close(conn);
        }
    }

    public static Long createCopy(VideoModel videoModel, Boolean copyExtras) {
        log.warn("gets to create a copy:");
        log.warn(videoModel.toString());
        final Long originalVideoId = videoModel.getVideoId();
        final Long newVideoId = insertVideo(videoModel);
        Long videoMovieId = SQLUtil.getNextTablePrimaryKey("VIDEOMOVIE");
        
        for (VideoMovieModel vmm : getVideoMovies(originalVideoId)) {
            log.warn("will need to create new videoMovie to this movieId: " + vmm.getMovieId() + " " + vmm.getTitle());
            //TODO 4/19/2023 seems to blow up because of multiple videos
            insertVideoMovie(videoMovieId, newVideoId, vmm.getMovieId());
            videoMovieId++;
        }
        
        
        insertVideoAudit(videoModel, "CreateCopy", INSERT_USER_ACTION);
      
        if (copyExtras != null && copyExtras) {
            insertExtras(getVideoExtras(originalVideoId), newVideoId);
        }
                
        return newVideoId;
    }
    
    public static List<VideoModel> getVideoList () {
        List<VideoModel> videoList = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(VIDEO_LIST);
            results = stmt.executeQuery();
            while (results.next()) {
                VideoModel videoModel = new VideoModel();
                videoModel.setVideoId(SQLSupport.getLong("VIDEOID", results));
                videoModel.setTitle(SQLSupport.getString("TITLE", results));
                videoModel.setVideoType(SQLSupport.getString("VIDEOTYPE", results));
                videoModel.setLabel(SQLSupport.getString("LABEL", results));
                videoList.add(videoModel);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getVideoList method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return videoList;
    }

    public static List<VideoMovieModel> getVideoMovies(Long videoId) {
        List<VideoMovieModel> movies = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(VIDEO_MOVIES);
            SQLSupport.setLong(videoId, 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
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
                movie.setTrailers(getMovieTrailers(movie.getMovieId()));
                movie.setReview(getMovieReview(movie.getMovieId()));
                movie.setWatchHistory(MovieDAO.getMovieWatchHistory(movie.getMovieId()));
                
                VideoMovieModel videoMovie = new VideoMovieModel(movie);
                videoMovie.setVideoMovieId(SQLSupport.getLong("VIDEOMOVIEID", results));
                videoMovie.setCommentaries(getCommentaries(videoMovie.getVideoMovieId()));
                videoMovie.setVideoMisc(getVideoMisc(videoMovie.getVideoMovieId()));
                
                movies.add(videoMovie);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getVideoMovies method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        return movies;
    }

    public static List getVideoExtras(Long videoId) {
        List<VideoExtraModel> videoExtras = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(VIDEOEXTRAS);
            SQLSupport.setLong(videoId, 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                VideoExtraModel vem = new VideoExtraModel();
                vem.setVideoExtraId(SQLSupport.getLong("VIDEOEXTRAID", results));
                vem.setVideoId(videoId);
                vem.setTitle(SQLSupport.getString("TITLE", results));
                vem.setDescription(SQLSupport.getString("DESCRIPTION", results));
                vem.setLastModified(SQLSupport.getDateFromTimestamp("LASTMODIFIED", results));
                videoExtras.add(vem);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getVideoExtras method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return videoExtras;
    }

    public static List<VideoBoxSetModel> getVideosByBoxSet(Long boxSetId) {
        List<VideoBoxSetModel> videoList = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(VIDEO_BOXSET);
            SQLSupport.setLong(boxSetId, 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                VideoBoxSetModel vbsm = new VideoBoxSetModel();
                vbsm.setVideoBoxSetId(SQLSupport.getLong("VIDEOBOXSETID", results));
                vbsm.setVideoId(SQLSupport.getLong("VIDEOID", results));
                vbsm.setBoxSetId(boxSetId);
                vbsm.setLastModified(SQLSupport.getDateFromTimestamp("LASTMODIFIED", results));
                vbsm.setVideoTitle(SQLSupport.getString("TITLE", results));
                videoList.add(vbsm);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getVideosByBoxSet method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return videoList;
    }    

    public static List<VideoMiscModel> getVideoMisc(Long videoMovieId) {
        List<VideoMiscModel> videoMisc = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(VIDEOMISC);
            SQLSupport.setLong(videoMovieId, 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                VideoMiscModel videoMiscModel = new VideoMiscModel();
                videoMiscModel.setVideoMisc(SQLSupport.getString("VIDEOMISC", results));
                videoMiscModel.setWarningYn(SQLSupport.getBoolean("WARNINGYN", results));
                videoMisc.add(videoMiscModel);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getVideoMisc method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        return videoMisc;
    }
    
    public static List<String> getCommentaries(Long videoMovieId) {
        List<String> commentaries = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(COMMENTARIES);
            SQLSupport.setLong(videoMovieId, 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                String commentators = SQLSupport.getString("COMMENTATORS", results);
                commentaries.add(commentators);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getCommentaries method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        return commentaries;
    }
    
    public static List<TrailerModel> getMovieTrailers(Long movieId) {
        List<TrailerModel> trailers = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(TRAILERS);
            SQLSupport.setLong(movieId, 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                TrailerModel trailerModel = new TrailerModel();
                trailerModel.setTrailerId(SQLSupport.getLong("TRAILERID", results));
                trailerModel.setMovieId(SQLSupport.getLong("MOVIEID", results));
                trailerModel.setEmbedCode(SQLSupport.getString("EMBEDCODE", results));
                trailerModel.setDescription(SQLSupport.getString("DESCRIPTION", results));
                trailers.add(trailerModel);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getMovieTrailers method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        return trailers;
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
                log.warn("review: " + cis.toString());
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
    
    public static String getCurrentlyLentTo(Long videoId) {
        String currentlyLentTo = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(CURRENT_LENDING);
            SQLSupport.setLong(videoId, 1, stmt);
            results = stmt.executeQuery();
            if (results.next()) {
                currentlyLentTo = SQLSupport.getString("LENTTO", results);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in currentlyLentTo method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
            
        return currentlyLentTo;
    }

    public static List<VideoModel> getVideosByCollectionType(String collectionType) {
        List<VideoModel> videoList = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(VIDEO_COLLECTION_TYPE);
            SQLSupport.setString(collectionType.toUpperCase(), 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                VideoModel videoModel = new VideoModel();
                videoModel.setVideoId(SQLSupport.getLong("VIDEOID", results));
                videoModel.setTitle(SQLSupport.getString("TITLE", results));
                videoModel.setLabel(SQLSupport.getString("LABEL", results));
                videoModel.setVideoType(SQLSupport.getString("VIDEOTYPE", results));
                videoModel.setSpineNumber(SQLSupport.getString("SPINENUMBER", results));
                videoList.add(videoModel);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getVideosByStudio method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }

        return videoList;
    }

    public static List<VideoModel> getVideosByLabel(String label) {
        List<VideoModel> videoList = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(VIDEO_LABEL);
            SQLSupport.setString(label.toUpperCase(), 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                VideoModel videoModel = new VideoModel();
                videoModel.setVideoId(SQLSupport.getLong("VIDEOID", results));
                videoModel.setTitle(SQLSupport.getString("TITLE", results));
                videoModel.setLabel(SQLSupport.getString("LABEL", results));
                videoModel.setVideoType(SQLSupport.getString("VIDEOTYPE", results));
                videoModel.setSpineNumber(SQLSupport.getString("SPINENUMBER", results));
                videoList.add(videoModel);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getVideosByStudio method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }

        return videoList;
    }
    
    public static List<VideoModel> getVideosByStudio(String studio) {
        List<VideoModel> videoList = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(VIDEO_STUDIOS);
            SQLSupport.setString(studio.toUpperCase(), 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                VideoModel videoModel = new VideoModel();
                videoModel.setVideoId(SQLSupport.getLong("VIDEOID", results));
                videoModel.setTitle(SQLSupport.getString("TITLE", results));
                videoModel.setLabel(SQLSupport.getString("LABEL", results));
                videoModel.setVideoType(SQLSupport.getString("VIDEOTYPE", results));
                videoList.add(videoModel);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getVideosByStudio method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }

        return videoList;
    }
    
    public static List<VideoModel> getVideosByGenre(String genre) {
        List<VideoModel> videoList = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(VIDEO_GENRE);
            SQLSupport.setString(genre.toUpperCase(), 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                VideoModel videoModel = new VideoModel();
                videoModel.setVideoId(SQLSupport.getLong("VIDEOID", results));
                videoModel.setTitle(SQLSupport.getString("TITLE", results));
                videoModel.setLabel(SQLSupport.getString("LABEL", results));
                videoModel.setVideoType(SQLSupport.getString("VIDEOTYPE", results));
                videoList.add(videoModel);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getVideosByStudio method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        return videoList;
    }

    private static List<String> getVideoCollections(Long videoId) {
        List<String> collections = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(COLLECTION);
            SQLSupport.setLong(videoId, 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                collections.add(SQLSupport.getString("COLLECTIONNAME", results));
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getVideoCollections method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return collections;
    }
        
}
