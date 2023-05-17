package com.mooip.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date; // ambiguous with: java.sql.Date 
import org.apache.log4j.Logger;

/**
 * This simplifies SQL support especially for null usage.
 */
public final class SQLSupport {
    private static final Logger log = Logger.getLogger(SQLSupport.class);

    private SQLSupport() {
    }
    
    public static Boolean getBoolean(String name, ResultSet rs) throws SQLException {
        try {
            int i = rs.getInt(name);
            if (rs.wasNull()) {
                return null;
            } else {
                return new Boolean(i != 0);
            }
        } catch (SQLException sqe) {
            log.error("SQLException recovering boolean field name: " + name);
            throw sqe;
        }
    }
    
    public static Boolean getBoolean(int index, ResultSet rs) throws SQLException {
        int i = rs.getInt(index);
        if (rs.wasNull()) {
            return null;
        } else {
            return new Boolean(i != 0);
        }
    }
    
    public static boolean getBooleanPrimitive(String name, ResultSet rs) throws SQLException {
        try {
            int i = rs.getInt(name);
            if (rs.wasNull()) {
                return false;
            } else {
                return i != 0;
            }
        } catch (SQLException sqe) {
            log.error("SQLException recovering field name: " + name);
            throw sqe;
        }
    }
    
    public static boolean getBooleanPrimitive(int index, ResultSet rs) throws SQLException {
        int i = rs.getInt(index);
        if (rs.wasNull()) {
            return false;
        } else {
            return i != 0;
        }
    }

    public static Date getDateFromSqlDate(String name, ResultSet rs) throws SQLException {
        try {
            java.sql.Date d = rs.getDate(name);
            if (rs.wasNull()) {
                return null;
            } else {
                return new Date(d.getTime());
            }
        } catch (SQLException sqe) {
            log.error("SQLException recovering field name: " + name);
            throw sqe;
        }
    }
    
    public static Date getDateFromSqlDate(int index, ResultSet rs) throws SQLException {
        try {
            java.sql.Date d = rs.getDate(index);
            if (rs.wasNull()) {
                return null;
            } else {
                return new Date(d.getTime());
            }
        } catch (SQLException sqe) {
            log.error("SQLException recovering field index: " + index);
            throw sqe;
        }
    }
    
    public static Date getDateFromTimestamp(String name, ResultSet rs) throws SQLException {
        try {
            java.sql.Timestamp d = rs.getTimestamp(name);
            if (rs.wasNull()) {
                return null;
            } else {
                return new Date(d.getTime());
            }
        } catch (SQLException sqe) {
            log.error("SQLException recovering field name: " + name);
            throw sqe;
        }
    }
    
    public static Date getDateFromTimestamp(int index, ResultSet rs) throws SQLException {
        java.sql.Timestamp d = rs.getTimestamp(index);
        if (rs.wasNull()) {
            return null;
        } else {
            return new Date(d.getTime());
        }
    }
    
    public static Float getFloat(String name, ResultSet rs) throws SQLException {
        float i = rs.getFloat(name);
        if (rs.wasNull()) {
            return null;
        } else {
            return new Float(i);
        }
    }
    
    public static Float getFloat(int index, ResultSet rs) throws SQLException {
        float i = rs.getFloat(index);
        if (rs.wasNull()) {
            return null;
        } else {
            return new Float(i);
        }
    }
    
    public static Double getDouble(String name, ResultSet rs) throws SQLException {
        double i = rs.getDouble(name);
        if (rs.wasNull()) {
            return null;
        } else {
            return new Double(i);
        }
    }
    
    public static Double getDouble(int index, ResultSet rs) throws SQLException {
        double i = rs.getDouble(index);
        if (rs.wasNull()) {
            return null;
        } else {
            return new Double(i);
        }
    }
    
    public static Integer getInteger(String name, ResultSet rs) throws SQLException {
        try {
            int i = rs.getInt(name);
            if (rs.wasNull()) {
                return null;
            } else {
                return new Integer(i);
            }
        } catch (SQLException sqe) {
            log.error("SQLException recovering field name: " + name);
            throw sqe;
        }
    }
    
    public static Integer getInteger(int index, ResultSet rs) throws SQLException {
        int i = rs.getInt(index);
        if (rs.wasNull()) {
            return null;
        } else {
            return new Integer(i);
        }
    }
    
        
    public static Long getLong(String name, ResultSet rs) throws SQLException {
        try {
            long i = rs.getLong(name);
            if (rs.wasNull()) {
                return null;
            } else {
                return new Long(i);
            }
        } catch (SQLException sqe) {
            log.error("SQLException recovering field name: " + name, sqe);
            throw sqe;
        }
    }
    
        
    public static Long getLong(int index, ResultSet rs) throws SQLException {
        try {
            long i = rs.getLong(index);
            if (rs.wasNull()) {
                return null;
            } else {
                return new Long(i);
            }
        } catch (SQLException sqe) {
            log.error("SQLException recovering field at: " + index, sqe);
            throw sqe;
        }
    }
    
        
    public static void setLong(Long i, int index, PreparedStatement ps) throws SQLException {
        if (i == null) {
            ps.setNull(index, Types.BIGINT);
        } else {
            ps.setLong(index, i.longValue());
        }
    }
    
    public static String getString(int index, ResultSet rs) throws SQLException {
        String s = rs.getString(index);
        if (rs.wasNull()) {
            return null;
        } else {
            return s;
        }
    }
    
    public static String getString(String name, ResultSet rs) throws SQLException {
        try {
            String s = rs.getString(name);
            if (rs.wasNull()) {
                return null;
            } else {
                return s;
            }
        } catch (SQLException sqe) {
            log.error("SQLException recovering field name: " + name);
            throw sqe;
        }
    }
    
    public static void setBoolean(boolean b, int index, PreparedStatement ps) throws SQLException {
        if (b) {
            ps.setInt(index, 1);
        } else {
            ps.setInt(index, 0);
        }
    }
    
    public static void setBoolean(Boolean b, int index, PreparedStatement ps) throws SQLException {
        if (b == null) {
            ps.setNull(index, Types.INTEGER);
        } else if (b.booleanValue()) {
            ps.setInt(index, 1);
        } else {
            ps.setInt(index, 0);
        }
    }
    
    public static void setDate(Date d, int index, PreparedStatement ps) throws SQLException {
        if (d == null) {
            ps.setNull(index, Types.DATE);
        } else {
            ps.setDate(index, new java.sql.Date(d.getTime()));
        }
    }
    
    public static void setFloat(Float f, int index, PreparedStatement ps) throws SQLException {
        if (f == null) {
            ps.setNull(index, Types.FLOAT);
        } else {
            ps.setFloat(index, f.floatValue());
        }
    }
    
    public static void setDouble(Double d, int index, PreparedStatement ps) throws SQLException {
        if (d == null) {
            ps.setNull(index, Types.DOUBLE);
        } else {
            ps.setDouble(index, d.doubleValue());
        }
    }
    
    public static void setInteger(Integer i, int index, PreparedStatement ps) throws SQLException {
        if (i == null) {
            ps.setNull(index, Types.INTEGER);
        } else {
            ps.setInt(index, i.intValue());
        }
    }
    
    public static void setString(String s, int index, PreparedStatement ps) throws SQLException {
        if (s == null) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, s);
        }
    }
    
    public static void setTimestamp(Date d, int index, PreparedStatement ps) throws SQLException {
        if (d == null) {
            ps.setNull(index, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(index, new java.sql.Timestamp(d.getTime()));
        }
    }

    public static void setTimestamp(Timestamp d, int index, PreparedStatement ps) throws SQLException {
        if (d == null) {
            ps.setNull(index, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(index, d);
        }
    }
}
