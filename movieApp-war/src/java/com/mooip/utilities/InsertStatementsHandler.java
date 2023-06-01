package com.mooip.utilities;

import com.mooip.util.control.Handler;
import com.mooip.util.LoggerAdapter;
import com.mooip.util.SQLUtil;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class InsertStatementsHandler implements Handler {
 
    private static final LoggerAdapter log = LoggerAdapter.getLogger(InsertStatementsHandler.class);
    private static final String NEWLINE = System.getProperty("line.separator");
    
    public void processRequest(HttpServletRequest request) {
        throw new UnsupportedOperationException("InsertStatementsHandler needs to add a response to the processRequest.");
    }
    
    public boolean processRequest(HttpServletRequest request, HttpServletResponse response) {
        boolean forward = true;
        final String listType = request.getParameter("listType");
        log.warn("Insert Statement Request " + listType);
        if (listType == null || listType.equals("ALL")) {
            processResponse(response);
            forward = false;
        } else {
            request.setAttribute("listType", SQLUtil.getInsertStatements(listType, listType + "ID"));
        }
        return forward;
    }
    
    private void processResponse(HttpServletResponse response) {
        response.reset();
        response.setContentType("application/x-mswrite");

        try {               
            ServletOutputStream responseOutputStream = response.getOutputStream();
            for (String video : SQLUtil.getInsertStatements("SERIES", "SERIESID")) {
                responseOutputStream.print(video + NEWLINE);  
            }
            for (String video : SQLUtil.getInsertStatements("STUDIO", "STUDIOID")) {
                responseOutputStream.print(video + NEWLINE);  
            }
            for (String video : SQLUtil.getInsertStatements("GENRE", "GENREID")) {
                responseOutputStream.print(video + NEWLINE);  
            }
            for (String video : SQLUtil.getInsertStatements("MOVIEGENRE", "MOVIEGENREID")) {
                responseOutputStream.print(video + NEWLINE);  
            }
            for (String video : SQLUtil.getInsertStatements("MOVIESTUDIO", "MOVIESTUDIOID")) {
                responseOutputStream.print(video + NEWLINE);  
            }
            for (String person : SQLUtil.getInsertStatements("BOOK", "BOOKID")) {
                responseOutputStream.print(person + NEWLINE);  
            }
            for (String person : SQLUtil.getInsertStatements("COLLECTION", "COLLECTIONID")) {
                responseOutputStream.print(person + NEWLINE);  
            }
            for (String person : SQLUtil.getInsertStatements("PERSON", "PERSONID")) {
                responseOutputStream.print(person + NEWLINE);  
            }
            for (String movie : SQLUtil.getInsertStatements("MOVIE", "MOVIEID")) {
                responseOutputStream.print(movie + NEWLINE);  
            }
            for (String movie : SQLUtil.getInsertStatements("MOVIEWATCHHISTORY", "MOVIEWATCHHISTORYID")) {
                responseOutputStream.print(movie + NEWLINE);  
            }
            for (String video : SQLUtil.getInsertStatements("VIDEO", "VIDEOID")) {
                responseOutputStream.print(video + NEWLINE);  
            }
            for (String lendingHistory : SQLUtil.getInsertStatements("LENDINGHISTORY", "LENDINGHISTORYID")) {
                responseOutputStream.print(lendingHistory + NEWLINE);  
            }
            for (String trailer : SQLUtil.getInsertStatements("TRAILER", "TRAILERID")) {
                responseOutputStream.print(trailer + NEWLINE);  
            }
            for (String videoExtra : SQLUtil.getInsertStatements("VIDEOBOXSET", "VIDEOBOXSETID")) {
                responseOutputStream.print(videoExtra + NEWLINE);  
            }
            for (String videoExtra : SQLUtil.getInsertStatements("VIDEOCOLLECTION", "VIDEOCOLLECTIONID")) {
                responseOutputStream.print(videoExtra + NEWLINE);  
            }
            for (String videoExtra : SQLUtil.getInsertStatements("VIDEOEXTRA", "VIDEOEXTRAID")) {
                responseOutputStream.print(videoExtra + NEWLINE);  
            }
            for (String videoMovie : SQLUtil.getInsertStatements("VIDEOMOVIE", "VIDEOMOVIEID")) {
                responseOutputStream.print(videoMovie + NEWLINE);  
            }
            for (String videoMovie : SQLUtil.getInsertStatements("VIDEOSERIES", "VIDEOSERIESID")) {
                responseOutputStream.print(videoMovie + NEWLINE);  
            }
            for (String videoCommentary : SQLUtil.getInsertStatements("VIDEOCOMMENTARY", "VIDEOCOMMENTARYID")) {
                responseOutputStream.print(videoCommentary + NEWLINE);  
            }
            for (String videoMisc : SQLUtil.getInsertStatements("VIDEOMISC", "VIDEOMISCID")) {
                responseOutputStream.print(videoMisc + NEWLINE);  
            }
            responseOutputStream.flush();
            responseOutputStream.close();
        } catch (IOException ioe) {
            log.error("IO Exception: " + ioe);
        }        
    } 
}
