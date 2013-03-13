package org.diagnoser.model.internal;

import org.diagnoser.model.internal.exception.AlreadyPresentException;
import org.diagnoser.model.internal.exception.InvalidTimeInstant;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 3/1/13
 * Time: 10:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class TraceTest {

    @Mock
    Event mockEvent;

    @Mock
    Event mockEvent2;

    Trace testTrace;

    @BeforeMethod(alwaysRun=true)
    public void setUpTest() throws Exception {
        MockitoAnnotations.initMocks(this);
        testTrace = new Trace("Name","Hazid");
        testTrace.addFragment(1,mockEvent);
        testTrace.addFragment(2,mockEvent2);
    }

    @Test
    public void createTraceAndTestSimpleOperations() throws Exception {
         assertEquals(testTrace.getAssociatedHazid(), "Hazid");
         assertEquals(testTrace.getName(),"Name");
         assertEquals(testTrace.getLength(),new Integer(2));
    }

    @Test
    public void addElementToSameTimeInstant() {
        try {
            testTrace.addFragment(1,mockEvent);
            fail("Time instant exception hadn't been thrown.");
        }
        catch(AlreadyPresentException e) {

        }
        catch(Exception e) {
            fail("Invalid exception thrown " + e.getMessage());
        }

    }

    @Test
    public void timeInstantMustBeANaturalNumber() {
        try {
            testTrace.addFragment(-1,mockEvent);
            fail("Time instant exception hadn't been thrown.");
        }
        catch(InvalidTimeInstant e) {

        }
        catch(Exception e) {
            fail("Invalid exception thrown " + e.getMessage());
        }
    }

    @Test
    public void returnStringRepresentation() {
       assertFalse(testTrace.toString().isEmpty());
    }

    @Test
    public void collectAllDeviationsInSimpleCase() {

        Trace trace1 = new Trace("Trace-1","TH1");
        Trace trace2 = new Trace("Trace-2","TH2");

        // TO BE COMPLETED!!!

    }



}
