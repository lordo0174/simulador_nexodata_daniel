package planificador;

import java.util.List;

public interface Algoritmo {

    Proceso seleccionar(List<Proceso> listos);

    boolean esRoundRobin();

    int getQuantum();
}