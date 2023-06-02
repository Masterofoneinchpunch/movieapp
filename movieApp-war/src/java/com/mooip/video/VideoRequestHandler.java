package com.mooip.video;

import com.mooip.movie.MovieModel;
import com.mooip.util.control.Handler;
import com.mooip.util.DateUtil;
import com.mooip.util.LoggerAdapter;
import com.mooip.util.NumberUtil;
import com.mooip.util.RequestUtil;
import com.mooip.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class VideoRequestHandler implements Handler {
 
    private static final LoggerAdapter log = LoggerAdapter.getLogger(VideoRequestHandler.class);
    private static final String DELETE_MESSAGE = "Deleted";
    private static final String INSERT_MESSAGE = "Inserted";
    private static final String SAVED_MESSAGE = "Saved";

    @Override
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request);
        return FORWARD;
    }
    
    @Override
    public void processRequest(HttpServletRequest request) {
        Long videoId = RequestUtil.getPrimaryKey(request, "videoId");
        String actionType = request.getParameter("actionType");
        log.warn("at: " + actionType + " " + request.getParameter("dude"));
               
        if (actionType != null && actionType.equals("Save")) {
            if (videoId != null) {
                //TODO: RequestUtil.traverseParameterNames(request); //remove after testing; prints out request parameters
                VideoDAO.updateVideo(transferModel(request, videoId));
                request.setAttribute("message", SAVED_MESSAGE);
            } else {
                videoId = VideoDAO.insertVideo(transferModel(request, videoId));
                request.setAttribute("message", INSERT_MESSAGE);
            }
        } else if (actionType != null && actionType.equals("Delete")) {
            VideoDAO.deleteVideo(transferModel(request, videoId));
            request.setAttribute("message", DELETE_MESSAGE);
        } else if (actionType != null && actionType.equals("CreateCopy")) {
            Boolean copyExtras = NumberUtil.parseBoolean(request.getParameter("copyExtras"));
            log.warn("copyExtras: " + copyExtras);
            videoId = VideoDAO.createCopy(transferModel(request, videoId), copyExtras);
            request.setAttribute("message", "New Copy created");
        }
        
        if (videoId != null) {
            request.setAttribute("video", VideoDAO.getVideo(videoId));
        }
        
        //TODO
        log.warn("video list is being called; not sure this should be called for all cases.");
        List videoList = VideoDAO.getVideoList();
        request.setAttribute("videoList", videoList);
        request.setAttribute("videoListCount", videoList.size());
    }
       
    private VideoModel transferModel(HttpServletRequest request, Long videoId) {
        VideoModel videoModel = new VideoModel();
        videoModel.setCaseType(request.getParameter("caseType"));
        videoModel.setComments(request.getParameter("comments"));
        videoModel.setCost(NumberUtil.parseCurrency(request.getParameter("cost")));
        videoModel.setDiscCount(NumberUtil.parseInteger(request.getParameter("discCount")));
        videoModel.setLabel(request.getParameter("label"));
        videoModel.setLocation(request.getParameter("location"));
        videoModel.setObtainedFrom(request.getParameter("obtainedFrom"));
        videoModel.setObtainedType(request.getParameter("obtainedType"));
        videoModel.setOopYn(NumberUtil.parseBoolean(request.getParameter("oopYn")));
        videoModel.setRating(request.getParameter("rating"));
        videoModel.setRegion(request.getParameter("region"));
        videoModel.setSlipcoverYn(NumberUtil.parseBoolean(request.getParameter("slipcoverYn")));
        videoModel.setSpineNumber(request.getParameter("spineNumber"));
        videoModel.setTitle(request.getParameter("title"));
        videoModel.setTotalMinutes(NumberUtil.parseInteger(request.getParameter("totalMinutes")));
        videoModel.setVideoId(videoId);
        videoModel.setVideoType(request.getParameter("videoType"));
        videoModel.setYearOfRelease(NumberUtil.parseInteger(request.getParameter("yearOfRelease")));
        
        videoModel.setVideoMovies(transferMovies(request));
        videoModel.setVideoExtras(transferExtras(request, videoId));
        
        return videoModel;
    }

    private List<VideoMovieModel> transferMovies(HttpServletRequest request) {
        List<VideoMovieModel> movies = new ArrayList();
        
        Integer newCount = new Integer(request.getParameter("newCount"));
        for (int i = 1; i <= newCount; i++) {
            String movieTitle = request.getParameter("movieTitle_new_" + i);
            
            if (StringUtil.isEmpty(movieTitle) == false) {               
                
                VideoMovieModel vmModel = new VideoMovieModel(setMovieTransfer(request, "_new_" + i, movieTitle));
                
                String commentary = request.getParameter("commentary_new_" + i);
                if (StringUtil.isEmpty(commentary) == false) {
                    List<String> commentaries = new ArrayList();
                    commentaries.add(commentary);
                    vmModel.setCommentaries(commentaries);
                }
                        
                movies.add(vmModel);
            }
        }
        
        String[] movieIds = request.getParameterValues("movieId");
        if (movieIds != null) {
            for (String movieId : movieIds) {                
                String movieTitle = request.getParameter("movieTitle_" + movieId);
                if (StringUtil.isEmpty(movieTitle) == false) {
                    log.warn("update movie: " + movieTitle);
                    MovieModel movieModel = setMovieTransfer(request, "_" + movieId, movieTitle);
                    movieModel.setMovieId(new Long(movieId));
                    log.warn(movieModel.toString());
                    VideoMovieModel vmModel = new VideoMovieModel(movieModel);
                    movies.add(vmModel);
                }
            }            
        }
            
        return movies;
    }

    private MovieModel setMovieTransfer(HttpServletRequest request, final String SUFFIX, final String movieTitle) {
        Integer runtime = NumberUtil.parseInteger(request.getParameter("runtime" + SUFFIX)); 
        Integer year = NumberUtil.parseInteger(request.getParameter("movieYear" + SUFFIX));
        String country = request.getParameter("country" + SUFFIX);
        String language = request.getParameter("language" + SUFFIX);
        String mpaaRating = request.getParameter("mpaaRating" + SUFFIX);
        String myRating = request.getParameter("myRating" + SUFFIX);
        Date lastWatched = DateUtil.parseDate(request.getParameter("lastWatched" + SUFFIX));

        MovieModel movieModel = new MovieModel();
        movieModel.setTitle(movieTitle);
        movieModel.setRuntime(runtime);
        movieModel.setYear(year);
        movieModel.setCountry(country);
        movieModel.setLanguage(language);
        movieModel.setMpaaRating(mpaaRating);
        movieModel.setMyRating(myRating);
        movieModel.setLastWatched(lastWatched);   
        
        return movieModel;
    }
    
    private List<VideoExtraModel> transferExtras(HttpServletRequest request, Long videoId) {
        List<VideoExtraModel> extras = new ArrayList();

        Integer newCount = new Integer(request.getParameter("newExtraCount"));
        for (int i = 1; i <= newCount; i++) {
            String extraTitle = request.getParameter("videoExtraTitle_new_" + i);
            if (StringUtil.isEmpty(extraTitle) == false) {               

                VideoExtraModel videoExtraModel = new VideoExtraModel();
                videoExtraModel.setTitle(extraTitle);
                videoExtraModel.setDescription(request.getParameter("videoExtraDescription_new_" + i));
                videoExtraModel.setVideoId(videoId);
                videoExtraModel.setLastModified(new Date());
                extras.add(videoExtraModel);
            }
        }
        
        return extras;
    }
}
