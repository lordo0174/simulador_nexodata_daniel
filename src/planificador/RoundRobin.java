package planificador;

import java.util.List;

public class RoundRobin implements Algoritmo {

    private int quantum;

    public RoundRobin(int quantum) {

        if (quantum <= 0) {
            throw new IllegalArgumentException(
                    "El quantum debe ser mayor que cero"
            );
        }

        this.quantum = quantum;
    }

    @Override
    public Proceso seleccionar(List<Proceso> listos) {

        if (listos.isEmpty()) {
            return null;
        }

        return listos.get(0);
    }

    @Override
    public boolean esRoundRobin() {
        return true;
    }

    @Override
    public int getQuantum() {
        return quantum;
    }
}