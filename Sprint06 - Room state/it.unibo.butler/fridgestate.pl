%% on(location, type, object, quantity)
on(fridge, food, pizza, 5).
on(fridge, food, pasta, 6).

state(fridge, List) :- setof((fridge, Type, Object, Quantity), on(fridge, Type, Object, Quantity), List). 

check(Obj, Quantity) :- on(fridge, food, Obj, X), X>=Quantity.

action(take, fridge, food, []).
action(take, fridge, food,[(Obj,N)|T]) :- 	on(fridge, food, Obj, X), 
											Quantity is X-N, 
											replaceRule(on(fridge, food, Obj, X), 
											on(fridge, food, Obj, Quantity)), 
											action(take, fridge, food, T). 
action(take, fridge, food, Obj, N) :- 	on(fridge, food, Obj, X), 
										Quantity is X-N, 
										replaceRule(on(fridge, food, Obj, X), 
										on(fridge, food, Obj, Quantity)).

