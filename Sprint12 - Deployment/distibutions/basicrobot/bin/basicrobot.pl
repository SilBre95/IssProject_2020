%====================================================================================
% basicrobot description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/qak/events").
context(ctxbasicrobot, "localhost",  "COAP", "8020").
 qactor( datacleaner, ctxbasicrobot, "rx.dataCleaner").
  qactor( distancefilter, ctxbasicrobot, "rx.distanceFilter").
  qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
msglogging.
