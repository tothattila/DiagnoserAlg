package org.diagnoser.model.internal;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class Deviation implements HazidElement {

    protected Keyword keyWord;
    protected TraceFragment fragment;

    // TODO
    protected int outputIndex;

    public Deviation(final Keyword deviationType, final TraceFragment originalFragment) {
       this.keyWord = deviationType;
       this.fragment = originalFragment;
    }

    public String toString() {
        String buffer = new String();
        buffer += keyWord.name() + " (" + fragment.toString() + ")";
        return buffer;
    }

    @Override
    public boolean equals(HazidElement hazidElement) {
        if (hazidElement instanceof Deviation) {
            if (keyWord.equals(((Deviation)hazidElement).keyWord) && (fragment.equals(((Deviation)hazidElement).fragment)) ) {
                return true;
            }
        }
        return false;
    }

}
