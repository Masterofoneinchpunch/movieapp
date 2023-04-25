package com.mooip.collectionType;

import com.mooip.util.control.Handler;
import com.mooip.util.LoggerAdapter;
import com.mooip.util.StringUtil;
import com.mooip.video.VideoDAO;
import com.mooip.video.VideoModel;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CollectionTypeRequestHandler implements Handler {
    
    public static final LoggerAdapter log = LoggerAdapter.getLogger(CollectionTypeRequestHandler.class);

    @Override
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request);
        return FORWARD;
    }

    @Override
    public void processRequest(HttpServletRequest request) {
        final List<VideoModel> videos;
        if (StringUtil.isEmpty(request.getParameter("labelSearch"))) {
            videos = VideoDAO.getVideosByCollectionType(request.getParameter("collectionType"));
        } else {
            videos = VideoDAO.getVideosByLabel(request.getParameter("collectionType"));
        }
        
        request.setAttribute("videoList", videos);
        request.setAttribute("videoCount", videos.size());        
    }
}
