package org.diagnoser.model.internal.exception;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 2/4/13
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidCommand extends Exception {
    public InvalidCommand(String command) {
        super(command);
    }
}
