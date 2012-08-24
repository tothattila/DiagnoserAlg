package org.diagnoser.algorithm;

import org.diagnoser.algorithm.exception.TooFewDeviations;
import org.diagnoser.model.JaxbParser;
import org.diagnoser.model.exception.InvalidFormatException;
import org.diagnoser.model.exception.UnsupportedHazidType;
import org.diagnoser.model.internal.*;
import org.diagnoser.model.internal.exception.*;
import org.diagnoser.model.xml.compact.hazid.Procedurehazidtable;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/3/12
 * Time: 11:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainClass {

    public static void main(final String[] args) {

        if (args.length < 3) {
            LogPrinter.printAndExit("Usage: ./command <hazidXml> <nominalTrace> <analysableTrace> [verbose]");
        }
        LogPrinter.printMessage("");
        LogPrinter.printCaption("   BEGIN DIAGNOSIS   ");
        HazidTable compactHazid = null;
        Trace compactNominalTrace = null;
        Trace compactTrace = null;

        checkFile(args[0], args[1], args[2]);



        try {
            compactHazid = new JaxbParser().parseHazidXml(args[0]);
            if (compactHazid == null) {
                LogPrinter.printAndExit("Hazid XML problem. Exiting.");
            }

            compactNominalTrace = new JaxbParser().parseTraceXml(args[1]);
            if (compactNominalTrace == null) {
                LogPrinter.printAndExit("Nominal-trace XML problem. Exiting.");
            }

            compactTrace = new JaxbParser().parseTraceXml(args[2]);
            if (compactTrace == null) {
                LogPrinter.printAndExit("Compact-trace XML problem. Exiting.");
            }

            LogPrinter.printMessage("Parsing completed.");
            LogPrinter.printMessage("Nominal trace:  \n" + compactNominalTrace.toString() + " in file " + args[1]);
            LogPrinter.printMessage("Analysis trace: \n" + compactTrace.toString() + " in file " + args[2]);

            if (!isVerbose(args)) {
                LogPrinter.disable();
            }

            LogPrinter.printMessage("Parsed HAZID table:");
            LogPrinter.printMessage(compactHazid.toString());

        } catch (JAXBException exc) {
            LogPrinter.printAndExit("Exception happened during parsing. ", exc);
        } catch (AlreadyPresentException e) {
            LogPrinter.printAndExit("Exception happened during parsing. ", e);
        } catch (CorruptTraceException e) {
            LogPrinter.printAndExit("Exception happened during parsing. ", e);
        } catch (InvalidOutput e) {
            LogPrinter.printAndExit("Exception happened during parsing. ", e);
        } catch (UnsupportedHazidType unsupportedHazidType) {
            LogPrinter.printAndExit("Exception happened during parsing. ", unsupportedHazidType);
        } catch (InvalidFormatException e) {
            LogPrinter.printAndExit("Exception happened during parsing. ", e);
        } catch (InvalidTraceFragment invalidTraceFragment) {
            LogPrinter.printAndExit(" An invalid tarce fragment found.", invalidTraceFragment);
        }

        LogPrinter.printCaption("   EXECUTING DIAGNOSIS ALGORITHM   ");

        DiagnosticAlgorithm algorithm = new DiagnosticAlgorithm(compactNominalTrace, compactTrace, compactHazid);
        try {
            algorithm.run();
        } catch (InvalidOutputSize invalidOutputSize) {
            LogPrinter.printAndExit("Invalid output size. ", invalidOutputSize);
        } catch (TooFewDeviations tooFewDeviations) {
            LogPrinter.printAndExit("Too few deviations. ", tooFewDeviations);
        }

        if (!isVerbose(args)) {
           LogPrinter.enable();
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

    private static boolean isVerbose(String[] args) {
        if (args.length > 3) {
            return "verbose".equals(args[3]);
        }
        return false;
    }

    private static void checkFile(String... args) {
        for (String file : args) {
            if(! new File(file).isFile()) {
                LogPrinter.printAndExit("Could not find file : " + file);
            }
        }
    }


}
