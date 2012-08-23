package org.diagnoser.model.internal;

import org.diagnoser.model.internal.exception.InvalidOutputSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class TraceFragment {

    private List<String> inputs;
    private List<String> outputs;

    public TraceFragment(final List<String> inputs, final List<String> outputs) {

        this.inputs = inputs;
        this.outputs = outputs;

    }

    public TraceFragment(final String inputs, final String outputs) {

        this(Arrays.asList(inputs.split(",")), Arrays.asList(outputs.split(",")));

    }

    public String toString() {
        String result = "";

        for (String in:inputs) { result += in + ","; }
        result = result.substring(0, result.length()-1);

        result += ";";
        for (String out:outputs) { result += out + ","; }
        result = result.substring(0, result.length()-1);

        return result;
    }

    public boolean compareInputWith(TraceFragment otherFragment) {
        if (otherFragment.inputs.size() != inputs.size()) {
            return false;
        }

        for (int i=0;i<inputs.size();i++) {

            if (!inputs.get(i).equals(otherFragment.inputs.get(i))) {
               return false;
            }
        }

        return true;

    }

    public List<Deviation> compareOutputWith(TraceFragment otherFragment) throws InvalidOutputSize {
        if (otherFragment.outputs.size() != outputs.size()) {
            throw new InvalidOutputSize("Cannot compare `" + toString() + "` with `" + otherFragment.toString() + "`");
        }
        ArrayList<Deviation> results = new ArrayList<Deviation>();
        for (int i=0;i<outputs.size();i++) {
            switch(compareQualitativeValues(outputs.get(i), otherFragment.outputs.get(i))) {
                case EQUALS: break;
                case SMALLER: results.add(new Deviation(Keyword.SMALLER, this)); break;
                case GREATER: results.add(new Deviation(Keyword.GREATER, this)); break;
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

    public boolean equals(final TraceFragment other) {

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
