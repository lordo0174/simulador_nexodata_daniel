package planificador;

import java.util.ArrayList;
import java.util.List;

public class Simulador {

    public Resultado ejecutar(List<Proceso> procesosOriginales,
                              Algoritmo algoritmo) {

        List<Proceso> procesos = new ArrayList<>();

        for (Proceso proceso : procesosOriginales) {
            procesos.add(proceso.copia());
        }

        List<Proceso> listos = new ArrayList<>();
        List<String> gantt = new ArrayList<>();

        Proceso ejecutando = null;

        int tiempo = 0;
        int quantumRestante = algoritmo.getQuantum();

        String procesoAnterior = null;
        int cambiosContexto = 0;

        while (!todosTerminados(procesos)) {

            // 1. Llegan los procesos
            for (Proceso proceso : procesos) {

                if (proceso.getLlegada() == tiempo
                        && proceso.getEstado() == EstadoProceso.NUEVO) {

                    proceso.setEstado(EstadoProceso.LISTO);
                    listos.add(proceso);
                }
            }

            // 2. Si el proceso ha terminado, registrar sus métricas
            if (ejecutando != null && ejecutando.estaTerminado()) {

                ejecutarFin(ejecutando, tiempo);

                ejecutando = null;
                quantumRestante = algoritmo.getQuantum();
            }

            // 3. Comprobar expiración del quantum
            if (ejecutando != null
                    && algoritmo.esRoundRobin()
                    && quantumRestante == 0) {

                if (!listos.isEmpty()) {

                    ejecutando.setEstado(EstadoProceso.LISTO);
                    listos.add(ejecutando);

                    ejecutando = null;
                    quantumRestante = algoritmo.getQuantum();

                } else {

                    quantumRestante = algoritmo.getQuantum();
                }
            }

            // 4. Elegir un proceso si la CPU está libre
            if (ejecutando == null && !listos.isEmpty()) {

                ejecutando = algoritmo.seleccionar(listos);
                listos.remove(ejecutando);

                ejecutando.setEstado(EstadoProceso.EJECUCION);

                // Primera entrada en CPU
                if (ejecutando.getRespuesta() == -1) {

                    ejecutando.setRespuesta(
                            tiempo - ejecutando.getLlegada()
                    );
                }

                // Cambio de contexto
                if (procesoAnterior != null
                        && !procesoAnterior.equals(ejecutando.getNombre())) {

                    cambiosContexto++;
                }

                procesoAnterior = ejecutando.getNombre();

                quantumRestante = algoritmo.getQuantum();
            }

            // 5. Ejecutar una unidad de tiempo
            if (ejecutando != null) {

                gantt.add(ejecutando.getNombre());

                ejecutando.setRestante(
                        ejecutando.getRestante() - 1
                );

                if (algoritmo.esRoundRobin()) {
                    quantumRestante--;
                }

            } else {

                gantt.add("-");
                procesoAnterior = null;
            }

            tiempo++;
        }

        // Registrar el último proceso que haya terminado
        if (ejecutando != null && ejecutando.estaTerminado()) {
            ejecutarFin(ejecutando, tiempo);
        }

        return new Resultado(
                procesos,
                gantt,
                cambiosContexto
        );
    }

    private void ejecutarFin(Proceso proceso, int tiempo) {

        proceso.setEstado(EstadoProceso.TERMINADO);

        proceso.setFin(tiempo);

        proceso.setRetorno(
                tiempo - proceso.getLlegada()
        );

        proceso.setEspera(
                proceso.getRetorno() - proceso.getRafaga()
        );
    }

    private boolean todosTerminados(List<Proceso> procesos) {

        for (Proceso proceso : procesos) {

            if (!proceso.estaTerminado()) {
                return false;
            }
        }

        return true;
    }
}