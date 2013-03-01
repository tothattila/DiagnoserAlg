package org.diagnoser.model.internal;

import org.diagnoser.algorithm.LogPrinter;
import org.diagnoser.model.internal.element.Deviation;
import org.diagnoser.model.internal.exception.InvalidOutputSize;
import org.diagnoser.model.internal.parser.EventParser;

import java.util.ArrayList;
import java.util.HashMap;
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

    public Map<String,String> getInputs() {
        return new HashMap<String,String>(inputs);
    }

    public Map<String,String> getOutputs() {
        return new HashMap<String,String>(outputs);
    }

    public String toString() {
        String result = "";

        for (String in:inputs.keySet()) { result += "["+in+"]:'" + inputs.get(in) + "',"; }
        if (inputs.keySet().size()!=0) { result = result.substring(0, result.length()-1); }

        result += ";";
        for (String out:outputs.keySet()) { result += "["+out+"]:'" + outputs.get(out) + "',"; }
        if (outputs.keySet().size()!=0) { result = result.substring(0, result.length()-1); }

        return result;
    }

    public boolean compareInputWith(Event otherFragment) {

        Map<String,String> otherInputs = otherFragment.inputs;

        for (String otherKey:otherInputs.keySet()) {
            if (inputs.containsKey(otherKey) && !inputs.get(otherKey).equals(otherInputs.get(otherKey))) {
               return false;
            }
        }
        return true;
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
    public List<Deviation> compareOutputWith(Event otherFragment) throws InvalidOutputSize {

        Map<String,String> otherOutputs = otherFragment.outputs;
        ArrayList<Deviation> results = new ArrayList<Deviation>();
        for (String outputName:otherOutputs.keySet()) {
            if (outputs.containsKey(outputName)) {
                LogPrinter.printMessage("Comparing `"+this.toString()+"` with `" +  otherFragment.toString() + "`");
                switch(compareQualitativeValues(outputs.get(outputName), otherOutputs.get(outputName))) {
                    case EQUALS: break;
                    case SMALLER: results.add(new Deviation(KeyWord.createSmaller(outputName), this)); break;
                    case GREATER: results.add(new Deviation(KeyWord.createGreater(outputName), this)); break;
                }
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

    private enum QualitativeRelation {
        EQUALS,SMALLER,GREATER;
    }
}
