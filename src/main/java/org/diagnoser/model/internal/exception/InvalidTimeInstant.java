package org.diagnoser.model.internal.exception;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 3/3/13
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidTimeInstant extends Exception{
    public InvalidTimeInstant(String error) {
        super(error);
    }
}
