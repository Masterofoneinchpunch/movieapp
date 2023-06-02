package com.mooip.video;

import com.mooip.util.LoggerAdapter;
import com.mooip.util.RequestUtil;
import com.mooip.util.control.Handler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class VideoExtraRequestHandler implements Handler {

    private static final LoggerAdapter log = LoggerAdapter.getLogger(VideoExtraRequestHandler.class);
    
    @Override
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request);
        return FORWARD;
    }

    @Override
    public void processRequest(HttpServletRequest request) {
        Long videoExtraId = RequestUtil.getPrimaryKey(request, "videoExtraId");       
        String actionType = request.getParameter("actionType");        
        
        if (actionType != null && actionType.equals("Save")) {  
            log.warn("saves video extra");
            VideoDAO.updateVideoExtra(transferModel(request, videoExtraId));
        }
    }

    private VideoExtraModel transferModel(HttpServletRequest request, Long videoExtraId) {
        VideoExtraModel vem = new VideoExtraModel();
        vem.setVideoExtraId(videoExtraId);
        vem.setDescription(request.getParameter("extraDesc"));
        vem.setTitle(request.getParameter("extraTitle"));
        
        return vem;
    }
    
}
