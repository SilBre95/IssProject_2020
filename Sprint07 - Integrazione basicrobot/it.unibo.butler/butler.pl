%====================================================================================
% butler description   
%====================================================================================
context(ctxrobot, "127.0.0.1",  "TCP", "8038").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( robot, ctxrobot, "it.unibo.robot.Robot").
  qactor( robotexecutor, ctxrobot, "it.unibo.robotexecutor.Robotexecutor").
  qactor( robotmover, ctxrobot, "it.unibo.robotmover.Robotmover").
  qactor( roomstate, ctxrobot, "it.unibo.roomstate.Roomstate").
  qactor( fridge, ctxrobot, "it.unibo.fridge.Fridge").
  qactor( maitre, ctxrobot, "it.unibo.maitre.Maitre").
  qactor( user, ctxrobot, "it.unibo.user.User").
