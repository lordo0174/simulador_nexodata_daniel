package planificador;

import java.util.List;

public class Resultado {

    private List<Proceso> procesos;
    private List<String> gantt;
    private int cambiosContexto;

    public Resultado(List<Proceso> procesos, List<String> gantt, int cambiosContexto) {
        this.procesos = procesos;
        this.gantt = gantt;
        this.cambiosContexto = cambiosContexto;
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }

    public List<String> getGantt() {
        return gantt;
    }

    public int getCambiosContexto() {
        return cambiosContexto;
    }
}