package com.mooip.video.lending;

import com.mooip.util.LoggerAdapter;
import com.mooip.util.SQLSupport;
import com.mooip.util.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class LendingDAO {
 
    private static final LoggerAdapter log = LoggerAdapter.getLogger(LendingDAO.class);
    
    private static final String CURRENT_LENDING = 
        "select VIDEO.VIDEOID, TITLE, LENTTO, LENDINGDATE " +
        "FROM VIDEO, LENDINGHISTORY " +
        "WHERE VIDEO.VIDEOID = LENDINGHISTORY.VIDEOID " +
        "AND RETURNEDYN = 0 " +
        "ORDER BY LENDINGDATE, TITLE ";
    
    private static final String LENDING_HISTORY =
        "select VIDEO.VIDEOID, TITLE, LENDINGDATE, RETURNEDYN, RETURNDATE " + 
        "FROM VIDEO, LENDINGHISTORY " + 
        "WHERE VIDEO.VIDEOID = LENDINGHISTORY.VIDEOID " + 
        "AND LENTTO = ? " + 
        "ORDER BY RETURNEDYN, NVL(RETURNDATE, TO_DATE('05/30/1973','MM/DD/YYYY')) DESC, LENDINGDATE DESC, TITLE ";
        
    private LendingDAO () {        
    }

    public static List<LendingModel> getLendingHistory(String lentTo) {
        List<LendingModel> lendingHistory = new ArrayList();        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(LENDING_HISTORY);
            SQLSupport.setString(lentTo, 1, stmt);
            results = stmt.executeQuery();
            while (results.next()) {
                LendingModel lendingModel = new LendingModel();
                lendingModel.setTitle(SQLSupport.getString("TITLE", results));
                lendingModel.setLentTo(lentTo);        
                lendingModel.setLendingDate(SQLSupport.getDateFromSqlDate("LENDINGDATE", results));
                lendingModel.setReturnDate(SQLSupport.getDateFromSqlDate("RETURNDATE", results));
                lendingModel.setReturnedYn(SQLSupport.getBoolean("RETURNEDYN", results));
                lendingModel.setVideoId(SQLSupport.getLong("VIDEOID", results));
                
                lendingHistory.add(lendingModel);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getLendingHistory method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return lendingHistory;
    }
    
    public static List<LendingModel> getCurrentLending() {
        List<LendingModel> currentLending = new ArrayList();        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(CURRENT_LENDING);
            results = stmt.executeQuery();
            while (results.next()) {
                LendingModel lendingModel = new LendingModel();
                lendingModel.setTitle(SQLSupport.getString("TITLE", results));
                lendingModel.setLentTo(SQLSupport.getString("LENTTO", results));        
                lendingModel.setLendingDate(SQLSupport.getDateFromSqlDate("LENDINGDATE", results));
                lendingModel.setVideoId(SQLSupport.getLong("VIDEOID", results));
                
                currentLending.add(lendingModel);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getCurrentLending method." + se); 
        } finally {
            SQLUtil.close(results);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }

        return currentLending;
    }
}    