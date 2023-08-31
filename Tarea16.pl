% Estudiantes:
% Gerald Matarrita Alvarado 2020144486
% Joseph Rojas Aragon 2021066885

% Definición de los hechos padre
% El primer argumento es el hijo y el segundo es el padre
padre(chordata, animalia).
padre(aves, chordata).
padre(apodiformes, aves).
padre(trochilidae, apodiformes).
padre(colibri, trochilidae).
padre(thalassinus, colibri).

padre(magnoliophyta, plantae).
padre(magnoliopsida, magnoliophyta).
padre(malvales, magnoliopsida).
padre(malvaceae, malvales).
padre(ceiba, malvaceae).
padre(pentandra, ceiba).

% Definición de la regla super
% La regla super se utiliza para obtener la jerarquía de un elemento
% El primer argumento es el hijo y el segundo es el padre
super(X, Y) :- padre(X,Y).
super(X, Y) :- padre(X,Z), super(Z,Y).

% Regla para encontrar el valor maximo en una lista
maximo([X], X). % Caso base: la lista contiene un único elemento
maximo([X], X). % Caso base: la lista contiene un único elemento
maximo([H|T], X) :- maximo(T, M), H =< M, X is M. % Si H es menor o igual que el máximo del resto de la lista, el máximo es el mismo.
maximo([H|T], X) :- maximo(T, M), H > M, X is H. % Si H es mayor que el máximo del resto de la lista, entonces H es el nuevo máximo.

% Regla para verificar si X es un miembro de la lista L
miembro(X, [X|_]). % Caso base: X es el primer elemento de la lista
miembro(X, [_|T]) :- miembro(X,T). % Si X no es el primer elemento, comprobamos si es un miembro del resto de la lista

% Regla para verificar si L es un subconjunto de K
subconjunto([], _). % Un conjunto vacío es siempre un subconjunto de cualquier conjunto
subconjunto([X|T], K) :- miembro(X, K), subconjunto(T, K). % Si X es un miembro de K, comprobamos si T es un subconjunto de K
