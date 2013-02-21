package org.diagnoser.model.internal.parser;

import org.diagnoser.model.internal.QualitativeValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 1/4/13
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventParser {

    public static final int TIME_PART = 0;
    public static final int INPUT_PART = 1;
    public static final int OUTPUT_PART = 2;
    public static final int NAME_PART = 0;
    public static final int VALUE_PART = 1;

    public static int extractTimeInstant(final String eventAsAString) {
        return Integer.valueOf(eventAsAString.split(";")[TIME_PART]);
    }

    public static Map<String,String> extractInputMap(final String eventAsAString) {
        return createMapFromEventPart(eventAsAString.split(";")[INPUT_PART]);
    }

    public static Map<String,String> extractOutputMap(final String eventAsAString) {
        return createMapFromEventPart(eventAsAString.split(";")[OUTPUT_PART]);
    }

    public static boolean checkLength(final String eventAsAString) {
        return eventAsAString.split(";").length == 3;
    }

    public static boolean checkOutputValues(final String eventAsString) {
       for (String possibleValue :createMapFromEventPart(eventAsString.split(";")[OUTPUT_PART]).values()) {
           try {
               QualitativeValue q = QualitativeValue.valueOf(possibleValue);
           }
           catch(IllegalArgumentException exc) {
               return false;
           }
       }
       return true;
    }

    public static boolean checkNaming(final String eventAsString) {
       return checkEventPart(eventAsString.split(";")[INPUT_PART]) && checkEventPart(eventAsString.split(";")[OUTPUT_PART]);
    }

    private static Map<String,String> createMapFromEventPart(final String eventPart) {
        String[] values = eventPart.split(",");
        HashMap<String,String> result = new HashMap<String,String>();
        for (String value:values) {
            result.put(value.split(":")[NAME_PART],value.split(":")[VALUE_PART]);
        }
        return result;
    }

    private static boolean checkEventPart(final String eventPart) {
        String[] values = eventPart.split(",");
        for (final String value:values) {
            if (value.split(":").length != 2)
            {
                return false;
            }
        }
        return true;
    }

    public static int countOutputSize(String eventPart) {
        return extractOutputMap(eventPart).size();
    }
}