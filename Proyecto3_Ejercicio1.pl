% Definicion de los hechos que representan la conexion entre las urbanizaciones y las comisiones
urbanizacion(a, b, 10).     % Conexion entre a y b con una distancia de 10 km
urbanizacion(b, c, 3).      % Conexion entre b y c con una distancia de 3 km
urbanizacion(b, e, 5).      % Conexion entre b y e con una distancia de 5 km
urbanizacion(b, d, 5).      % Conexion entre b y d con una distancia de 4 km
urbanizacion(e, h, 2).      % Conexion entre e y h con una distancia de 2 km
urbanizacion(d, h, 1).      % Conexion entre d y h con una distancia de 1 km
urbanizacion(c, f, 2).      % Conexion entre c y f con una distancia de 2 km
urbanizacion(f, i, 3).      % Conexion entre f y i con una distancia de 3 km
urbanizacion(f, j, 10).     % Conexion entre f y j con una distancia de 3 km
urbanizacion(g, j, 1).      % Conexion entre g y j con una distancia de 1 km
urbanizacion(h, f, 3).      % Conexion entre h y f con una distancia de 1 km
urbanizacion(h, i, 6).      % Conexion entre h y i con una distancia de 6 km
urbanizacion_inversa(b, a, 10). % Conexion invertida entre b y a con una distancia de 10 km
urbanizacion_inversa(c, b, 3).  % Conexion invertida entre c y b con una distancia de 3 km
urbanizacion_inversa(e, b, 5).  % Conexion invertida entre e y b con una distancia de 5 km
urbanizacion_inversa(d, b, 4).  % Conexion invertida entre d y b con una distancia de 4 km
urbanizacion_inversa(h, e, 2).  % Conexion invertida entre h y e con una distancia de 2 km
urbanizacion_inversa(h, d, 1).  % Conexion invertida entre h y d con una distancia de 1 km
urbanizacion_inversa(f, c, 2).  % Conexion invertida entre f y c con una distancia de 2 km
urbanizacion_inversa(i, f, 3).  % Conexion invertida entre i y f con una distancia de 3 km
urbanizacion_inversa(j, f, 3).  % Conexion invertida entre j y f con una distancia de 3 km
urbanizacion_inversa(j, g, 1).  % Conexion invertida entre j y g con una distancia de 1 km
urbanizacion_inversa(f, h, 1).  % Conexion invertida entre f y h con una distancia de 1 km
urbanizacion_inversa(i, h, 6).  % Conexion invertida entre i y h con una distancia de 6 km

% Comisiones entre las urbanizaciones
comision(a, b, 5.0).        % Comision de 5% para ir de a a b
comision(b, c, 1.0).        % Comision de 1% para ir de b a c
comision(b, e, 2.0).        % Comision de 2% para ir de b a e
comision(b, d, 2.0).        % Comision de 2% para ir de b a d
comision(e, h, 1.0).        % Comision de 1% para ir de e a h
comision(d, h, 0.5).        % Comision de 0.5% para ir de d a h
comision(c, f, 1.0).        % Comision de 1% para ir de c a f
comision(f, i, 2.0).        % Comision de 2% para ir de f a i
comision(f, j, 5.0).        % Comision de 2% para ir de f a j
comision(g, j, 0.5).        % Comision de 0.5% para ir de g a j
comision(h, f, 1.0).        % Comision de 0.5% para ir de h a f
comision(h, i, 3.0).        % Comision de 3% para ir de h a i

comision(b, a, 5.0).        % Comision de 5% para ir de b a a
comision(c, b, 1.0).        % Comision de 1% para ir de c a b
comision(e, b, 2.0).        % Comision de 2% para ir de e a b
comision(d, b, 2.0).        % Comision de 2% para ir de d a b
comision(h, e, 1.0).        % Comision de 1% para ir de h a e
comision(h, d, 0.5).        % Comision de 0.5% para ir de h a d
comision(f, c, 1.0).        % Comision de 1% para ir de f a c
comision(i, f, 2.0).        % Comision de 2% para ir de i a f
comision(j, f, 5.0).        % Comision de 2% para ir de j a f
comision(j, g, 0.5).        % Comision de 0.5% para ir de j a g
comision(f, h, 1.0).        % Comision de 0.5% para ir de f a h
comision(i, h, 3.0).        % Comision de 3% para ir de i a h
comision(_, [], 0.0).       % Comision de 0% para cualquier otro caso


% Funcion recursiva que encuentra el camino entre dos nodos
encontrar_camino(A, B, [A, B], Distancia) :-
    urbanizacion(A, B, Distancia).                          % Existe una conexion directa entre A y B con una distancia determinada.
encontrar_camino(A, B, CaminoAB, Distancia) :-
    urbanizacion(A, C, X),                                  % Existe una conexion directa entre A y C con una distancia X.
    encontrar_camino(C, B, CaminoCB, DistanciaCB),          % Se busca un camino entre C y B.
    CaminoAB = [A | CaminoCB],                              % El camino completo es la concatenacion de A y el camino mas corto entre C y B.
    Distancia is X + DistanciaCB.                           % La distancia total es la suma de la distancia entre A y C y la distancia entre C y B.

% Funcion recursiva que encuentra el camino mas corto en sentido inverso entre dos urbanizaciones
encontrar_camino_inverso(A, B, [A, B], Distancia) :-
urbanizacion_inversa(A, B, Distancia).                     % Existe una conexion directa inversa entre A y B con una distancia determinada.
encontrar_camino_inverso(A, B, CaminoAB, Distancia) :-
    urbanizacion_inversa(A, C, X),                         % Existe una conexion directa inversa entre A y C con una distancia X.
    encontrar_camino_inverso(C, B, CaminoCB, DistanciaCB), % Se busca el camino mas corto en sentido inverso entre C y B.
    CaminoAB = [A | CaminoCB],                             % El camino completo es la concatenacion de A y el camino mas corto en sentido inverso entre C y B.
    Distancia is X + DistanciaCB.                          % La distancia total es la suma de la distancia entre A y C y la distancia entre C y B.

% Funcion principal que encuentra el camino mas corto y muestra la informacion
camino_mas_corto(A, B) :-
    findall(p(Distancia, Camino),
            encontrar_camino(A, B, Camino, Distancia),         % Encuentra todos los caminos posibles entre A y B junto con sus distancias.
            Caminos),
    sort(Caminos, Lista),                                      % Ordena los caminos de menor a mayor distancia.
    Lista = [p(Distancia1, Camino1) | _],                      % Obtiene el primer camino de la lista, que es el mas corto.
    write('Se encontro que el camino mas corto es: ('),
    imprimir_camino(Camino1),                                  % Imprime el camino mas corto.
    calcular_comisiones(Camino1, Comision),                    % Calcula la comision total del camino mas corto.
    writef(')\nCon una distancia de %d km y una comision de %d%\n', [Distancia1, Comision]). % Muestra la informacion del camino mas corto.

% Funcion principal que encuentra el camino mas corto en sentido inverso y muestra la informacion
camino_mas_corto_inverso(A, B) :-
    findall(p(Distancia, Camino),
            encontrar_camino_inverso(A, B, Camino, Distancia), % Encuentra todos los caminos posibles en sentido inverso entre A y B junto con sus distancias.
            Caminos),
    sort(Caminos, Lista),                                      % Ordena los caminos de menor a mayor distancia.
    Lista = [p(Distancia1, Camino1) | _],                      % Obtiene el primer camino de la lista, que es el mas corto en sentido inverso.
    write('Se encontro que el camino mas corto es: ('),
    imprimir_camino(Camino1),                                  % Imprime el camino mas corto en sentido inverso.
    calcular_comisiones(Camino1, Comision),                    % Calcula la comision total del camino mas corto en sentido inverso.
    writef(')\nCon una distancia de %d km y una comision de %d%\n', [Distancia1, Comision]). % Muestra la informacion del camino mas corto en sentido inverso.

% Funcion recursiva que recorre la lista de caminos solucion e imprime el costo de comisiones
calcular_comisiones([], 0).
calcular_comisiones([X|T], ComisionTotal) :-
    cabeza(T, Primero),                          % Obtiene el primer elemento de la lista T.
    comision(X, Primero, Comision),              % Obtiene la comision entre X y Primero.
    calcular_comisiones(T, ComisionParcial),     % Calcula la comision total para el resto de la lista T.
    ComisionTotal is Comision + ComisionParcial. % Calcula la comision total sumando la comision de X y Primero con la comision parcial del resto de la lista T.


% Funcion para extraer la cabeza de una lista
cabeza([], []).
cabeza([X|_], X). % La cabeza de una lista es el primer elemento de la lista.

% Funcion para imprimir el camino solucion
imprimir_camino([]).
imprimir_camino([X]) :-
    !, write(X).           % Si hay un solo elemento en la lista, se imprime sin coma ni espacio adicional.
imprimir_camino([X|Y]) :-
    write(X),
    write(', '),           % Imprime el elemento X seguido de una coma y un espacio.
    imprimir_camino(Y).    % Imprime el resto de la lista Y recursivamente.