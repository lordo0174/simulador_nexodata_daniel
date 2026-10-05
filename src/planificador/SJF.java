package planificador;

import java.util.List;

public class SJF implements Algoritmo {

    @Override
    public Proceso seleccionar(List<Proceso> listos) {

        if (listos.isEmpty()) {
            return null;
        }

        Proceso elegido = listos.get(0);

        for (Proceso proceso : listos) {

            if (proceso.getRafaga() < elegido.getRafaga()) {
                elegido = proceso;

            } else if (proceso.getRafaga() == elegido.getRafaga()) {

                if (proceso.getLlegada() < elegido.getLlegada()) {
                    elegido = proceso;

                } else if (proceso.getLlegada() == elegido.getLlegada()
                        && proceso.getOrden() < elegido.getOrden()) {
                    elegido = proceso;
                }
            }
        }

        return elegido;
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