% Hechos sobre dispositivos
dispositivo('Telefono').
dispositivo('Tableta').

% Hechos sobre problemas y soluciones para cada dispositivo
problema('Telefono', 'No enciende').
problema('Telefono', 'Pantalla rota').
problema('Telefono', 'Bateria descargada').
problema('Tableta', 'No se conecta a Wi-Fi').
problema('Tableta', 'No carga').
problema('Tableta', 'Lento').

solucion('No enciende', ['Cargue el telefono', 'Reemplace la bateria']).
solucion('Bateria descargada', ['Cargue la bateria']).
solucion('No se conecta a Wi-Fi', ['Reinicie el enrutador', 'Verifique la configuracion de Wi-Fi']).
solucion('No carga', ['Reemplace el cable de carga', 'Verifique el puerto de carga']).
solucion('Lento', ['Libere espacio de almacenamiento', 'Realice un reinicio de fabrica']).

% Hechos sobre pasos para resolver los problemas
paso('Pantalla rota', '1. Apagar el telefono').
paso('1. Apagar el telefono', '2. Cambiar la pantalla').
paso('2. Cambiar la pantalla', '3. Encender el telefono').
paso('3. Encender el telefono', '4. Verificar el correcto funcionamiento').
paso('4. Verificar el correcto funcionamiento', end).

% Regla para mostrar los pasos de manera consecutiva
solucion_recursiva(Problema) :-
    dif(Paso, end),
    paso(Problema, Paso),
    write(Paso), nl,
    solucion_recursiva(Paso).