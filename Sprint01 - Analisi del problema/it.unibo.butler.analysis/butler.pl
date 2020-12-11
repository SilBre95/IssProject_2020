%====================================================================================
% butler description   
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8038").
context(ctxmaitre, "localhost",  "TCP", "8094").
context(ctxfridge, "localhost",  "TCP", "8092").
 qactor( robot, ctxrobot, "it.unibo.robot.Robot").
  qactor( fridge, ctxfridge, "it.unibo.fridge.Fridge").
  qactor( maitre, ctxmaitre, "it.unibo.maitre.Maitre").
  qactor( user, ctxmaitre, "it.unibo.user.User").
