%====================================================================================
% butler description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/qak/events").
context(ctxrobot, "localhost",  "TCP", "8038").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( robot, ctxrobot, "it.unibo.robot.Robot").
  qactor( robotexecutor, ctxrobot, "it.unibo.robotexecutor.Robotexecutor").
  qactor( robotmover, ctxrobot, "it.unibo.robotmover.Robotmover").
  qactor( roomstate, ctxrobot, "it.unibo.roomstate.Roomstate").
  qactor( fridge, ctxrobot, "it.unibo.fridge.Fridge").
  qactor( maitre, ctxrobot, "it.unibo.maitre.Maitre").
  qactor( console, ctxrobot, "it.unibo.console.Console").
