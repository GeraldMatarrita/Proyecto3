import org.jpl7.Query;
import org.jpl7.Term;

import java.util.*;

/**
 * Esta clase representa el menú de Soporte Técnico.
 * Permite interactuar con el usuario para seleccionar dispositivos, problemas y soluciones.
 */
public class MenuSoporteTecnico {
    private Scanner scanner;
    private Query consult;

    /**
     * Crea una instancia del menú de Soporte Técnico.
     * Carga la base de conocimiento y muestra el menú principal.
     */
    public MenuSoporteTecnico() {

        // Inicializa la consulta a la base de conocimiento
        consult = new Query("consult('proyecto3_ejercicio2.pl')");

        // Muestra un mensaje de éxito o error al cargar la base de conocimiento
        System.out.println(consult.hasSolution() ? "Base de conocimiento cargada con éxito"
                : "Error al cargar la base de conocimiento");

        // Inicializa el scanner para leer la entrada del usuario
        this.scanner = new Scanner(System.in);

        // Muestra el menú principal
        mostrarMenuPrincipal();
    }

    /**
     * Muestra el menú principal.
     * Permite al usuario seleccionar un dispositivo y muestra el menú de problemas correspondiente.
     */
    public void mostrarMenuPrincipal() {
        System.out.println("==== Menú de Soporte Técnico ====");
        System.out.println("Seleccione un dispositivo:");

        // Muestra los dispositivos disponibles
        int opcion = 1;
        Query consultaDispositivos = new Query("dispositivo(Dispositivo)");
        while (consultaDispositivos.hasMoreSolutions()) {
            Term dispositivo = consultaDispositivos.nextSolution().get("Dispositivo");
            String disposivoString = dispositivo.toString().replace("'", "");
            System.out.println(opcion + ". " + disposivoString);
            opcion++;
        }
        // Muestra la opción de salir
        System.out.println("\n" + opcion + ". Salir");

        // Lee la opción del usuario
        int dispositivoSeleccionado = leerOpcion(new Query("dispositivo(Dispositivo)").allSolutions().length);

        // Si el usuario selecciona la opción de salir, termina la ejecución
        if (dispositivoSeleccionado == new Query("dispositivo(Dispositivo)").allSolutions().length + 1) {
            System.out.println("Gracias por usar nuestro sistema");
            return;
        }

        // Obtiene el dispositivo seleccionado
        String dispositivo = getDispositivoPorIndice(dispositivoSeleccionado);

        // Muestra el menú de problemas para el dispositivo seleccionado
        mostrarMenuProblemas(dispositivo);
    }

    /**
     * Muestra el menú de problemas para un dispositivo dado.
     *
     *  Entrada:
     *  -dispositivo: El dispositivo seleccionado.
     */
    public void mostrarMenuProblemas(String dispositivo) {
        System.out.println("==== Menú de Problemas ====");
        System.out.println("Seleccione un problema para el " + dispositivo.replace("'", "") + ":");

        // Muestra los problemas disponibles para el dispositivo seleccionado
        int opcion = 1;
        Query consultaProblemas = new Query("problema(" + dispositivo + ", Problema)");
        while (consultaProblemas.hasMoreSolutions()) {
            Term problema = consultaProblemas.nextSolution().get("Problema");
            String problemaString = problema.toString().replace("'", "");
            System.out.println(opcion + ". " + problemaString);
            opcion++;
        }

        // Lee la opción del usuario
        int problemaSeleccionado = leerOpcion(new Query("problema(" + dispositivo + ", Problema)").allSolutions().length);

        // Obtiene el problema seleccionado
        String problema = getProblemaPorIndice(dispositivo, problemaSeleccionado);

        // Muestra una posible solución para el problema seleccionado
        mostrarSolucion(dispositivo, problema);
    }

    /**
     * Muestra la solución para un problema dado.
     * Entrada:
     * -dispositivo: El dispositivo relacionado con el problema.
     * -problema: El problema seleccionado.
     */
    public void mostrarSolucion(String dispositivo, String problema) {
        System.out.println("==== Solución ====");
        System.out.println("Posible solución para el problema \"" + problema.replace("'", "") + "\":");

        // Si el problema es "Pantalla rota" para un "Telefono", muestra los pasos para solucionarlo
        if (dispositivo.equals("'Telefono'") && problema.equals("'Pantalla rota'")) {
            Query consultaPasos = new Query("solucion_recursiva('Pantalla rota')");
            if (consultaPasos.hasSolution()) {
                System.out.println("Pasos para solucionar el problema \"" + problema.replace("'", "") + "\":");
                Arrays.stream(consultaPasos.allSolutions()).forEach(solution -> {
                    Term pasos = solution.get("Problema");
                    System.out.println(pasos.toString().replace("'", ""));
                });
            }
            System.out.println("Presione cualquier tecla para continuar");
            scanner.nextLine();
            mostrarMenuPrincipal();
        } else {
            // Muestra una solución para el problema seleccionado
            Query consultaSoluciones = new Query("solucion(" + problema + ", Soluciones)");
            if (consultaSoluciones.hasSolution()) {
                Term soluciones = consultaSoluciones.oneSolution().get("Soluciones");
                // Si la solución es una lista, muestra la primera solución
                if (soluciones.isList()) {
                    Term[] solucionesArray = soluciones.toTermArray();
                    if (solucionesArray.length > 0) {
                        System.out.println(solucionesArray[0].toString().replace("'", ""));

                        System.out.println("¿Funcionó la solución? (S/N)");
                        String respuesta = scanner.nextLine().trim().toLowerCase();

                        // Si la solución funciona, muestra el menú principal
                        if (respuesta.equals("s")) {
                            mostrarMenuPrincipal();
                            // Si la solución no funciona, muestra las soluciones restantes
                        } else if (respuesta.equals("n")) {
                            mostrarMasSoluciones(dispositivo, problema, solucionesArray);
                        } else {
                            // Si la respuesta es inválida, muestra un mensaje de error y vuelve a mostrar la solución
                            System.out.println("Respuesta inválida. Intente nuevamente.");
                            mostrarSolucion(dispositivo, problema);
                        }
                    }
                }
            }
        }
    }

    /**
     * Muestra las soluciones restantes para un problema dado.
     * Entrada:
     * - dispositivo: El dispositivo relacionado con el problema.
     * - problema: El problema seleccionado.
     * - solucionesRestantes: Las soluciones restantes para el problema.
     */
    public void mostrarSolucionesRestantes(String dispositivo, String problema, Term[] solucionesRestantes) {

        // Si hay soluciones restantes, muestra la primera solución
        if (solucionesRestantes.length > 0) {
            System.out.println(solucionesRestantes[0].toString().replace("'", ""));

            System.out.println("==== Solución ====");
            System.out.println("¿Funcionó la solución? (S/N)");
            String respuesta = scanner.nextLine().trim().toLowerCase();

            // Si la solución funciona, muestra el menú principal
            if (respuesta.equals("s")) {
                mostrarMenuPrincipal();
                // Si la solución no funciona, muestra las soluciones restantes
            } else if (respuesta.equals("n")) {
                mostrarMasSoluciones(dispositivo, problema, solucionesRestantes);
            } else {
                // Si la respuesta es inválida, muestra un mensaje de error y vuelve a mostrar las soluciones restantes
                System.out.println("Respuesta inválida. Intente nuevamente.");
                mostrarSolucionesRestantes(dispositivo, problema, solucionesRestantes);
            }
        }
    }

    /**
     * Muestra más soluciones para un problema dado.
     * Entrada:
     * -dispositivo: El dispositivo relacionado con el problema.
     * -problema: El problema seleccionado.
     * -solucionesRestantes: Las soluciones restantes para el problema.
     */
    private void mostrarMasSoluciones(String dispositivo, String problema, Term[] solucionesRestantes) {
        // Si hay más soluciones restantes, muestra la siguiente solución
        if (solucionesRestantes.length > 1) {
            mostrarSolucionesRestantes(dispositivo, problema, Arrays.copyOfRange(solucionesRestantes, 1, solucionesRestantes.length));
        } else {
            // Si no hay más soluciones restantes, muestra un mensaje de error y vuelve al menú principal
            System.out.println("No hay más soluciones para el problema \"" + problema.replace("'", "") + "\"");
            System.out.println("Presione enter para continuar");
            scanner.nextLine();
            mostrarMenuPrincipal();
        }
    }

    /**
     * Lee la opción seleccionada por el usuario.
     * Entrada:
     * -maxOpciones El número máximo de opciones disponibles.
     * Salida:
     * La opción seleccionada por el usuario.
     */
    public int leerOpcion(int maxOpciones) {
        // Lee la opción del usuario hasta que sea válida
        while (true) {
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                // Si la opción es válida, la retorna
                if (opcion >= 1 && opcion <= maxOpciones + 1) {
                    return opcion;
                }
                // Si la opción está fuera del rango, muestra un mensaje de error y vuelve a leer la opción
                System.out.println("Opción inválida. Intente nuevamente.");
            } catch (NumberFormatException e) {
                // Si la opción es inválida, muestra un mensaje de error y vuelve a leer la opción
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    /**
     * Obtiene el dispositivo correspondiente a un índice dado.
     * Entrada:
     * -indice: El índice del dispositivo.
     * Salida:
     * El dispositivo correspondiente al índice.
     */
    public String getDispositivoPorIndice(int indice) {
        int contador = 1;
        // Obtiene todos los dispositivos
        Query consultaDispositivos = new Query("dispositivo(Dispositivo)");
        while (consultaDispositivos.hasMoreSolutions()) {
            // Si el contador es igual al índice, retorna el dispositivo
            Term dispositivo = consultaDispositivos.nextSolution().get("Dispositivo");
            if (contador == indice) {
                return dispositivo.toString();
            }
            contador++;
        }

        // Si no se encontró el dispositivo, retorna null
        return null;
    }

    /**
     * Obtiene el problema correspondiente a un índice dado para un dispositivo dado.
     * Entrada:
     * - dispositivo: El dispositivo relacionado con el problema.
     * - indice: El índice del problema.
     * Salida:
     * El problema correspondiente al índice y dispositivo.
     */
    public String getProblemaPorIndice(String dispositivo, int indice) {
        int contador = 1;

        // Obtiene todos los problemas para el dispositivo dado
        Query consultaProblemas = new Query("problema(" + dispositivo + ", Problema)");
        while (consultaProblemas.hasMoreSolutions()) {
            // Si el contador es igual al índice, retorna el problema
            Term problema = consultaProblemas.nextSolution().get("Problema");
            if (contador == indice) {
                return problema.toString();
            }
            contador++;
        }

        // Si no se encontró el problema, retorna null
        return null;
    }
}
