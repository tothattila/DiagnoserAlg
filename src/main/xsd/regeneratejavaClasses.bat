@ECHO OFF

CALL xjc -p org.diagnoser.model.xml.compact.trace -d ../java "trace.xsd"
CALL xjc -p org.diagnoser.model.xml.compact.hazid -d ../java "hazid.xsd"