package org.diagnoser.model.internal;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/7/12
 * Time: 1:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class KeyWord {

    public enum KeyWordType {
        EARLIER, LATER, GREATER, SMALLER, NEVERHAPPENED
    }

    public static KeyWord createNeverHappened() {
        return new KeyWord(KeyWordType.NEVERHAPPENED);
    }

    public static KeyWord createEarlier() {
        return new KeyWord(KeyWordType.EARLIER);
    }

    public static KeyWord createLater() {
        return new KeyWord(KeyWordType.LATER);
    }

    public static KeyWord createSmaller(final int outputIndex) {
        return new KeyWord(KeyWordType.SMALLER, outputIndex);
    }

    public static KeyWord createGreater(final int outputIndex) {
       return new KeyWord(KeyWordType.GREATER, outputIndex);
    }

    private KeyWordType type;

    private static final int INVALID_OUTPUT_INDEX = -1;
    private int outputIndex = INVALID_OUTPUT_INDEX;

    private KeyWord(final KeyWordType timelyKeyWordType) {
        type = timelyKeyWordType;
    }

    private KeyWord(final KeyWordType qualitaitveKeyWordType, final int outputIndex) {
        type = qualitaitveKeyWordType;
        this.outputIndex = outputIndex;
    }

    @Override
    public boolean equals(final Object o) {
       if (o instanceof KeyWord) {
          KeyWord k = (KeyWord)o;
           if (isQuantitative()) {
                return (k.type.equals(type) && k.outputIndex == outputIndex);
           }
           else
           {
                return (k.type.equals(type));
           }
       }

       return false;
    }

    @Override
    public int hashCode() {
       int prime = 31;
       int result = 1;
       result = prime*result + type.hashCode();
       result = prime*result + Integer.valueOf(outputIndex).hashCode();
       return result;
    }

    public String toString() {
        String result = type.name();
        if (isQuantitative()) {
            result += "(" + outputIndex + ")";
        }


        return result;
    }

    private boolean isQuantitative() {
        return type == KeyWordType.SMALLER || type==KeyWordType.GREATER;
    }

}

