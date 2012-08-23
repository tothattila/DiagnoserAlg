package org.diagnoser.model.internal;

import org.diagnoser.model.internal.exception.InvalidInputFormat;
import org.diagnoser.model.internal.exception.InvalidTraceFragment;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeviationAtTime extends Deviation {

    private Integer timeInstant;

    public DeviationAtTime(final Keyword keyWord, final Integer time, final TraceFragment originalFragment) {
        super(keyWord,originalFragment);
        this.timeInstant = time;
    }

    public DeviationAtTime(final Deviation deviation, final Integer time) {
        super(deviation.keyWord, deviation.fragment);
        this.timeInstant = time;
    }

    public DeviationAtTime(final Keyword keyWord, final String traceFragmentWithTime)  {
        super(keyWord, new TraceFragment(traceFragmentWithTime.split(";")[1], traceFragmentWithTime.split(";")[2]));
        this.timeInstant = Integer.valueOf(traceFragmentWithTime.split(";")[0]);
    }

    public DeviationAtTime(DeviationAtTime devAtTime) {
        super(devAtTime.keyWord, devAtTime.fragment);
        this.timeInstant = devAtTime.timeInstant;
    }

    @Override
    public boolean equals(HazidElement hazidElement) {
        if (hazidElement instanceof DeviationAtTime) {

            Keyword otherKeyword = ((DeviationAtTime)hazidElement).keyWord;
            TraceFragment otherFragment = ((DeviationAtTime)hazidElement).fragment;
            Integer otherTimeInstant = ((DeviationAtTime)hazidElement).timeInstant;

            if (keyWord.equals(otherKeyword) && fragment.equals(otherFragment) && timeInstant == otherTimeInstant ) {
                return true;
            }
        }
        return false;
    }

    public Integer getTime() {
        return new Integer(timeInstant);
    }

    public String toString() {
        String buffer = new String();
        buffer += keyWord.name() + "(" + timeInstant + ";" + fragment.toString() + ")";
        return buffer;
    }

}
