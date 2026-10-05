package planificador;

import java.util.List;

public class FCFS implements Algoritmo {

    @Override
    public Proceso seleccionar(List<Proceso> listos) {

        if (listos.isEmpty()) {
            return null;
        }

        return listos.get(0);
    }

    @Override
    public boolean esRoundRobin() {
        return false;
    }

    @Override
    public int getQuantum() {
        return 0;
    }
}