%====================================================================================
% maitre description   
%====================================================================================
context(ctxmaitre, "localhost",  "TCP", "8039").
context(ctxrobot, "127.0.0.1",  "TCP", "8040").
context(ctxfridge, "::1",  "TCP", "8038").
 qactor( robot, ctxrobot, "external").
  qactor( fridge, ctxfridge, "external").
  qactor( maitre, ctxmaitre, "it.unibo.maitre.Maitre").
  qactor( console, ctxmaitre, "it.unibo.console.Console").
