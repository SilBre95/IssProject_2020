%====================================================================================
% fridge description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/qak/events").
context(ctxfridge, "localhost",  "COAP", "8038").
 qactor( fridge, ctxfridge, "it.unibo.fridge.Fridge").
