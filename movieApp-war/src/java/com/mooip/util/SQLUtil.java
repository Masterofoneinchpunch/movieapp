package com.mooip.util;

import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class SQLUtil { 
    private static final LoggerAdapter log = LoggerAdapter.getLogger(SQLUtil.class);
    private static final String DATASOURCE = "java:/DataSourceCASE";
    private static DataSource datasource = null;
  
    public static DataSource getDataSource() {
        if (datasource == null) {
            initializeDataSource();
        }
        return datasource;
    }

    public static Connection getDBConnection() throws SQLException {
        if (datasource == null) {
            initializeDataSource();
        }
        return datasource.getConnection();
    }   
    
    private static synchronized void initializeDataSource() {
        try {
            InitialContext ic = new InitialContext();
            datasource = (DataSource) ic.lookup(DATASOURCE);
        } catch (NamingException ne) {
            log.error(ne);
            throw new IllegalStateException("NamingException while looking up DB context  : " + ne.getMessage());
        }
    }
        
    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot close SQL ResultSet: " + e.toString());
        }
    }
    
    public static void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot close SQL Connection: " + e.toString());
        }
    }
    
    public static void close(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot close SQL Statement: " + e.toString());
        }
    }

    /**
     * Get next available primary key value for table.
     *
     * @param tableName The name of the table.
     * @return id The primary key value.
     * @throws RuntimeException if a SQLException is thrown.
     */
    public static Long getNextTablePrimaryKey(String tableName) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        long id = 0;
        try {
            con = SQLUtil.getDBConnection();
            stmt = con.createStatement();
            String sql = "SELECT MAX(" + tableName + "ID) AS MAX FROM " + tableName;
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                id = rs.getLong(1);
                id++;
            }
        } catch (SQLException sql) {
            log.error("Unable to retrieve next primary key value for table" + tableName + ". ", sql);
            throw new RuntimeException("Unable to retrieve next primary key value for table" + tableName + ". " + sql);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(stmt);
            SQLUtil.close(con);
        }
        return new Long(id);
    }
    
    /**
     * Get next available primary key value for table Oracle only.
     *
     * @param sequenceName The name of the sequence.
     * @return id The primary key value.
     * @throws RuntimeException if a SQLException is thrown.
     */
    public static Long getNextPrimaryKey(String sequenceName) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        long id = 0;
        try {
            con = SQLUtil.getDBConnection();
            stmt = con.createStatement();
            String sql = "SELECT " + sequenceName + ".NEXTVAL FROM DUAL";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException sql) {
            log.error("Unable to retrieve next primary key value for sequence" + sequenceName + ". ", sql);
            throw new RuntimeException("Unable to retrieve next primary key value for sequence" + sequenceName + ". " + sql);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(stmt);
            SQLUtil.close(con);
        }
        return new Long(id);
    }
    
    /**
     * Gets the column names.
     *
     * @param metaData The ResultSetMetaData full of metadata.
     * @return columnNames A String array with the column names.
     * @throws SQLException if there is a problem retrieving the metadata.
     */
    public static String[] getColumnNames(ResultSetMetaData metaData) throws SQLException {
        final int COLUMNCOUNT = metaData.getColumnCount();
        String[] columnNames = new String[COLUMNCOUNT];
        for (int i = 1; i <= COLUMNCOUNT; ++i) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        return columnNames;
    }
    
    public static List<String> getInsertStatements(String tableName, String orderBy) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<String> insertStatements = new ArrayList();
        
        try {
            con = SQLUtil.getDBConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM " + tableName + " ORDER BY " + orderBy;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String insertStatement = processInsertStatement(rs, tableName);
                insertStatements.add(insertStatement);
            }
        } catch (SQLException sql) {
            log.error("Unable to retrieve values for table name " + tableName + ". ", sql);
            throw new RuntimeException("Unable to retrieve values for table name " + tableName + ". ", sql);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(stmt);
            SQLUtil.close(con);
        }
        
        return insertStatements;
    }
    
    private static String processInsertStatement(ResultSet rs, String tableName) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        StringBuffer insertStatement = new StringBuffer("INSERT INTO ").append(tableName).append(" (");
        String[] columnNames = getColumnNames(metaData);
        for (int i = 0; i < columnNames.length; ++i) {
            insertStatement.append(columnNames[i]);
            if (i < columnNames.length - 1) {
                insertStatement.append(", ");
            }           
        }
        insertStatement.append(") VALUES (");
        
        for (int i = 1; i <= columnNames.length; i++) {
            int columnType = metaData.getColumnType(i);
            if (columnType == Types.VARCHAR) {
                String result = SQLSupport.getString(i, rs);
                result  = (result != null) ? result.replaceAll("'", "''") : result;
                insertStatement.append("'").append(result).append("'");
            } else if (columnType == Types.DECIMAL) {
                insertStatement.append("DECIMAL NOT SUPPORTED");
            } else if (columnType == Types.NUMERIC) {
                Long result = SQLSupport.getLong(i, rs);
                insertStatement.append(result);
            } else if (columnType == Types.DATE) {
                Date date = SQLSupport.getDateFromTimestamp(i, rs);
                String dateText = DateUtil.getDateText(DateUtil.DATE_TIME_DATEFORMAT, date);
                insertStatement.append("TO_DATE('").append(dateText).append("', 'MM/DD/YYYY HH:MI AM')");
            } else if (columnType == Types.CLOB) {
                Clob clob = rs.getClob(i);
                try {
                    ClobInputStream cis = new ClobInputStream(clob);
                    insertStatement.append("'");
                    String clobString = cis.toString();
                    insertStatement.append(clobString.replaceAll("'", "''"));
                    insertStatement.append("'");
                } catch (IOException io) {
                    log.error(io);
                    throw new RuntimeException("IOException getting clob in processInsertStatement method." + io); 
                }                
            } else {
                insertStatement.append("OTHER NOT SUPPORTED");
            }
            if (i <= columnNames.length - 1) {
                insertStatement.append(", ");
            }           
        }
        insertStatement.append(");");
        return insertStatement.toString();
    }  
}
