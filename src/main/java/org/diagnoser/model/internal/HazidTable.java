package org.diagnoser.model.internal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class HazidTable {

    ArrayList<HazidElement[]> hazidTable;
    String identifier;
    final int COLUMN_SIZE = 30;

    public HazidTable(final String identifier) {
       hazidTable = new ArrayList<HazidElement[]>();
       this.identifier = identifier;
    }

    public String getId() {
        return identifier;
    }

    public void addRow(final HazidElement cause, final HazidElement deviation, final HazidElement implication) {
        hazidTable.add(new HazidElement[] {cause, deviation, implication});
    }

    public List<HazidElement> getCauseForDeviationAndImplication(final HazidElement deviation, final HazidElement implication) {
       List<HazidElement> result = new ArrayList<HazidElement>();

       for(HazidElement[] row: hazidTable) {
           if ((deviation.equalsWithOtherHazidElement(row[1])) && (implication.equalsWithOtherHazidElement(row[2]))) {
               result.add(row[0]);
           }

       }

       return result;
    }

    public int size() {
        return hazidTable.size();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("\n" + extendStringToLength("CAUSE")+extendStringToLength("DEVIATION")+extendStringToLength("IMPLICATION") + "\n\n");
        for (HazidElement[] row : hazidTable) {
            builder.append(extendStringToLength(row[0].toString()) + extendStringToLength(row[1].toString()) + extendStringToLength(row[2].toString()) + "\n");
        }

        return builder.toString();
    }

    private String extendStringToLength(String s) {
       String result = s;
       while(result.length()<COLUMN_SIZE) {
          result=result+" ";
       }
       return result;
    }
}
