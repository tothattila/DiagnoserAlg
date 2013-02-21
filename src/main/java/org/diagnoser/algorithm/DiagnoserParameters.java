package org.diagnoser.algorithm;

import org.diagnoser.algorithm.exception.InvalidDiagnoserParameter;

import java.io.File;
import java.security.InvalidParameterException;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 2/21/13
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class DiagnoserParameters {

    public static final String COMMON_TRACE_DIR = "." + File.separator + "trace";
    private static String COMMON_HAZID_DIR = "." + File.separator + "hazid";

    private boolean verbose;
    private String hazidDir;
    private String traceDir;
    private boolean diagnose;
    private String analysableTrace;
    private String initialNominalTrace;

    public static DiagnoserParameters getInstance(String[] unParsedParameters) throws InvalidDiagnoserParameter {
        return new DiagnoserParameters(unParsedParameters);
    }

    private DiagnoserParameters(String[] unParsedParameters) throws InvalidDiagnoserParameter {
        if (unParsedParameters.length<1) {
            throw new InvalidDiagnoserParameter("Not enough parameters");
        }
        verbose=isVerbose(unParsedParameters);
        diagnose = isDiagnose(unParsedParameters);
        analysableTrace = unParsedParameters[0];
        hazidDir = COMMON_HAZID_DIR;
        traceDir = COMMON_TRACE_DIR;
        initialNominalTrace = extractNominalTraceDir(unParsedParameters);

    }

    private String extractNominalTraceDir(final String[] args) throws InvalidDiagnoserParameter {
        for (int i=0;i<args.length;i++) {
            if ( ("diagnose".equals(args[i])) && (args.length>(i+1))) {
                return args[i+1];
            }
        }
       throw new InvalidDiagnoserParameter("Not provided diagnose mandatory parameter.");
    }

    public boolean isVerbose() {
        return verbose;
    }

  public boolean isDiagnose() {
        return diagnose;
    }
    public String getHazidDir() {
        return hazidDir;
    }

    public String getTraceDir() {
        return traceDir;
    }

    public String getAnalysableTrace() {
        return analysableTrace;
    }

    private static boolean isVerbose(String[] args) {
        for (String s: args) {
            if ("verbose".equals(s)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagnose(String[] args) {
        for (String s: args) {
            if ("diagnose".equals(s)) {
                return true;
            }
        }
        return false;
    }

    public String getStartNominalTrace() {
        return initialNominalTrace;
    }


}


