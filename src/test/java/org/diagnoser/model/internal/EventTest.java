package org.diagnoser.model.internal;

import org.diagnoser.model.internal.Event;
import org.diagnoser.model.internal.exception.InvalidOutputSize;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.reporters.jq.TestPanel;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 3/1/13
 * Time: 5:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventTest {

    Event testEvent;

    Map<String,String> fakeInputMap;
    Map<String,String> fakeOutputMap;

    @BeforeMethod
    public void create() {
        testEvent = new Event(fakeInputMap, fakeOutputMap);
        fakeInputMap=createMap(new String[]{"VA","VB"},new String[]{"1","2"});
        fakeOutputMap=createMap(new String[]{"OUT-1","OUT-2"}, new String[]{"LOW","NRM"});
    }

    @Test
    public void returnGivenMaps() {
        assertEquals(testEvent.getInputs(),fakeInputMap);
        assertEquals(testEvent.getOutputs(), fakeOutputMap);
    }

    @Test
    public void equalToOtherEvent() {
       Event event1=new Event(fakeInputMap,fakeOutputMap);
       Event event2 = new Event(copy(fakeInputMap),copy(fakeOutputMap) );
       assertTrue(event1.equals(event2));
    }

    @Test
    public void compareInputs() {
       Event event1=new Event(fakeInputMap,new HashMap<String,String>());
       Event event2=new Event(copy(fakeInputMap),fakeOutputMap);

        assertTrue(event1.compareInputWith(event2));
    }

    @Test
    public void returnStringRepresentation() {
       assertFalse(testEvent.toString().isEmpty());
    }

    @Test
    public void compareOutputs() throws Exception {
        Event event1=new Event(new HashMap<String,String>(),fakeOutputMap);
        Event event2=new Event(copy(fakeInputMap),fakeOutputMap);

        assertTrue(event1.compareOutputWith(event2).isEmpty());
    }

    @Test
    public void compareInputsWhenInputNamesAreDifferent() {
        Event event1=new Event(createMap(new String[]{"I-1","I-2"}, new String[]{"x","y"}),createMap(new String[]{},new String[]{}));
        Event event2=new Event(createMap(new String[]{"DIFFERENCE","I-2"}, new String[]{"x","y"}),createMap(new String[]{},new String[]{}));

        assertTrue(event1.compareInputWith(event2));
    }

    @Test
    public void compareDifferentlySizedInputs() {
        Event event1=new Event(createMap(new String[]{"I-1","I-2","I-3"}, new String[]{"NO","LOW","NRM"}),createMap(new String[]{},new String[]{}));
        Event event2=new Event(createMap(new String[]{"DIFFERENCE","I-2"}, new String[]{"NO","LOW"}),createMap(new String[]{},new String[]{}));

        assertTrue(event1.compareInputWith(event2));
    }

    @Test
    public void compareOutputsWhenOutputNamesAreDifferent() throws InvalidOutputSize {
        Event event1=new Event(createMap(new String[]{},new String[]{}),createMap(new String[]{"O-1","O-2"}, new String[]{"NO","LOW"}));
        Event event2=new Event(createMap(new String[]{},new String[]{}), createMap(new String[]{"DIFFERENCE","O-2"}, new String[]{"NO","LOW"}));

        assertTrue(event1.compareOutputWith(event2).isEmpty());
    }

    @Test
    public void compareDifferentlySizedOutputs() throws InvalidOutputSize {
        Event event1=new Event(createMap(new String[]{},new String[]{}),createMap(new String[]{"O-1","O-2","O-3"}, new String[]{"NO","LOW","NRM"}));
        Event event2=new Event(createMap(new String[]{},new String[]{}), createMap(new String[]{"DIFFERENCE","O-2"}, new String[]{"NO","LOW"}));

        assertTrue(event1.compareOutputWith(event2).isEmpty());
    }

    @Test
    public void exactEqualityCheck() {

    }

    private Map<String, String> copy(Map<String, String> map) {
        return new HashMap<String,String>(map);
    }

    private Map<String,String> createMap(String[] keys, String[] values) {
       Map<String,String> result = new HashMap<String,String>();
       for (int i=0;i<keys.length;i++) {
          result.put(keys[i],values[i]);
       }
       return result;

    }

}
