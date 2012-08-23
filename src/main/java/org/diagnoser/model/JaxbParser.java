package org.diagnoser.model;

import org.diagnoser.model.exception.InvalidFormatException;
import org.diagnoser.model.exception.UnsupportedHazidType;
import org.diagnoser.model.internal.*;
import org.diagnoser.model.internal.exception.InvalidOutput;
import org.diagnoser.model.internal.exception.AlreadyPresentException;
import org.diagnoser.model.internal.exception.CorruptTraceException;
import org.diagnoser.model.internal.exception.InvalidTraceFragment;
import org.diagnoser.model.xml.compact.hazid.Procedurehazidtable;
import org.diagnoser.model.xml.compact.hazid.Tablerow;

import javax.swing.text.TableView;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: eatttth
 * Date: 8/3/12
 * Time: 11:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class JaxbParser {

    private static final int TIME_PART = 0;
    private static final int INPUT_PART = 1;
    private static final int OUTPUT_PART = 2;

    public HazidTable parseHazidXml(final String xmlFile) throws JAXBException, UnsupportedHazidType, InvalidFormatException, InvalidTraceFragment {
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
        Trace retTrace = new Trace(xmlTrace.getName());

        for (String fragment:xmlTrace.getEvent()) {

            String[] traceParts = fragment.split(";");

            if (traceParts.length != 3) {
                throw new InvalidTraceFragment("Invalid trace fragment in XML `" + xmlFile +"`" + " and the fragment is '" + fragment + "'" );
            }

            if (Integer.valueOf(traceParts[TIME_PART]) != currentTime) {
                throw new CorruptTraceException("Time flow is not continuous in trace at event line `"+ fragment +"` in file `"+ xmlFile+ "`");
            }

            if (!checkOutputAgainstQualitativeRangeSpace(traceParts[OUTPUT_PART])) {
                throw new InvalidOutput("Output value not of qualitative range space at event `"+fragment+"` in file `"+xmlFile+"`");
            }

            retTrace.addFragment(Integer.valueOf(currentTime), new TraceFragment(traceParts[INPUT_PART], traceParts[OUTPUT_PART]));

            currentTime++;
        }


        return retTrace;
    }

    private HazidTable createHazidTableFromCompactHazid(final String xmFile, final Procedurehazidtable hazid) throws UnsupportedHazidType, InvalidFormatException, InvalidTraceFragment {

        final HazidTable table = new HazidTable();

        for (Tablerow row: hazid.getTablerow()) {
           final HazidElement cause = createElementFromTypeAndParam(row.getCause().getType(),row.getCause().getParam());
           final HazidElement deviation = createElementFromTypeAndParam(row.getDeviation().getType(),row.getDeviation().getParam());
           final HazidElement implication = createElementFromTypeAndParam(row.getImplication().getType(),row.getImplication().getParam());

           table.addRow(cause, deviation, implication);
        }

        return table;

    }

    private HazidElement createElementFromTypeAndParam(String type, String param) throws UnsupportedHazidType, InvalidFormatException, InvalidTraceFragment {

        HazidElement result;

        try {
            if (type.equals("rootcause")) {
                result = new RootCause(param);
            } else if (type.equals("never-happened")) {
                result = new DeviationAtTime(Keyword.NEVERHAPPENED, checkParam(param));
            } else if (type.equals("earlier")) {
                result = new DeviationAtTime(Keyword.EARLIER, checkParam(param));
            } else if (type.equals("later")) {
                result = new DeviationAtTime(Keyword.LATER, checkParam(param));
            } else if (type.equals("greater")) {
                result = new DeviationAtTime(Keyword.GREATER, checkParam(param));
            }  else if (type.equals("smaller")) {
                result = new DeviationAtTime(Keyword.SMALLER, checkParam(param));
            }  else if (type.equals("notavailable")) {
                result = new NotAvailable();
            }  else {
                throw new UnsupportedHazidType("Type `" + type + "` not supported.");
            }
            }
        catch (ArrayIndexOutOfBoundsException exc) {
            throw new InvalidFormatException("Invalid param format found in HAZID XML. Type: `"+type+"` Param: `"+param+"`" );
        }

        return result;
    }

    private String checkParam(String param) throws InvalidTraceFragment {
        if (param.split(";").length != 3) {
            throw new InvalidTraceFragment("Not correct trace fragment :" + param);
        }

        return param;
    }

    private boolean checkOutputAgainstQualitativeRangeSpace(String output) {

        final String[] parts = output.split(",");

        try {
            for (String part:parts) {
               QualitativeValue.valueOf(part);

            }
        }
        catch (IllegalArgumentException exc) {
            return false;
        }

        return true;
    }





}
