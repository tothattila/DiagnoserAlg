package org.diagnoser.model.internal.element;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class RootCause implements HazidElement {

    private final String component;

    public RootCause(final String componentFailure) {

        this.component = componentFailure;

    }

    public String getCause() {
        return component;
    }

    public String toString() { return component; }

    @Override
    public boolean equalsWithOtherHazidElement(HazidElement hazidElement) {
        if (hazidElement instanceof RootCause) {
            if (component.equals(((RootCause)hazidElement).component)) {
                return true;
            }
        }
        return false;
    }

}
