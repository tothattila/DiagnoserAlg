package org.diagnoser.model.internal;

import org.diagnoser.model.internal.deviation.Deviation;
import org.diagnoser.model.internal.deviation.DeviationAtTime;
import org.diagnoser.model.internal.exception.AlreadyPresentException;
import org.diagnoser.model.internal.exception.InvalidOutputSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class Trace {

    private Map<Integer, Event> trace;
    private String name;

    public Trace(final String name) {
        this.name = name;
        trace = new HashMap<Integer, Event>();
    }

    public void addFragment(Integer timeInstant, Event traceFragment) throws AlreadyPresentException {
        if (trace.containsKey(timeInstant)) {
            throw new AlreadyPresentException("Trace `" + name + "` already contains value for time instant `" + timeInstant + "`. The value is `" + trace.get(timeInstant).toString() + "` while the new value to insert was `"+traceFragment.toString()+"`");
        }
        trace.put(timeInstant, traceFragment);
    }

    public String toString() {
        StringBuilder buffer=new StringBuilder();
        buffer.append("-=[ TRACE NAME: " + name + "]=-\n");
        for (Integer i:trace.keySet()) {
            buffer.append("(" + i + "," + trace.get(i).toString() + ")");
        }
        return buffer.toString();
    }

    public List<DeviationAtTime> collectDeviationsFrom(final Trace otherTrace) throws InvalidOutputSize {

        final ArrayList<DeviationAtTime> result = new ArrayList<DeviationAtTime>();

        for(Integer time:trace.keySet()) {
            result.addAll(calculateDeviationAtTimeFrom(otherTrace,time));
        }


        for (Integer time :trace.keySet()) {
            if (!otherTrace.containsFragment(trace.get(time))) {
                result.add(new DeviationAtTime(KeyWord.createNeverHappened(), time, trace.get(time)));
            }
            else if (otherTrace.getFragmentFirstOccurenceInTime(trace.get(time)) < this.getFragmentFirstOccurenceInTime(trace.get(time))) {
                result.add(new DeviationAtTime(KeyWord.createEarlier(), time, trace.get(time)));
            }
            else if (otherTrace.getFragmentFirstOccurenceInTime(trace.get(time)) > this.getFragmentFirstOccurenceInTime(trace.get(time))) {
                result.add(new DeviationAtTime(KeyWord.createLater(), time, trace.get(time)));
            }
        }

        return result;
    }

    public List<DeviationAtTime> calculateDeviationAtTimeFrom(final Trace otherTrace, final Integer time) throws InvalidOutputSize {
        final ArrayList<DeviationAtTime> result = new ArrayList<DeviationAtTime>();

        if (trace.containsKey(time) && otherTrace.trace.containsKey(time)) {
            for (Deviation dev: trace.get(time).compareOutputWith(otherTrace.trace.get(time))) {
                result.add(new DeviationAtTime(dev,time));
            }
        }
        return result;

    }

    public Integer getLength() {
        Integer length = 0;

        for (Integer i : trace.keySet()) {
           if (i>length) {
               length = i;
           }
        }

        return length;
    }

    private Integer getFragmentFirstOccurenceInTime(final Event fragment) {

        for (Integer time : trace.keySet()) {
            if (trace.get(time).equals(fragment)) {
                return time;
            }
        }
        return -1;
    }

    private boolean containsFragment(final Event checkFragment) {

        for (Event fragment : trace.values()) {
            if (fragment.equals(checkFragment)) {
                return true;
            }
        }

        return false;

    }

}
