package org.diagnoser.model.internal.deviation;

import org.diagnoser.model.internal.Event;
import org.diagnoser.model.internal.HazidElement;
import org.diagnoser.model.internal.KeyWord;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class Deviation implements HazidElement {

    protected KeyWord keyWord;
    protected Event fragment;
    protected int outputIndex;

    public Deviation(final KeyWord deviationType, final Event originalFragment) {
       this.keyWord = deviationType;
       this.fragment = originalFragment;
    }

    public String toString() {
        String buffer = new String();
        buffer += keyWord.toString() + " (" + fragment.toString() + ")";
        return buffer;
    }

    @Override
    public boolean equalsWithOtherHazidElement(HazidElement hazidElement) {
        if (hazidElement instanceof Deviation) {
            if (keyWord.equals(((Deviation)hazidElement).keyWord) && (fragment.equals(((Deviation)hazidElement).fragment)) ) {
                return true;
            }
        }
        return false;
    }

}
