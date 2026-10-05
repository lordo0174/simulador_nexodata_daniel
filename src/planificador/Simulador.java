package planificador;

import java.util.ArrayList;
import java.util.List;

public class Simulador {

    public List<String> ejecutar(List<Proceso> procesosOriginales,
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

        while (!todosTerminados(procesos)) {

            // 1. Llegan los procesos en este instante
            for (Proceso proceso : procesos) {

                if (proceso.getLlegada() == tiempo
                        && proceso.getEstado() == EstadoProceso.NUEVO) {

                    proceso.setEstado(EstadoProceso.LISTO);
                    listos.add(proceso);
                }
            }

            // 2. Comprobar si termina el proceso actual
            if (ejecutando != null && ejecutando.estaTerminado()) {

                ejecutando.setEstado(EstadoProceso.TERMINADO);
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

                    // No hay nadie esperando.
                    // El mismo proceso continúa.
                    quantumRestante = algoritmo.getQuantum();
                }
            }

            // 4. Si la CPU está libre, elegir proceso
            if (ejecutando == null && !listos.isEmpty()) {

                ejecutando = algoritmo.seleccionar(listos);
                listos.remove(ejecutando);

                ejecutando.setEstado(EstadoProceso.EJECUCION);

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

                // CPU sin trabajo
                gantt.add("-");
            }

            tiempo++;
        }

        return gantt;
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