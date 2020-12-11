%====================================================================================
% butler description   
%====================================================================================
context(ctxroom, "localhost",  "TCP", "8038").
 qactor( robot, ctxroom, "it.unibo.robot.Robot").
  qactor( fridge, ctxroom, "it.unibo.fridge.Fridge").
  qactor( maitre, ctxroom, "it.unibo.maitre.Maitre").
