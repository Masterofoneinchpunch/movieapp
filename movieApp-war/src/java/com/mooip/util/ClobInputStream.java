package com.mooip.util;

import java.sql.Clob;
import java.sql.SQLException;
import java.io.InputStream;
import java.io.IOException;

/**
 * This class wraps a {@link java.sql.Clob} and an InputStream. It allows for safe streaming 
 * of a {@link java.sql.Clob}.
 *
 * @author masterofoneinchpunch
 */
public final class ClobInputStream {  
    private static final LoggerAdapter log = LoggerAdapter.getLogger(RequestUtil.class);
    
    final private Clob clob;
    final private InputStream in;

    /** 
     * Creates a new ClobInputStream.
     * 
     * @param clob The {@link java.sql.Clob} to create the stream for.
     * @throws IOException if unable to create stream from clob.
     * @throws java.sql.SQLException
     */
    public ClobInputStream(Clob clob) throws IOException, SQLException {
	this.clob = clob;
        this.in = clob.getAsciiStream();
    }
    
    /**
     * Reads the next byte in the stream.
     * 
     * @return The next byte.
     * @throws IOException if unable to retrieve next byte.
     */
    public int read() throws IOException {
        return in.read();
    }
    
    /**
     * Closes stream and connection to the database.
     * 
     * @throws IOException if {@link SQLException} occurs while closing 
     * stream or connection.
     */
    public void close() throws IOException {
        if (in != null) {
            in.close();
        }
    }
    
    /**
     * Retrieves clob length.
     * 
     * @return The length of the clob.
     * @throws IOException if clob is null or SQL exception occurs.
     */
    public long getLength() throws IOException {
        long length = 0L;
        try {
            if (clob != null) {
                length = clob.length();
            } else {
                throw new IOException("Unable to get length of clob because clob is null.");
            }
	} catch(SQLException se) {
            log.error(se);
	    throw new IOException(se.getMessage());
	}
        return length;
    }
 
    /**
     * Gets the InputStream.
     *
     * @return InputStream The InputStream of this object.
     * @throws IllegalStateException if the InputStream is null; this is possibly due to the InputStream being closed.
     */
    public InputStream getInputStream() {
        if (this.in == null) {
            throw new IllegalStateException("InputStream is null.  Possibly caused by trying to get the InputStream after it has been closed.");
        }
        return this.in;
    }
    
    /**
     * This gets the clob in String form; so far up until 2 to 31 power (Max Integer).
     *
     * @return String The clob in String form.
     * @throws RuntimeException if there is a SQLException.
     */
    @Override
    public String toString() {
        try {
            return this.clob.getSubString(1L, (int) clob.length());
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("problem reading clob in toString method.");
        }
    }
}
