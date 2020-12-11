%====================================================================================
% robot description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/qak/events").
context(ctxbasicrobot, "192.168.137.16",  "COAP", "8020").
context(ctxrobot, "localhost",  "COAP", "8040").
context(ctxfridge, "127.0.0.1",  "COAP", "8038").
 qactor( fridge, ctxfridge, "external").
  qactor( basicrobot, ctxbasicrobot, "external").
  qactor( robot, ctxrobot, "it.unibo.robot.Robot").
  qactor( robotexecutor, ctxrobot, "it.unibo.robotexecutor.Robotexecutor").
  qactor( robotmover, ctxrobot, "it.unibo.robotmover.Robotmover").
  qactor( roomstate, ctxrobot, "it.unibo.roomstate.Roomstate").
