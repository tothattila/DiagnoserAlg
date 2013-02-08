package org.diagnoser.model;

import org.diagnoser.algorithm.LogPrinter;
import org.diagnoser.model.exception.InvalidHazid;
import org.diagnoser.model.internal.*;
import org.diagnoser.model.internal.exception.*;
import org.diagnoser.model.xml.compact.DirectoryLister;
import org.diagnoser.model.xml.compact.hazid.Procedurehazidtable;
import org.diagnoser.model.xml.compact.hazid.Row;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/3/12
 * Time: 11:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class JaxbParser {

    public Map<String,HazidTable> parseDir(final String dir) throws JAXBException, InvalidHazid {

        List<String> xmlFiles = new DirectoryLister().listAllXmlsInDirectory(dir);
        LogPrinter.printCaption("Beginning to parse `" + xmlFiles.size() + "` number of files from given directory `" + dir + "`");
        Map<String,HazidTable> result = new HashMap<String, HazidTable>();

        for (String s:xmlFiles) {
            LogPrinter.printMessage("Parsing file:"+s+"...");
            HazidTable simpleTable = parseHazidXml(s);
            String newId = returnOrGenerateUniqueId(simpleTable.getId(), result.size());
            result.put(newId, simpleTable);
            LogPrinter.printMessage("...HAZID table " + newId + " loaded");


        }

        return result;
    }

    private String returnOrGenerateUniqueId(String id, int size) {
        return (id==null?"HAZID-"+size : id);
    }

    public HazidTable parseHazidXml(final String xmlFile) throws JAXBException, InvalidHazid {
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

    private HazidTable createHazidTableFromCompactHazid(final String xmlFile, final Procedurehazidtable hazid) throws InvalidHazid {

        final HazidTable table = new HazidTable(hazid.getId());

        for (Row row: hazid.getRow()) {
           try {
              final HazidElement cause = CellParser.parse(row.getCause());
              final HazidElement deviation = CellParser.parse(row.getDeviation());
              final HazidElement implication = CellParser.parse(row.getImplication());
              table.addRow(cause, deviation, implication);
           }
           catch (InvalidCommand exc) {
               throw new InvalidHazid("Invalid command found in XML file: `"+xmlFile+ "`: "+ exc.toString(), "\nCause:\n"+row.getCause() +"\nDeviation:\n"+row.getDeviation() + "\nImplication:\n"+row.getImplication());
           }

        }

        return table;

    }

}
