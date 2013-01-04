package org.diagnoser.model.internal;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 1/4/13
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventParserTest {

    EventParser ef;

    @BeforeMethod(alwaysRun =true)
    public void setUp() {
       ef = new EventParser();
    }

    @Test
    public void parseTimeInstantCorectly() {
       assertEquals(1, EventParser.extractTimeInstant("1;INP1:1,INP2:0;V1OUT:L,V2OUT:N,V3OUT:NO"));
    }

    @Test
    public void parseInputsCorrectly() {
       Map<String,String> inputs = EventParser.extractInputMap("1;INP1:1,INP2:0;V1OUT:L,V2OUT:N,V3OUT:NO");
       assertEquals(2,inputs.size());
       assertEquals("1",inputs.get("INP1"));
       assertEquals("0",inputs.get("INP2"));
    }

    @Test
    public void parseOutputsCorrectly() {
        Map<String,String> outputs = EventParser.extractOutputMap("1;INP1:1,INP2:0;V1OUT:L,V2OUT:N,V3OUT:NO");
        System.out.println(outputs.toString());
        assertEquals(3,outputs.size());
        assertEquals("L",outputs.get("V1OUT"));
        assertEquals("N",outputs.get("V2OUT"));
        assertEquals("NO",outputs.get("V3OUT"));
    }

    @Test
    public void determineIfTraceIsCorrect() {
       assertTrue(EventParser.checkLength("1;INP1:1,INP2:0;V1OUT:L,V2OUT:N,V3OUT:NO"));
    }

    @Test
    public void determineIfTraceHasTooFewMainParts() {
       assertFalse(EventParser.checkLength("1;INP1:1,INP2:0"));
    }

    @Test
    public void determineCorrectQualitativeValue() {
       assertFalse(EventParser.checkOutputValues("1;INP1:1;V1OUT:L,V2OUT:NO"));
    }

    @Test
    public void determineFaultyQualitativeValue() {
       assertFalse(EventParser.checkOutputValues("1;INP1:1;V1OUT:L,V2OUT:FAULTY"));
    }

    @Test
    public void detectNameLessInput() {
        assertFalse(EventParser.checkNaming("1;1;V1OUT:L,V2OUT:NO"));
    }

    @Test
    public void detectNameLessOutput() {
        assertFalse(EventParser.checkNaming("1;INP1:1;V1OUT:L,NO"));
    }


}
