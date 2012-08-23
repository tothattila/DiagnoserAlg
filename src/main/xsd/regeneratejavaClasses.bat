@ECHO OFF

CALL xjc -p org.diagnoser.model.xml.extended.trace -d ../java "extended trace.xsd"
CALL xjc -p org.diagnoser.model.xml.compact.trace -d ../java "compact trace.xsd"
CALL xjc -p org.diagnoser.model.xml.extended.hazid -d ../java "extended hazid.xsd"
CALL xjc -p org.diagnoser.model.xml.compact.hazid -d ../java "compact hazid.xsd"