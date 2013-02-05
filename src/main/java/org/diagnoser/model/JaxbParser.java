package org.diagnoser.model;

import org.diagnoser.model.exception.InvalidFormatException;
import org.diagnoser.model.exception.UnsupportedHazidType;
import org.diagnoser.model.internal.*;
import org.diagnoser.model.internal.deviation.DeviationAtTime;
import org.diagnoser.model.internal.exception.*;
import org.diagnoser.model.xml.compact.hazid.Cell;
import org.diagnoser.model.xml.compact.hazid.Procedurehazidtable;
import org.diagnoser.model.xml.compact.hazid.Row;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/3/12
 * Time: 11:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class JaxbParser {

    public HazidTable parseHazidXml(final String xmlFile) throws JAXBException, InvalidCommand {
        final JAXBContext context = JAXBContext
                .newInstance("org.diagnoser.model.xml.compact.hazid");
        final Unmarshaller unMarshaller = context.createUnmarshaller();
        return createHazidTableFromCompactHazid(xmlFile, (Procedurehazidtable) unMarshaller.unmarshal(new File(xmlFile)));

    }

    public Trace parseTraceXml(final String xmlFile) throws JAXBException, CorruptTraceException, AlreadyPresentException, InvalidOutput, InvalidTraceFragment {
        final JAXBContext context = JAXBContext.newInstance("org.diagnoser.model.xml.compact.trace");
        final Unmarshaller unMarshaller = context.createUnmarshaller();

        org.diagnoser.model.xml.compact.trace.Trace xmlTrace = (org.diagnoser.model.xml.compact.trace.Trace) unMarshaller.unmarshal(new File(xmlFile));
        return createInternalTraceFromCompactXmlTrace(xmlFile, xmlTrace);
    }

    private Trace createInternalTraceFromCompactXmlTrace(String xmlFile, org.diagnoser.model.xml.compact.trace.Trace xmlTrace) throws CorruptTraceException, AlreadyPresentException, InvalidOutput, InvalidTraceFragment {
        int currentTime = 1;
        Trace retTrace = new Trace(xmlTrace.getId());

        for (String fragment:xmlTrace.getEvent()) {

            if (!EventParser.checkLength(fragment)) {
                throw new InvalidTraceFragment("Invalid trace fragment in XML `" + xmlFile +"`" + " and the fragment is '" + fragment + "'" );
            }

            if (EventParser.extractTimeInstant(fragment) != currentTime) {
                throw new CorruptTraceException("Time flow is not continuous in trace at event line `"+ fragment +"` in file `"+ xmlFile+ "`");
            }

            if (!EventParser.checkOutputValues(fragment)) {
                throw new InvalidOutput("Output value not of qualitative range space at event `"+fragment+"` in file `"+xmlFile+"`");
            }

            if (!EventParser.checkNaming(fragment)) {
                throw new InvalidTraceFragment("Either an input or an output was not named (<name>:<value> syntax) in `"+fragment+"` in file `"+xmlFile+"`");
            }

            retTrace.addFragment(EventParser.extractTimeInstant(fragment), new Event(EventParser.extractInputMap(fragment), EventParser.extractOutputMap(fragment)));

            currentTime++;
        }


        return retTrace;
    }

    private HazidTable createHazidTableFromCompactHazid(final String xmFile, final Procedurehazidtable hazid) throws InvalidCommand {

        final HazidTable table = new HazidTable();

        for (Row row: hazid.getRow()) {
           final HazidElement cause = CellParser.parse(row.getCause());
           final HazidElement deviation = CellParser.parse(row.getDeviation());
           final HazidElement implication = CellParser.parse(row.getImplication());

           table.addRow(cause, deviation, implication);
        }

        return table;

    }


    private String checkParam(String param) throws InvalidTraceFragment {
        if (param.split(";").length != 3) {
            throw new InvalidTraceFragment("Not correct trace fragment `" + param + "`");
        }
        else if (!EventParser.checkNaming(param))  {
            throw new InvalidTraceFragment("Please name inputs and outputs (<name>:<value) in param `" + param + "`");
        } else if (!EventParser.checkOutputValues(param)) {
            throw new InvalidTraceFragment("Please use valid qualitative values for outputs in param `" + param + "`");
        }

        return param;
    }

}
