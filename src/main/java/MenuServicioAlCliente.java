import org.jpl7.Query;
import org.jpl7.Term;

import java.util.*;

public class MenuServicioAlCliente {
    private Scanner scanner;
    private Query consult;

    public MenuServicioAlCliente() {

        consult = new Query("consult('proyecto3.pl')");

        System.out.println(consult.hasSolution() ? "Base de conocimiento cargada con éxito"
                : "Error al cargar la base de conocimiento");

        this.scanner = new Scanner(System.in);
        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() {
        System.out.println("==== Menú de Servicio al Cliente ====");
        System.out.println("Seleccione un dispositivo:");

        int opcion = 1;
        Query consultaDispositivos = new Query("dispositivo(Dispositivo)");
        while (consultaDispositivos.hasMoreSolutions()) {
            Term dispositivo = consultaDispositivos.nextSolution().get("Dispositivo");
            String disposivoString = dispositivo.toString().replace("'", "");
            System.out.println(opcion + ". " + disposivoString);
            opcion++;
        }
        System.out.println("\n" + opcion + ". Salir");

        int dispositivoSeleccionado = leerOpcion(new Query("dispositivo(Dispositivo)").allSolutions().length);

        if (dispositivoSeleccionado == new Query("dispositivo(Dispositivo)").allSolutions().length + 1) {
            System.out.println("Gracias por usar el servicio al cliente");
            return;
        }
        String dispositivo = getDispositivoPorIndice(dispositivoSeleccionado);

        mostrarMenuProblemas(dispositivo);
    }

    public void mostrarMenuProblemas(String dispositivo) {
        System.out.println("==== Menú de Problemas ====");
        System.out.println("Seleccione un problema para el " + dispositivo.replace("'", "") + ":");
        int opcion = 1;

        Query consultaProblemas = new Query("problema(" + dispositivo + ", Problema)");
        while (consultaProblemas.hasMoreSolutions()) {
            Term problema = consultaProblemas.nextSolution().get("Problema");
            String problemaString = problema.toString().replace("'", "");
            System.out.println(opcion + ". " + problemaString);
            opcion++;
        }

        int problemaSeleccionado = leerOpcion(new Query("problema(" + dispositivo + ", Problema)").allSolutions().length);

        String problema = getProblemaPorIndice(dispositivo, problemaSeleccionado);
        mostrarSolucion(dispositivo, problema);
    }

    public void mostrarSolucion(String dispositivo, String problema) {
        System.out.println("==== Solución ====");
        System.out.println("Posible solución para el problema \"" + problema.replace("'", "") + "\":");

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
            Query consultaSoluciones = new Query("solucion(" + problema + ", Soluciones)");
            if (consultaSoluciones.hasSolution()) {
                Term soluciones = consultaSoluciones.oneSolution().get("Soluciones");
                if (soluciones.isList()) {
                    Term[] solucionesArray = soluciones.toTermArray();
                    if (solucionesArray.length > 0) {
                        System.out.println(solucionesArray[0].toString().replace("'", ""));

                        System.out.println("¿Funcionó la solución? (S/N)");
                        String respuesta = scanner.nextLine().trim().toLowerCase();

                        if (respuesta.equals("s")) {
                            mostrarMenuPrincipal();
                        } else if (respuesta.equals("n")) {
                            if (solucionesArray.length > 1) {
                                mostrarSolucionesRestantes(dispositivo, problema, Arrays.copyOfRange(solucionesArray, 1, solucionesArray.length));
                            } else {
                                System.out.println("No hay más soluciones para el problema \"" + problema.replace("'", "") + "\"");
                                mostrarMenuPrincipal();
                            }
                        } else {
                            System.out.println("Respuesta inválida. Intente nuevamente.");
                            mostrarSolucion(dispositivo, problema);
                        }
                    }
                }
            }
        }
    }

    public void mostrarSolucionesRestantes(String dispositivo, String problema, Term[] solucionesRestantes) {
        if (solucionesRestantes.length > 0) {
            System.out.println(solucionesRestantes[0].toString().replace("'", ""));

            System.out.println("==== Solución ====");
            System.out.println("¿Funcionó la solución? (S/N)");
            String respuesta = scanner.nextLine().trim().toLowerCase();

            if (respuesta.equals("s")) {
                mostrarMenuPrincipal();
            } else if (respuesta.equals("n")) {
                if (solucionesRestantes.length > 1) {
                    mostrarSolucionesRestantes(dispositivo, problema, Arrays.copyOfRange(solucionesRestantes, 1, solucionesRestantes.length));
                } else {
                    System.out.println("No hay más soluciones para el problema \"" + problema.replace("'", "") + "\"");
                    mostrarMenuPrincipal();
                }
            } else {
                System.out.println("Respuesta inválida. Intente nuevamente.");
                mostrarSolucionesRestantes(dispositivo, problema, solucionesRestantes);
            }
        }
    }

    public int leerOpcion(int maxOpciones) {
        while (true) {
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion >= 1 && opcion <= maxOpciones + 1) {
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
        Query consultaDispositivos = new Query("dispositivo(Dispositivo)");
        while (consultaDispositivos.hasMoreSolutions()) {
            Term dispositivo = consultaDispositivos.nextSolution().get("Dispositivo");
            if (contador == indice) {
                return dispositivo.toString();
            }
            contador++;
        }

        return null;
    }

    public String getProblemaPorIndice(String dispositivo, int indice) {
        int contador = 1;
        Query consultaProblemas = new Query("problema(" + dispositivo + ", Problema)");
        while (consultaProblemas.hasMoreSolutions()) {
            Term problema = consultaProblemas.nextSolution().get("Problema");
            if (contador == indice) {
                return problema.toString();
            }
            contador++;
        }

        return null;
    }
}
