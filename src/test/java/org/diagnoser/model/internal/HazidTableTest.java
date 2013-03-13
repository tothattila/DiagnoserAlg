package org.diagnoser.model.internal;

import org.diagnoser.model.internal.element.HazidElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 3/13/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class HazidTableTest {

    HazidElement[][] testContent = {{createMock(HazidElement.class, "Cau-1"), createMock(HazidElement.class, "Dev-1"), createMock(HazidElement.class, "Imp-1")},
                                  {createMock(HazidElement.class, "Cau-2"), createMock(HazidElement.class, "Dev-2"), createMock(HazidElement.class, "Imp-2")},
                                  {createMock(HazidElement.class, "Cau-3"), createMock(HazidElement.class, "Dev-3"), createMock(HazidElement.class, "Imp-3")}};

    HazidTable table;

    @BeforeMethod
    public void setUp() {
        table = new HazidTable("testId");
    }

    @Test
    public void constructHazidTable() {
        assertEquals(table.getId(), "testId");
    }

    @Test
    public void addAndFetchRows() {
        table.addRow(testContent[0][0], testContent[0][1], testContent[0][2]);
        table.addRow(testContent[1][0], testContent[1][1], testContent[1][2]);
        table.addRow(testContent[2][0], testContent[2][1], testContent[2][2]);

        assertEquals(table.size(),3);

        checkArray(table.getCauseForDeviationAndImplication(testContent[0][1], testContent[0][2]), testContent[0][0]);
        checkArray(table.getCauseForDeviationAndImplication(testContent[1][1], testContent[1][2]), testContent[1][0]);
        checkArray(table.getCauseForDeviationAndImplication(testContent[2][1], testContent[2][2]), testContent[2][0]);
    }

    @Test
    public void haveSomeKindOfStringRepresentationEvenWhenEmpty() {
        assertTrue(table.toString().length() > 0);
    }

    private void checkArray(List<HazidElement> array, HazidElement... elements) {
        if (array.size() != elements.length) {
            fail("element count mismatch. should be " + elements.length + " but is " + array.size());
        }
        else {
            for (HazidElement e : elements) {
               if (!array.contains(e)) {
                  fail("element not contained. name:" +e.toString());
               }
            }
        }

    }

    private HazidElement createMock(Class<HazidElement> hazidElementClass, String name) {
        HazidElement result = mock(hazidElementClass,name);
        when(result.equalsWithOtherHazidElement(eq(result))).thenReturn(true);
        return result;
    }


}
