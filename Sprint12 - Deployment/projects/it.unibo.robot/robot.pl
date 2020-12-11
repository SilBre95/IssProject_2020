%====================================================================================
% robot description   
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8040").
context(ctxfridge, "localhost",  "TCP", "8038").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
 qactor( fridge, ctxfridge, "external").
  qactor( basicrobot, ctxbasicrobot, "external").
  qactor( robot, ctxrobot, "it.unibo.robot.Robot").
  qactor( robotexecutor, ctxrobot, "it.unibo.robotexecutor.Robotexecutor").
  qactor( robotmover, ctxrobot, "it.unibo.robotmover.Robotmover").
  qactor( roomstate, ctxrobot, "it.unibo.roomstate.Roomstate").
