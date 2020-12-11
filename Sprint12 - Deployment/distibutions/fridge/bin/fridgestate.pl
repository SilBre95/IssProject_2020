%% on(location, type, object, quantity)
on(fridge, food, f000, 15).
on(fridge, food, f001, 36).
on(fridge, food, f002, 25).
on(fridge, food, f003, 6).
on(fridge, food, f004, 5).
on(fridge, food, f005, 3).
on(fridge, food, f006, 9).
on(fridge, food, f007, 17).
on(fridge, food, f008, 11).
on(fridge, food, f009, 10).
on(fridge, food, f010, 5).
on(fridge, food, f011, 8).

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

