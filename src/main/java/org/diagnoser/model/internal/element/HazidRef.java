package org.diagnoser.model.internal.element;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 2/4/13
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class HazidRef implements HazidElement {

    private final String targetHazidTableId;
    private final String targetRowLabel;

    public HazidRef(final String targetHazidTableId, final String targetRowLabel) {
        this.targetHazidTableId = targetHazidTableId;
        this.targetRowLabel = targetRowLabel;
    }

    public String getTargetHazidTableId() {
        return targetHazidTableId;
    }

    public String getTargetRowLabel() {
        return targetRowLabel;
    }

    @Override
    public boolean equalsWithOtherHazidElement(HazidElement hazidElement) {

        if (hazidElement instanceof HazidRef) {
            HazidRef otherRef = (HazidRef)hazidElement;

            if (otherRef.getTargetHazidTableId().equals(targetHazidTableId) && otherRef.getTargetRowLabel().equals(targetRowLabel)) {
                return true;
            }
        }
        return false;
    }

}
