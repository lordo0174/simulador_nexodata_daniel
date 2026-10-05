package planificador;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * PSP · Tema 2 · Simulador de planificación para NexoData
 *
 * Punto de entrada. Este fichero YA ESTÁ HECHO: solo lee y comprueba los argumentos.
 * No cambies cómo se leen: el profesor ejecutará tu programa siempre así:
 *
 *   java planificador.Main <fichero.csv> <fcfs|sjf|rr|todos> [quantum] [--traza]
 *
 * Todo lo demás (modelo del proceso, lectura del CSV, algoritmos, métricas,
 * informe por consola...) lo diseñas y programas tú en este mismo paquete.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Uso: java planificador.Main <fichero.csv> <fcfs|sjf|rr|todos> [quantum] [--traza]");
            System.exit(1);
        }
        Path fichero = Path.of(args[0]);
        String algoritmo = args[1].toLowerCase();
        boolean traza = List.of(args).contains("--traza");
        int quantum = 2;
        if (args.length >= 3 && !args[2].startsWith("--")) {
            try {
                quantum = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("El quantum debe ser un número entero: " + args[2]);
                System.exit(1);
            }
        }
        if (!Files.exists(fichero)) {
            System.err.println("No encuentro el fichero " + fichero.toAbsolutePath()
                    + "\nComprueba el «Working directory» de la configuración de ejecución.");
            System.exit(1);
        }
        if (!List.of("fcfs", "sjf", "rr", "todos").contains(algoritmo)) {
            System.err.println("Algoritmo desconocido: " + algoritmo + " (usa fcfs, sjf, rr o todos)");
            System.exit(1);
        }

        System.out.println("Fichero: " + fichero + " | algoritmo: " + algoritmo
                + " | quantum: " + quantum + " | traza: " + traza);

        // TODO (tareas 1 a 3): a partir de aquí, lee los procesos del fichero,
        // simula el algoritmo o algoritmos pedidos y muestra los resultados.
        // Cuando lo tengas, borra el println de arriba y este comentario.

        try {

            // Leer procesos del fichero
            List<Proceso> procesos = LectorProcesos.leer(fichero);

            System.out.println("Procesos cargados:");
            System.out.println("-----------------");

            for (Proceso proceso : procesos) {
                System.out.println(
                        proceso.getNombre()
                                + " | llegada=" + proceso.getLlegada()
                                + " | ráfaga=" + proceso.getRafaga()
                                + " | restante=" + proceso.getRestante()
                                + " | estado=" + proceso.getEstado()
                );
            }

            // Crear simulador
            Simulador simulador = new Simulador();

            // FCFS
            if (algoritmo.equals("fcfs")) {

                Resultado resultado = simulador.ejecutar(
                        procesos,
                        new FCFS(),
                        traza
                );

                mostrarResultado("FCFS", resultado);

                // SJF
            } else if (algoritmo.equals("sjf")) {

                Resultado resultado = simulador.ejecutar(
                        procesos,
                        new SJF(),
                        traza
                );

                mostrarResultado("SJF", resultado);

                // Round Robin
            } else if (algoritmo.equals("rr")) {

                Resultado resultado = simulador.ejecutar(
                        procesos,
                        new RoundRobin(quantum),
                        traza
                );

                mostrarResultado(
                        "Round Robin (q=" + quantum + ")",
                        resultado
                );

                // Todos
            } else if (algoritmo.equals("todos")) {

                Resultado resultadoFCFS = simulador.ejecutar(
                        procesos,
                        new FCFS(),
                        traza
                );

                Resultado resultadoSJF = simulador.ejecutar(
                        procesos,
                        new SJF(),
                        traza
                );

                Resultado resultadoRR = simulador.ejecutar(
                        procesos,
                        new RoundRobin(quantum),
                        traza
                );

                mostrarResultado("FCFS", resultadoFCFS);

                mostrarResultado("SJF", resultadoSJF);

                mostrarResultado(
                        "Round Robin (q=" + quantum + ")",
                        resultadoRR
                );
            }

        } catch (Exception e) {

            System.err.println(
                    "Error al ejecutar el simulador: "
                            + e.getMessage()
            );

            System.exit(1);
        }
    }

    private static void mostrarResultado(
            String nombreAlgoritmo,
            Resultado resultado) {

        System.out.println();
        System.out.println("=== " + nombreAlgoritmo + " ===");

        System.out.println("Gantt:");
        System.out.println(resultado.getGantt());

        System.out.println(
                "Cambios de contexto: "
                        + resultado.getCambiosContexto()
        );

        System.out.println();

        System.out.println(
                "Proceso | Fin | Retorno | Espera | Respuesta"
        );

        System.out.println(
                "---------------------------------------------"
        );

        for (Proceso proceso : resultado.getProcesos()) {

            System.out.println(
                    proceso.getNombre()
                            + "       | "
                            + proceso.getFin()
                            + "   | "
                            + proceso.getRetorno()
                            + "       | "
                            + proceso.getEspera()
                            + "      | "
                            + proceso.getRespuesta()
            );
        }

        double mediaRetorno = 0;
        double mediaEspera = 0;
        double mediaRespuesta = 0;

        for (Proceso proceso : resultado.getProcesos()) {

            mediaRetorno += proceso.getRetorno();
            mediaEspera += proceso.getEspera();
            mediaRespuesta += proceso.getRespuesta();
        }

        int cantidad = resultado.getProcesos().size();

        if (cantidad > 0) {

            mediaRetorno = mediaRetorno / cantidad;
            mediaEspera = mediaEspera / cantidad;
            mediaRespuesta = mediaRespuesta / cantidad;
        }

        System.out.printf(
                "Media retorno: %.2f%n",
                mediaRetorno
        );

        System.out.printf(
                "Media espera: %.2f%n",
                mediaEspera
        );

        System.out.printf(
                "Media respuesta: %.2f%n",
                mediaRespuesta
        );
    }
}