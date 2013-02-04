package org.diagnoser.model.internal;

import junit.framework.Assert;
import org.diagnoser.model.internal.deviation.DeviationAtTime;
import org.diagnoser.model.internal.exception.InvalidCommand;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.security.InvalidParameterException;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 2/4/13
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class CellParserTest {

    CellParser testParser;

    @BeforeMethod(alwaysRun= true)
    public void setUp() {
        testParser = new CellParser();
    }

    @Test
    public void parseRootCause() throws InvalidCommand {
       HazidElement result = testParser.parse("ROOTCAUSE;VALVE-RUPTURE");
       assertTrue(result instanceof RootCause);
       assertEquals(((RootCause)result).getCause(),"VALVE-RUPTURE");
    }

    @Test
    public void parseEarlierDeviationWithInputsAndOutputs() throws InvalidCommand {
       HazidElement result = testParser.parse("EARLIER;3;INP-1:1,INP-2:2;OUT:N,DST:L");
       assertTrue(result instanceof DeviationAtTime);
       DeviationAtTime deviation = (DeviationAtTime)result;
       assertEquals(deviation.getDeviationType(), KeyWord.createEarlier());
       assertEquals(deviation.getTime(), new Integer(3));
       Map<String,String> inputMap = deviation.getInputs();
       Map<String,String> outputMap = deviation.getOutputs();

       assertEquals(inputMap.get("INP-1"),"1");
       assertEquals(inputMap.get("INP-2"),"2");
       assertEquals(outputMap.get("OUT"), "N");
       assertEquals(outputMap.get("DST"),"L");
    }

    @Test
    public void parseLaterDeviationWithInputsAndOutputs() throws InvalidCommand {
        HazidElement result = testParser.parse("LATER;3;INP-3:1,INP-4:2;L-OUT:N,L-DST:L");
        assertTrue(result instanceof DeviationAtTime);
        DeviationAtTime deviation = (DeviationAtTime)result;
        assertEquals(deviation.getTime(),new Integer(3));
        assertEquals(deviation.getDeviationType(), KeyWord.createLater());
        Map<String,String> inputMap = deviation.getInputs();
        Map<String,String> outputMap = deviation.getOutputs();

        assertEquals(inputMap.get("INP-3"),"1");
        assertEquals(inputMap.get("INP-4"),"2");
        assertEquals(outputMap.get("L-OUT"), "N");
        assertEquals(outputMap.get("L-DST"),"L");
    }

    @Test
    public void parseGreaterDeviationWithInputsAndSingleOutput() throws InvalidCommand {
        HazidElement result = testParser.parse("GREATER;3;INP:1,I-2:2;GRE:N");
        assertTrue(result instanceof DeviationAtTime);
        DeviationAtTime deviation = (DeviationAtTime)result;
        assertEquals(deviation.getDeviationType(), KeyWord.createGreater("GRE"));
        assertEquals(deviation.getTime(),new Integer(3));
        Map<String,String> inputMap = deviation.getInputs();
        Map<String,String> outputMap = deviation.getOutputs();

        assertEquals(inputMap.get("INP"),"1");
        assertEquals(inputMap.get("I-2"),"2");
        assertEquals(outputMap.get("GRE"), "N");

    }

    @Test
    public void parseSmallerDeviationWithInputsAndSingleOutput() throws InvalidCommand {
        HazidElement result = testParser.parse("SMALLER;3;INP:1,I-2:2;SMA:N");
        assertTrue(result instanceof DeviationAtTime);
        DeviationAtTime deviation = (DeviationAtTime)result;
        assertEquals(deviation.getDeviationType(), KeyWord.createSmaller("SMA"));
        assertEquals(deviation.getTime(),new Integer(3));
        Map<String,String> inputMap = deviation.getInputs();
        Map<String,String> outputMap = deviation.getOutputs();

        assertEquals(inputMap.get("INP"),"1");
        assertEquals(inputMap.get("I-2"),"2");
        assertEquals(outputMap.get("SMA"), "N");
    }

    @Test
    public void parseNeverHappenedDeviationWithInputsAndOutputs() throws InvalidCommand {
        HazidElement result = testParser.parse("NEVERHAPPENED;3;INP-NH:1,INP-NH2:2;O:N,D:L");
        assertTrue(result instanceof DeviationAtTime);
        DeviationAtTime deviation = (DeviationAtTime)result;
        assertEquals(deviation.getDeviationType(), KeyWord.createNeverHappened());
        assertEquals(deviation.getTime(),new Integer(3));
        Map<String,String> inputMap = deviation.getInputs();
        Map<String,String> outputMap = deviation.getOutputs();

        assertEquals(inputMap.get("INP-NH"),"1");
        assertEquals(inputMap.get("INP-NH2"),"2");
        assertEquals(outputMap.get("O"), "N");
        assertEquals(outputMap.get("D"),"L");
    }

    @Test
    public void parseReferenceCell() throws InvalidCommand {
       HazidElement result = testParser.parse("REF;NEXTHAZIDTABLE;SPECIFIC-ROW-3");
       assertTrue(result instanceof HazidRef);
       assertEquals(((HazidRef) result).getTargetHazidTableId(), "NEXTHAZIDTABLE");
       assertEquals(((HazidRef)result).getTargetRowLabel(),"SPECIFIC-ROW-3");
    }

    @Test
    public void invalidCommand() {
        try {
           testParser.parse("INVALID-COMMAND;PARAMETER");
           fail();
        }
        catch(InvalidCommand exc) {

        }
    }

}
