package org.diagnoser.model.internal;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotAvailable implements HazidElement {

    public String toString() { return "Not available"; }

    @Override
    public boolean equalsWithOtherHazidElement(HazidElement hazidElement) {
        if (hazidElement instanceof NotAvailable) {
            return true;
        }
        return false;
    }
}
