%%to cunsult room state / location state
roomstate(List) :- setof((Location, Type, Object, Quantity), on(Location, Type, Object, Quantity), List).
state(Location, Type, List) :- setof((Object, Quantity), on(Location, Type, Object, Quantity), List).


%%initial state
%% on(location, type, object, quantity)
on(pantry, dishes, plate, 40).
on(pantry, dishes, glass, 40).
on(pantry, dishes, cutlery, 40).



%% action(action, target, type, [(object, quantity)]) action:put/take

action(put, Loc, Type) :- state(robot, Type, List), action(put, Loc, Type, List).
action(take, Loc, Type) :- state(Loc, Type, List), action(take, Loc, Type, List).

%%take from loc, put on robot .. fridge is different because it maintains its state
action(take, fridge, food, []).
action(take, fridge, food,[(Obj,N)|T]) :- addState(robot, food, Obj, N), action(take, fridge, food, T).
action(take, fridge, food, Obj, N) :- addRule(on(robot, food, Obj, N)).

action(take, Loc, Type, []).
action(take, Loc, Type,[(Obj,N)|T]) :- changeState(Loc, robot, Type, Obj, N), action(take, Loc, Type, T).

%%take from robot, put on loc .. fridge is different because it maintains its state
action(put, fridge, food, []).
action(put, fridge, food,[(Obj,N)|T]) :- removeRule(on(robot, food, Obj, N)), action(put, fridge, food, T).

action(put, Loc, Type, []).
action(put, Loc, Type,[(Obj,N)|T]) :- changeState(robot, Loc, Type, Obj, N), action(put, Loc, Type, T).



changeState(Source, Dest, Type, Object, Quantity) :-
	on(Source,Type,Object,N), NewN is N-Quantity, NewN>0, !,
	replaceRule(on(Source,Type,Object,N), on(Source,Type,Object,NewN)), 
	addState(Dest,Type, Object, Quantity).
	
changeState(Source, Dest, Type, Object, Quantity) :-
	on(Source,Type,Object,N),
	removeRule(on(Source,Type,Object,N)), 
	addState(Dest,Type, Object, Quantity).
	
addState(Dest,Type, Object, Quantity):- 
	on(Dest,Type, Object,X), !, 
	New is X+Quantity, 
	replaceRule(on(Dest, Type, Object, X), on(Dest, Type, Object, New)).
addState(Dest,Type, Object, Quantity):- 
	addRule(on(Dest, Type, Object, Quantity)).







		