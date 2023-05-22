% Hechos sobre dispositivos
dispositivo('Telefono').
dispositivo('Tableta').
dispositivo('Laptop').
dispositivo('Computadora de escritorio').
dispositivo('Impresora').
dispositivo('Camara').

% Hechos sobre problemas y soluciones para cada dispositivo
problema('Telefono', 'No enciende').
problema('Telefono', 'Pantalla rota').
problema('Telefono', 'Bateria descargada').
problema('Tableta', 'No se conecta a Wi-Fi').
problema('Tableta', 'No carga').
problema('Tableta', 'Lento').
problema('Laptop', 'Pantalla en negro').
problema('Laptop', 'Teclado no funciona').
problema('Computadora de escritorio', 'No arranca').
problema('Computadora de escritorio', 'Pantalla azul').
problema('Impresora', 'Atasco de papel').
problema('Impresora', 'Impresion borrosa').
problema('Camara', 'Bateria agotada').
problema('Camara', 'Tarjeta de memoria llena').

% Hechos sobre soluciones para cada problema
solucion('No enciende', ['Cargue el telefono', 'Reemplace la bateria']).
solucion('Bateria descargada', ['Cargue la bateria']).
solucion('No se conecta a Wi-Fi', ['Reinicie el enrutador', 'Verifique la configuracion de Wi-Fi']).
solucion('No carga', ['Reemplace el cable de carga', 'Verifique el puerto de carga']).
solucion('Lento', ['Libere espacio de almacenamiento', 'Realice un reinicio de fabrica']).
solucion('Pantalla en negro', ['Reinicie el equipo', 'Verifique la conexion del cable de alimentacion']).
solucion('Teclado no funciona', ['Reinicie el equipo', 'Verifique si hay teclas atascadas']).
solucion('No arranca', ['Verifique el cable de alimentacion', 'Reemplace la fuente de poder']).
solucion('Pantalla azul', ['Verifique si hay problemas de hardware', 'Actualice los controladores']).
solucion('Atasco de papel', ['Retire con cuidado el papel atascado', 'Verifique los rodillos de alimentación']).
solucion('Impresion borrosa', ['Reemplace el cartucho de tinta', 'Limpie los cabezales de impresion']).
solucion('Bateria agotada', ['Cargue la bateria de la camara', 'Reemplace la bateria si es necesario']).
solucion('Tarjeta de memoria llena', ['Descargue las fotos o videos en una computadora', 'Reemplace la tarjeta de memoria']).

% Hechos sobre pasos para resolver el problema pantalla rota del Telefono
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