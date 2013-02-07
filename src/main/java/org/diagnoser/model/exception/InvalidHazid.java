package org.diagnoser.model.exception;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 2/7/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidHazid extends Exception {

    private String problem;
    private String row;

    public InvalidHazid(final String problem, final String row) {
       this.problem = problem;
       this.row = row;
    }

    public String toString() {
        return problem + " " + row;
    }

}
