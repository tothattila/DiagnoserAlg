package org.diagnoser.model.internal.deviation;

import org.diagnoser.model.internal.Event;
import org.diagnoser.model.internal.EventParser;
import org.diagnoser.model.internal.HazidElement;
import org.diagnoser.model.internal.KeyWord;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeviationAtTime extends Deviation {

    private Integer timeInstant;

    public DeviationAtTime(final KeyWord keyWord, final Integer time, final Event originalFragment) {
        super(keyWord,originalFragment);
        this.timeInstant = time;
    }

    public DeviationAtTime(final Deviation deviation, final Integer time) {
        super(deviation.keyWord, deviation.fragment);
        this.timeInstant = time;
    }

    public DeviationAtTime(final KeyWord keyWord, final String traceFragmentWithTime)  {
        super(keyWord, new Event(traceFragmentWithTime));
        this.timeInstant = EventParser.extractTimeInstant(traceFragmentWithTime);
    }

    public DeviationAtTime(DeviationAtTime devAtTime) {
        super(devAtTime.keyWord, devAtTime.fragment);
        this.timeInstant = devAtTime.timeInstant;
    }

    @Override
    public boolean equalsWithOtherHazidElement(HazidElement hazidElement) {
        if (hazidElement instanceof DeviationAtTime) {

            KeyWord otherKeyword = ((DeviationAtTime)hazidElement).keyWord;
            Event otherFragment = ((DeviationAtTime)hazidElement).fragment;
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
        buffer += keyWord.toString() + "(" + timeInstant + ";" + fragment.toString() + ")";
        return buffer;
    }

}
