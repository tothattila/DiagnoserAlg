package org.diagnoser.algorithm;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/12/12
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogPrinter {

    private static boolean print=true;

    public static void disable() {
        print = false;
    }

    public static void enable() {
        print = true;
    }

    public static void printMessage(final String s) {
        if (print) {
           System.out.println(s);
        }
    }

    public static void printAndExit(final String s) {
        printMessage(s);
        System.exit(-1);
    }

    public static void printAndExit(final String s, final Throwable e) {
        printMessage(s);
        e.printStackTrace();
        System.exit(-1);
    }

    public static void printCaption(final String s) {
       printMessage(createCaptionLine(s.length()));
       printMessage(s);
       printMessage(createCaptionLine(s.length()));
    }

    private static String createCaptionLine(final int length) {
        final String captionChar = "=";
        if (length>1) {
            return captionChar + createCaptionLine(length-1);
        }
        else
        {
           return captionChar;
        }
    }
}
