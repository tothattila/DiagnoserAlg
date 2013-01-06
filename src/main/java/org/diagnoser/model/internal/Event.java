package org.diagnoser.model.internal;

import org.diagnoser.model.internal.deviation.Deviation;
import org.diagnoser.model.internal.exception.InvalidOutputSize;
import sun.applet.resources.MsgAppletViewer_sv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class Event {

    private Map<String,String> inputs;
    private Map<String,String> outputs;

    public Event(final Map<String,String> inputs, final Map<String,String> outputs) {

        this.inputs = inputs;
        this.outputs = outputs;

    }

    public Event(final String traceFragmentWithTime) {
        this(EventParser.extractInputMap(traceFragmentWithTime), EventParser.extractOutputMap(traceFragmentWithTime));
    }

    public String toString() {
        String result = "";

        for (String in:inputs.keySet()) { result += "["+in+"]:'" + inputs.get(in) + "',"; }
        result = result.substring(0, result.length()-1);

        result += ";";
        for (String out:outputs.keySet()) { result += "["+out+"]:'" + outputs.get(out) + "',"; }
        result = result.substring(0, result.length()-1);

        return result;
    }

    public boolean compareInputWith(Event otherFragment) {
        if (otherFragment.inputs.size() != inputs.size()) {
            return false;
        }

        for (String i:inputs.keySet()) {

            if (!inputs.get(i).equals(otherFragment.inputs.get(i))) {
               return false;
            }
        }
        return true;
    }

    public List<Deviation> compareOutputWith(Event otherFragment) throws InvalidOutputSize {
        if (otherFragment.outputs.size() != outputs.size()) {
            throw new InvalidOutputSize("Cannot compare `" + toString() + "` with `" + otherFragment.toString() + "`");
        }
        ArrayList<Deviation> results = new ArrayList<Deviation>();
        for (String outputIndex:outputs.keySet()) {
            switch(compareQualitativeValues(outputs.get(outputIndex), otherFragment.outputs.get(outputIndex))) {
                case EQUALS: break;
                case SMALLER: results.add(new Deviation(KeyWord.createSmaller(outputIndex), this)); break;
                case GREATER: results.add(new Deviation(KeyWord.createGreater(outputIndex), this)); break;
            }
        }
        return results;
    }

    private QualitativeRelation compareQualitativeValues(final String value, final String comparedTo) {

        if (QualitativeValue.valueOf(value).ordinal() > QualitativeValue.valueOf(comparedTo).ordinal()) {
            return QualitativeRelation.SMALLER;
        } else if (QualitativeValue.valueOf(value).ordinal() < QualitativeValue.valueOf(comparedTo).ordinal()) {
            return QualitativeRelation.GREATER;
        }

        return QualitativeRelation.EQUALS;
    }

    public boolean equals(final Event other) {

       boolean result;

       try {
          result = (compareInputWith(other) && compareOutputWith(other).isEmpty());
       } catch (InvalidOutputSize invalidOutputSize) {
          result = false;
       }


        return result;
    }

    private enum QualitativeRelation {
        EQUALS,SMALLER,GREATER;
    }
}
