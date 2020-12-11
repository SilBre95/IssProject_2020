%====================================================================================
% maitre description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/qak/events").
context(ctxmaitre, "localhost",  "COAP", "8039").
context(ctxrobot, "127.0.0.1",  "COAP", "8040").
 qactor( robot, ctxrobot, "external").
  qactor( maitre, ctxmaitre, "it.unibo.maitre.Maitre").
  qactor( console, ctxmaitre, "it.unibo.console.Console").
