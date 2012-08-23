@ECHO OFF

CALL java -jar .\diagnoser-algorithm-impl.jar ".\normalOpProc_hazid.xml" ".\normalOpProc_nominal.xml" ".\normalOpProc_tankleak.xml"
CALL java -jar .\diagnoser-algorithm-impl.jar ".\normalOpProc_hazid.xml" ".\normalOpProc_nominal.xml" ".\normalOpProc_posli.xml"
CALL java -jar .\diagnoser-algorithm-impl.jar ".\normalOpProc_hazid.xml" ".\normalOpProc_nominal.xml" ".\normalOpProc_negli.xml"
CALL java -jar .\diagnoser-algorithm-impl.jar ".\normalOpProc_hazid.xml" ".\normalOpProc_nominal.xml" ".\normalOpProc_va1con.xml"
CALL java -jar .\diagnoser-algorithm-impl.jar ".\normalOpProc_hazid.xml" ".\normalOpProc_nominal.xml" ".\normalOpProc_va2con.xml"

CALL java -jar .\diagnoser-algorithm-impl.jar ".\maintOpProc_hazid.xml" ".\maintOpProc_nominal.xml" ".\maintOpProc_tankleak.xml"
CALL java -jar .\diagnoser-algorithm-impl.jar ".\maintOpProc_hazid.xml" ".\maintOpProc_nominal.xml" ".\maintOpProc_posli.xml"
CALL java -jar .\diagnoser-algorithm-impl.jar ".\maintOpProc_hazid.xml" ".\maintOpProc_nominal.xml" ".\maintOpProc_negli.xml"
CALL java -jar .\diagnoser-algorithm-impl.jar ".\maintOpProc_hazid.xml" ".\maintOpProc_nominal.xml" ".\maintOpProc_va1con.xml"
CALL java -jar .\diagnoser-algorithm-impl.jar ".\maintOpProc_hazid.xml" ".\maintOpProc_nominal.xml" ".\maintOpProc_va2con.xml"

CALL java -jar .\diagnoser-algorithm-impl.jar ".\tankFillOp_hazid.xml" ".\tankFillOp_nominal.xml" ".\tankFillOp_tankleak.xml"
CALL java -jar .\diagnoser-algorithm-impl.jar ".\tankFillOp_hazid.xml" ".\tankFillOp_nominal.xml" ".\tankFillOp_posli.xml"
CALL java -jar .\diagnoser-algorithm-impl.jar ".\tankFillOp_hazid.xml" ".\tankFillOp_nominal.xml" ".\tankFillOp_negli.xml"
CALL java -jar .\diagnoser-algorithm-impl.jar ".\tankFillOp_hazid.xml" ".\tankFillOp_nominal.xml" ".\tankFillOp_va1con.xml"

