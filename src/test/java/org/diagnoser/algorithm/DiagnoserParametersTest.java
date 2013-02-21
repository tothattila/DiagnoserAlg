package org.diagnoser.algorithm;

import org.diagnoser.algorithm.exception.InvalidDiagnoserParameter;
import org.testng.annotations.Test;

import java.security.InvalidParameterException;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 2/21/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class DiagnoserParametersTest {

    @Test
    public void parseSimpleParameterList() throws InvalidDiagnoserParameter {
        DiagnoserParameters testParamProcessor = DiagnoserParameters.getInstance(new String[] {".\\maintOpProc_tankleak.xml","verbose","diagnose","xyz"});
        assertTrue(testParamProcessor.isVerbose());
        assertEquals(testParamProcessor.getHazidDir(),".\\hazid");
        assertEquals(testParamProcessor.getTraceDir(),".\\trace");
        assertEquals(testParamProcessor.getAnalysableTrace(),".\\maintOpProc_tankleak.xml");
        assertEquals(testParamProcessor.getStartNominalTrace(),"xyz");
        assertTrue(testParamProcessor.isDiagnose());
    }

    @Test
    public void throwExceptionOnNotEnoughParameters() {
        try{
            DiagnoserParameters.getInstance(new String[] {});
            fail();
        } catch(InvalidDiagnoserParameter exc) {

        }
    }

    @Test
    public void throwExceptionOnNotProvidedDiagnosableTrace() {
        try{
            DiagnoserParameters.getInstance(new String[] {".\\maintOpProc_tankleak.xml","verbose","diagnose"});
            fail();
        } catch(InvalidDiagnoserParameter exc) {

        }
    }
}
