package org.diagnoser.algorithm;

import org.diagnoser.algorithm.exception.TooFewDeviations;
import org.diagnoser.model.internal.*;
import org.diagnoser.model.internal.exception.InvalidOutputSize;


import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 9:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class DiagnosticAlgorithm {
    private Trace nominalTrace;
    private Trace analysisTrace;
    private HazidTable hazidTable;

    private List<RootCause> identifiedRootCauses;
    private List<HazidElement> identifiedNonRootCauses;

    public DiagnosticAlgorithm(Trace nominalTrace, Trace analysisTrace, HazidTable hazidTable) {
        this.nominalTrace = nominalTrace;
        this.analysisTrace = analysisTrace;
        this.hazidTable = hazidTable;

        identifiedRootCauses = new ArrayList<RootCause>();
        identifiedNonRootCauses = new ArrayList<HazidElement>();
    }

    public List<RootCause> getIrc() {
        return identifiedRootCauses;
    }

    public List<HazidElement> getNrc() {
        return identifiedNonRootCauses;
    }

    public void run() throws InvalidOutputSize, TooFewDeviations {

        List<DeviationAtTime> allDeviations = nominalTrace.collectDeviationsFrom(analysisTrace);
        LogPrinter.printMessage("All Deviations:");
        for(Deviation d:allDeviations) {
            LogPrinter.printMessage(d.toString() );
        }

        if (allDeviations.size()<2) {
            throw new TooFewDeviations("Only found " + allDeviations.size() + " number of deviations. Cannot perform diagnosis, quitting.");
        }

        List<DeviationAtTime> initialDeviations;
        int beginTimeInstant = nominalTrace.getLength() -1;
        int endTimeInstant = nominalTrace.getLength();

        do {
           if (beginTimeInstant < 0) {
               throw new TooFewDeviations("Could not find proper pairs to start diagnostic algorithm. Cannot perform diagnosis, quitting.");
           }
           initialDeviations = extractTimeInstantsFromDeviationList(allDeviations, beginTimeInstant, endTimeInstant);
           beginTimeInstant = beginTimeInstant - 1;
           endTimeInstant = endTimeInstant - 1;
        } while (initialDeviations.size() < 2);
        LogPrinter.printMessage("Initial Deviations (to begin HAZID table search):");
        for(Deviation d:initialDeviations) {
            LogPrinter.printMessage(d.toString());
        }

        LogPrinter.printMessage("Deviation pairs to start from:");

        List<List<DeviationAtTime>> deviationPairs = generateDeviationPairsFrom(initialDeviations);
        for(List<DeviationAtTime> d:deviationPairs) {
            LogPrinter.printMessage("Pair:" + d.get(0).toString() + ";" + d.get(1).toString());
        }

        LogPrinter.printMessage(" Starting recursive traversal in HAZID table");

        for(List<DeviationAtTime> d:deviationPairs) {
            ArrayList<HazidElement> path = new ArrayList<HazidElement>();
            path.add(d.get(1));
            path.add(d.get(0));
            recursiveHazidSearch(d.get(0), d.get(1), 1, new HashSet<DeviationAtTime>(allDeviations), path);
        }

    }

    private void recursiveHazidSearch(final HazidElement deviation, final HazidElement implication, int level, Set<DeviationAtTime> allDeviations, final List<HazidElement> path) {
       List<HazidElement> possibleCauses = hazidTable.getCauseForDeviationAndImplication(deviation, implication);
       if (possibleCauses.isEmpty()) {
           LogPrinter.printMessage("NO MATCHING CAUSE FOR PATH " + convertPathToString(path));
       }
       else {
           for (HazidElement possibleCause:removeDuplications(possibleCauses)) {
               path.add(possibleCause);
               if (possibleCause instanceof RootCause) {
                   LogPrinter.printMessage("ROOT CAUSE FOUND: " + convertPathToString(path));
                   identifiedRootCauses.add((RootCause)possibleCause);
               } else if(deviationSetContainDeviationBeforeDeviation(allDeviations, possibleCause, implication)) {
                  recursiveHazidSearch(possibleCause, deviation, level + 1, allDeviations, new ArrayList<HazidElement>(path));
               } else {
                   LogPrinter.printMessage("NON-ROOT CAUSE FOUND " + convertPathToString(path));
                  identifiedNonRootCauses.add(possibleCause);
               }
               path.remove(path.size() - 1);
           }
       }
    }

    private List<HazidElement> removeDuplications(List<HazidElement> possibleCauses) {
        List<HazidElement> noDuplicates = new ArrayList<HazidElement>();

        for (HazidElement h : possibleCauses) {
           if (!containsHazidElement(noDuplicates,h)) {
               noDuplicates.add(h);
           }
        }
        return noDuplicates;
    }

    private boolean containsHazidElement(List<HazidElement> hazidElList, HazidElement h) {
        for (HazidElement hzd : hazidElList) {
            if (hzd.equals(h)) {
                return true;
            }
        }
        return false;
    }

    private String convertPathToString(List<HazidElement> path) {
        String result = "";
        final String separator = " -> ";
        for (HazidElement hazidElement : path) {
           result += hazidElement.toString() + " -> ";
        }
        result = result.substring(0, result.length() - separator.length());
        return result;
    }

    private boolean deviationSetContainDeviationBeforeDeviation(Set<DeviationAtTime> allDeviations, HazidElement possibleCause, HazidElement implication) {
        if(implication instanceof DeviationAtTime) {
           for (DeviationAtTime deviation : allDeviations) {
               if (deviation.equals(possibleCause) && (deviation.getTime() < ((DeviationAtTime) implication).getTime())) {
                   return true;
               }
           }
           return false;
        } else {
           return false;
        }
    }

    private String createLevelIndent(int level) {
        String l = "";
        for (int i=0;i<level;i++) {
            l+=" ";
        }
        return l;
    }

    private List<List<DeviationAtTime>> generateDeviationPairsFrom(final List<DeviationAtTime> deviationList) {
        List<List<DeviationAtTime>> pairs = new ArrayList<List<DeviationAtTime>>();

        for (DeviationAtTime first : deviationList) {


           for (DeviationAtTime second : deviationList) {
               if (!first.equals(second)) {
                   if (first.getTime() < second.getTime()) {
                       List<DeviationAtTime> pair = new ArrayList<DeviationAtTime>();
                       pair.add(first);
                       pair.add(second);
                       pairs.add(pair);
                    }
               }

           }
        }

        return orderPairByTime(pairs);
    }

    private List<List<DeviationAtTime>> orderPairByTime(List<List<DeviationAtTime>> pairs) {
        for (List<DeviationAtTime> pair : pairs) {
            if ((pair.get(0)).getTime() > (pair.get(1)).getTime()) {
               DeviationAtTime puffer = pair.get(0);
               pair.set(0,pair.get(1));
               pair.set(1,puffer);
            }

        }
        return pairs;
    }

    private List<List<DeviationAtTime>> dropWhereTimeIsTheSame(List<List<DeviationAtTime>> pairs) {
        for (List<DeviationAtTime> pair : pairs) {
            if ((pair.get(0)).getTime() == (pair.get(1)).getTime()) {
                pairs.remove(pair);
            }

        }
        return pairs;
    }

    private List<DeviationAtTime> extractTimeInstantsFromDeviationList(List<DeviationAtTime> allDeviations, Integer... timeInstants) {
       final List<DeviationAtTime> trimmedDeviationList = new ArrayList<DeviationAtTime>();
       final List<Integer> toBeExtracted = Arrays.asList(timeInstants);

       for (DeviationAtTime devAtTime : allDeviations) {
          if (toBeExtracted.contains(devAtTime.getTime())) {
              trimmedDeviationList.add(new DeviationAtTime(devAtTime));
          }
       }

       return trimmedDeviationList;
    }
}
