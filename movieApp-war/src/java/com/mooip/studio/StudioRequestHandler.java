package com.mooip.studio;

import com.mooip.util.control.Handler;
import com.mooip.video.VideoDAO;
import com.mooip.video.VideoModel;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class StudioRequestHandler implements Handler {

    @Override
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request);
        return FORWARD;
    }

    @Override
    public void processRequest(HttpServletRequest request) {
        final List<VideoModel> videos = VideoDAO.getVideosByStudio(request.getParameter("studio"));
        request.setAttribute("videoList", videos);
        request.setAttribute("videoCount", videos.size());
    }
}
