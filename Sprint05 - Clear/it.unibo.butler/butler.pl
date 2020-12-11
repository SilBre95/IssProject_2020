%====================================================================================
% butler description   
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8038").
 qactor( robot, ctxrobot, "it.unibo.robot.Robot").
  qactor( robotexecutor, ctxrobot, "it.unibo.robotexecutor.Robotexecutor").
  qactor( robotmover, ctxrobot, "it.unibo.robotmover.Robotmover").
  qactor( fridge, ctxrobot, "it.unibo.fridge.Fridge").
  qactor( maitre, ctxrobot, "it.unibo.maitre.Maitre").
  qactor( user, ctxrobot, "it.unibo.user.User").
