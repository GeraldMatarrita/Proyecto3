import java.util.*;

public class MenuServicioAlCliente {

    private Map<String, Map<String, Queue<String>>> menu;
    private Scanner scanner;

    public MenuServicioAlCliente() {
        menu = new HashMap<>();
        this.scanner = new Scanner(System.in);
        //Agregar opciones al menú
        agregarOpcion("Teléfono", "No enciende", Arrays.asList("Cargue el teléfono", "Reemplace la batería"));
        agregarOpcion("Teléfono", "Pantalla rota", Arrays.asList("Reemplace la pantalla", "Visite un centro de servicio"));
        agregarOpcion("Teléfono", "Batería descargada", Collections.singletonList("Cargue la batería"));
        agregarOpcion("Tableta", "No se conecta a Wi-Fi", Arrays.asList("Reinicie el enrutador", "Verifique la configuración de Wi-Fi"));
        agregarOpcion("Tableta", "No carga", Arrays.asList("Reemplace el cable de carga", "Verifique el puerto de carga"));
        agregarOpcion("Tableta", "Lento", Arrays.asList("Libere espacio de almacenamiento", "Realice un reinicio de fábrica"));

        mostrarMenuPrincipal();
    }

    public void agregarOpcion(String dispositivo, String problema, List<String> soluciones) {
        if (!menu.containsKey(dispositivo)) {
            menu.put(dispositivo, new HashMap<>());
        }
        Queue<String> solucionesQueue = new LinkedList<>(soluciones);
        menu.get(dispositivo).put(problema, solucionesQueue);
    }

    public void mostrarMenuPrincipal() {
        System.out.println("==== Menú de Servicio al Cliente ====");
        System.out.println("Seleccione un dispositivo:");
        int opcion = 1;
        for (String dispositivo : menu.keySet()) {
            System.out.println(opcion + ". " + dispositivo);
            opcion++;
        }
        int dispositivoSeleccionado = leerOpcion(menu.size());

        String dispositivo = getDispositivoPorIndice(dispositivoSeleccionado);
        mostrarMenuProblemas(dispositivo);
    }

    public void mostrarMenuProblemas(String dispositivo) {
        System.out.println("==== Menú de Problemas ====");
        System.out.println("Seleccione un problema para el " + dispositivo + ":");
        int opcion = 1;
        Map<String, Queue<String>> problemas = menu.get(dispositivo);
        for (String problema : problemas.keySet()) {
            System.out.println(opcion + ". " + problema);
            opcion++;
        }
        int problemaSeleccionado = leerOpcion(problemas.size());

        String problema = getProblemaPorIndice(dispositivo, problemaSeleccionado);
        mostrarSolucion(dispositivo, problema);
    }

    public void mostrarSolucion(String dispositivo, String problema) {
        System.out.println("==== Solución ====");
        System.out.println("Posible solución para el problema \"" + problema + "\":");
        Queue<String> solucionesQueue = menu.get(dispositivo).get(problema);
        String solucion = solucionesQueue.poll();
        System.out.println(solucion);
        System.out.println("¿Funcionó la solución? (S/N)");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        if (solucionesQueue.isEmpty()) {
            System.out.println("------------------------------------");
            System.out.println("No hay más soluciones para este problema.");
            System.out.println("------------------------------------\n");
            mostrarMenuPrincipal();
        } else {
            if (respuesta.equals("s")) {
                mostrarMenuPrincipal();
            } else if (respuesta.equals("n")){
                mostrarSolucion(dispositivo, problema);
            } else {
                System.out.println("Respuesta inválida. Intente nuevamente.");
                mostrarSolucion(dispositivo, problema);
            }
        }
    }

    public int leerOpcion(int maxOpciones) {
        while (true) {
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion >= 1 && opcion <= maxOpciones) {
                    return opcion;
                }
                System.out.println("Opción inválida. Intente nuevamente.");
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    public String getDispositivoPorIndice(int indice) {
        int contador = 1;
        for (String dispositivo : menu.keySet()) {
            if (contador == indice) {
                return dispositivo;
            }
            contador++;
        }
        return null;
    }

    public String getProblemaPorIndice(String dispositivo, int indice) {
        int contador = 1;
        Map<String, Queue<String>> problemas = menu.get(dispositivo);
        for (String problema : problemas.keySet()) {
            if (contador == indice) {
                return problema;
            }
            contador++;
        }
        return null;
    }
}
