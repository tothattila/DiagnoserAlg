package org.diagnoser.algorithm;

import org.diagnoser.algorithm.exception.InvalidDiagnoserParameter;
import org.diagnoser.algorithm.exception.TooFewDeviations;
import org.diagnoser.model.JaxbParser;
import org.diagnoser.model.exception.InvalidHazid;
import org.diagnoser.model.internal.*;
import org.diagnoser.model.internal.element.HazidElement;
import org.diagnoser.model.internal.element.RootCause;
import org.diagnoser.model.internal.exception.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/3/12
 * Time: 11:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainClass {

    private static DiagnoserParameters params;

    public static void main(final String[] args) {

        try {
            params=DiagnoserParameters.getInstance(args);
        } catch (InvalidDiagnoserParameter exc) {
            LogPrinter.printAndExit("Usage: ./command <analysableTrace> [verbose] [diagnose <startTraceId>]");
        }
        LogPrinter.printMessage("");
        LogPrinter.printCaption("   BEGIN DIAGNOSIS   ");
        Map<String,HazidTable> compactHazid = null;
        Map<String,Trace> compactNominalTrace = null;
        Trace compactTrace = null;

        checkDir(params.getHazidDir());
        checkDir(params.getTraceDir());
        checkFile(params.getAnalysableTrace());
        try {
            compactHazid = new JaxbParser().parseHazidXmlDir(params.getHazidDir());
            if (compactHazid == null) {
                LogPrinter.printAndExit("Hazid XML problem. Exiting.");
            }

            compactNominalTrace = new JaxbParser().parseTraceDir(params.getTraceDir());
            if (compactNominalTrace == null) {
                LogPrinter.printAndExit("Nominal-trace XML problem. Exiting.");
            }

            compactTrace = new JaxbParser().parseTraceXml(params.getAnalysableTrace());
            if (compactTrace == null) {
                LogPrinter.printAndExit("Compact-trace XML problem. Exiting.");
            }

            LogPrinter.printMessage("Parsing completed.");
            LogPrinter.printMessage("Analysis trace: \n" + compactTrace.toString() + " in file " + params.getAnalysableTrace());

            if (!params.isVerbose()) {
                LogPrinter.disable();
            }

            LogPrinter.printMessage("Parsed HAZID table:");
            printAllHazid(compactHazid);

        } catch (JAXBException exc) {
            LogPrinter.printAndExit("Exception happened during parsing. ", exc);
        } catch (AlreadyPresentException e) {
            LogPrinter.printAndExit("Exception happened during parsing. ", e);
        } catch (CorruptTraceException e) {
            LogPrinter.printAndExit("Exception happened during parsing. ", e);
        } catch (InvalidOutput e) {
            LogPrinter.printAndExit("Exception happened during parsing. ", e);
        }  catch (InvalidTraceFragment invalidTraceFragment) {
            LogPrinter.printAndExit(" An invalid tarce fragment found.", invalidTraceFragment);
        } catch (InvalidHazid invalidCommand) {
            LogPrinter.printAndExit("Hazid parsing problem ", invalidCommand);
        }

        LogPrinter.enable();

        if (!params.isDiagnose()) {
            LogPrinter.printCaption("PARSING COMPLETE");
            LogPrinter.printAndExit("Diagnosis skipped by command line argument. Goodbye.");
        }

        LogPrinter.printCaption("   CONSISTENCY CHECK   ");
        consistencyCheck(compactNominalTrace,compactHazid,params.getStartNominalTrace());

        LogPrinter.printCaption("   EXECUTING DIAGNOSIS ALGORITHM   ");

        if (!params.isVerbose()) {
            LogPrinter.disable();
        }

        DiagnosticAlgorithm algorithm = new DiagnosticAlgorithm(compactNominalTrace, compactTrace, compactHazid, params.getStartNominalTrace());
        try {
            algorithm.run();
        } catch (InvalidOutputSize invalidOutputSize) {
            LogPrinter.printAndExit("Invalid output size. ", invalidOutputSize);
        } catch (TooFewDeviations tooFewDeviations) {
            LogPrinter.printAndExit("Too few deviations. ", tooFewDeviations);
        }

        LogPrinter.printCaption("     DIAGNOSTIC FINAL RESULTS      ");

        LogPrinter.printMessage("Identified root causes:");
        for (RootCause irc : algorithm.getIrc()) {
            LogPrinter.printMessage(irc.toString());
        }

        LogPrinter.printMessage("Identified non-root causes:");
        for (HazidElement elm : algorithm.getNrc()) {
            LogPrinter.printMessage(elm.toString());
        }
    }



    private static void printAllHazid(Map<String, HazidTable> compactHazid) {
        for (String s : compactHazid.keySet()) {
            LogPrinter.printCaption(s);
            LogPrinter.printMessage(compactHazid.get(s).toString());
        }
    }



    private static void checkFile(String... args) {
        for (String file : args) {
            if(! new File(file).isFile()) {
                LogPrinter.printAndExit("Could not find file : " + file);
            }
        }
    }

    private static boolean consistencyCheck(Map<String,Trace> traces, Map<String,HazidTable> hazidTables, String startNominal) {
        if (traces.get(startNominal) == null) {
            LogPrinter.printAndExit("Start nominal trace named `"+startNominal+"` could not be found among traces.");
            return false;
        }

        for (Trace t:traces.values()) {
            if (hazidTables.get(t.getAssociatedHazid()) == null) {
                LogPrinter.printAndExit("HAZID table `"+t.getAssociatedHazid()+"` referred by trace `"+t.getName()+"` could not be found among the set of HAZID tables.");
                return false;
            }
        }

        return true;
    }

    private static void checkDir(String... args) {
        for (String dir : args) {
            if(! new File(dir).isDirectory()) {
                LogPrinter.printAndExit("Could not find directory : " + dir);
            }
        }
    }


}
