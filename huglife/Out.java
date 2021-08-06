package huglife;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 *  This class provides methods for writing strings and numbers to
 *  various output streams, including standard output, file, and sockets.
 *  <p>
 *  For additional documentation, see
 *  <a href="http://introcs.cs.princeton.edu/31datatype">Section 3.1</a> of
 *  <i>Introduction to Programming in Java: An Interdisciplinary Approach</i>
 *  by Robert Sedgewick and Kevin Wayne.
 *
 *  *************************************************************************
 *  *  Compilation:  javac Out.java
 *  *  Execution:    java Out
 *  *
 *  *  Writes data of various types to: stdout, file, or socket.
 *  *
 *  *************************************************************************
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Out {

    // force Unicode UTF-8 encoding; otherwise it's system dependent
    private static final String CHARSET_NAME = StandardCharsets.UTF_8.name();

    // assume language = English, country = US for consistency with In
    private static final Locale LOCALE = Locale.US;

    private PrintWriter writer;

   /**
     * Create an Out object using an OutputStream.
     */
    public Out(OutputStream os) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(os, CHARSET_NAME);
            writer = new PrintWriter(osw, true);
        }
        catch (IOException e) { e.printStackTrace(); }
    }

   /**
     * Create an Out object using standard output.
     */
    public Out() { this(System.out); }

   /**
     * Create an Out object using a Socket.
     */
    public Out(Socket socket) {
        try {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, CHARSET_NAME);
            writer = new PrintWriter(osw, true);
        }
        catch (IOException e) { e.printStackTrace(); }
    }

   /**
     * Create an Out object using a file specified by the given name.
     */
    public Out(String s) {
        try {
            OutputStream os = new FileOutputStream(s);
            OutputStreamWriter osw = new OutputStreamWriter(os, CHARSET_NAME);
            writer = new PrintWriter(osw, true);
        }
        catch (IOException e) { e.printStackTrace(); }
    }

   /**
     * Close the output stream.
     */
    public void close() { writer.close(); }



   /**
     * Terminate the line.
     */
    public void println() {
        writer.println();
    }

   /**
     * Print an object and then terminate the line.
     */
    public void println(Object x) {
        writer.println(x);
    }

   /**
     * Print a boolean and then terminate the line.
     */
    public void println(boolean x) {
        writer.println(x);
    }

   /**
     * Print a char and then terminate the line.
     */
    public void println(char x) {
        writer.println(x);
    }

   /**
     * Print an double and then terminate the line.
     */
    public void println(double x) {
        writer.println(x);
    }

   /**
     * Print a float and then terminate the line.
     */
    public void println(float x) {
        writer.println(x);
    }

   /**
     * Print an int and then terminate the line.
     */
    public void println(int x) {
        writer.println(x);
    }

   /**
     * Print a long and then terminate the line.
     */
    public void println(long x) {
        writer.println(x);
    }

   /**
     * Print a byte and then terminate the line.
     */
    public void println(byte x) {
        writer.println(x);
    }



   /**
     * Flush the output stream.
     */
    public void print() {
        writer.flush();
    }

   /**
     * Print an object and then flush the output stream.
     */
    public void print(Object x) {
        writer.print(x);
        writer.flush();
    }

   /**
     * Print an boolean and then flush the output stream.
     */
    public void print(boolean x) {
        writer.print(x);
        writer.flush();
    }

   /**
     * Print an char and then flush the output stream.
     */
    public void print(char x) {
        writer.print(x);
        writer.flush();
    }

   /**
     * Print an double and then flush the output stream.
     */
    public void print(double x) {
        writer.print(x);
        writer.flush();
    }

   /**
     * Print a float and then flush the output stream.
     */
    public void print(float x) {
        writer.print(x);
        writer.flush();
    }

   /**
     * Print an int and then flush the output stream.
     */
    public void print(int x) {
        writer.print(x);
        writer.flush();
    }

   /**
     * Print a long and then flush the output stream.
     */
    public void print(long x) {
        writer.print(x);
        writer.flush();
    }

   /**
     * Print a byte and then flush the output stream.
     */
    public void print(byte x) {
        writer.print(x);
        writer.flush();
    }

   /**
     * Print a formatted string using the specified format string and arguments,
     * and then flush the output stream.
     */
    public void printf(String format, Object... args) {
        writer.printf(LOCALE, format, args);
        writer.flush();
    }

   /**
     * Print a formatted string using the specified locale, format string and arguments,
     * and then flush the output stream.
     */
    public void printf(Locale locale, String format, Object... args) {
        writer.printf(locale, format, args);
        writer.flush();
    }


   /**
     * A test client.
     */
    public static void main(String[] args) {
        Out out;

        // write to stdout
        out = new Out();
        out.println("Test 1");
        out.close();

        // write to a file
        out = new Out("test.txt");
        out.println("Test 2");
        out.close();
    }

}
