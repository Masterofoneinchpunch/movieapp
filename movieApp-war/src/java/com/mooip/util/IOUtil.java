package com.mooip.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Scanner;

/**
 * Utility class for IO related actions.
 * 
 * @author masterofoneinchpunch
 */
public final class IOUtil {
    private static final int BUFFER_SIZE = 32768;
    
    private IOUtil() { }

    /**
     * Reads a file in and returns it as a string
     * This method is very memory intensive
     * !!! Only use for very small files!!!
     *
     * @param in The InputStream of the file to read in.
     * @throws IOException
     * @return the contents of the file as a String.
     */
    public static String readFile(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeStreamOut(in, baos);
        return baos.toString();
    }

    /**
     * Writes an InputStream to an OutputStream.
     *
     * @param in The InputStream to read in.
     * @param out The OutputStream to write to.
     * @throws IOException
     */
    public static void writeStreamOut(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int size;
        while ((size = in.read(buffer)) != -1) {
            out.write(buffer, 0, size);
        }
    }

    /**
     * Writes an InputStream to a Writer.
     *
     * @param sin The InputStream to read in.
     * @param out The Writer to write to.
     * @throws IOException
     */
    public static void writeStreamOut(InputStream sin, Writer out) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(sin));
        char[] buffer = new char[BUFFER_SIZE];
        int size;
        while ((size = in.read(buffer)) != -1) {
            out.write(buffer, 0, size);
        }
    }
    
    /**
     * Checks to see if the directory (passed in as a String) exists. If it
     * doesn't, then it tries to create it. If all that fails, it returns <code>false</code>.
     *
     * @param strDir The directory to check to see if it exist.
     * @return <code>true</code> if everything goes well. <code>false</code> if something goes wrong.
     */
    public static boolean createDirectoryIfDoesNotExist(String strDir){
        boolean flag;
        final File dir = new File(strDir);
        
        if(dir.exists() && dir.isDirectory()) {
            flag = true;
        } else {
            flag = dir.mkdir();
        }

        return flag;
    }
    
    /**
     * Returns a boolean indicating whether a file exists at the 
     * passed filePath.
     *
     * @param filePath The file path and file name to check.
     * @return fileExists A true if the file exists otherwise a false.
     */
    public static boolean doesFileExist(String filePath) {
        final File file = new File(filePath);
        return file.exists();
    }
    
    /**
     * Gets a buffered file reader.
     * 
     * @param fullPathName The full path to retrieve the file.
     * @return reader A BufferedReader.
     * @throws RuntimeException if the file is not found given the path.
     */
    public static BufferedReader getBufferedFileReader(String fullPathName) {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(fullPathName));           
            return reader;
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException("File: " + fullPathName + " does not exist." + fnfe);
        }
    }
    
    /**
     * Gets the scanner for the passed in file path and name.
     * 
     * @param fullPathName The full path name.
     * @return reader A Scanner.
     * @throws RuntimeException if the file is not found given the path.
     */
    public static Scanner getScanner(String fullPathName) {
        try {
            final Scanner reader = new Scanner(new File(fullPathName));
            
            return reader;
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException("File: " + fullPathName + " does not exist." + fnfe);
        }
    }   
}

