package planificador;

public class Proceso {

    private String nombre;
    private int llegada;
    private int rafaga;
    private int restante;
    private EstadoProceso estado;

    private int fin;
    private int retorno;
    private int espera;
    private int respuesta;

    private int orden;

    public Proceso(String nombre, int llegada, int rafaga, int orden) {
        this.nombre = nombre;
        this.llegada = llegada;
        this.rafaga = rafaga;
        this.restante = rafaga;
        this.estado = EstadoProceso.NUEVO;

        this.fin = -1;
        this.retorno = -1;
        this.espera = -1;
        this.respuesta = -1;

        this.orden = orden;
    }

    public Proceso(Proceso otro) {
        this.nombre = otro.nombre;
        this.llegada = otro.llegada;
        this.rafaga = otro.rafaga;
        this.restante = otro.rafaga;
        this.estado = EstadoProceso.NUEVO;

        this.fin = -1;
        this.retorno = -1;
        this.espera = -1;
        this.respuesta = -1;

        this.orden = otro.orden;
    }

    public String getNombre() {
        return nombre;
    }

    public int getLlegada() {
        return llegada;
    }

    public int getRafaga() {
        return rafaga;
    }

    public int getRestante() {
        return restante;
    }

    public EstadoProceso getEstado() {
        return estado;
    }

    public int getFin() {
        return fin;
    }

    public int getRetorno() {
        return retorno;
    }

    public int getEspera() {
        return espera;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public int getOrden() {
        return orden;
    }

    public void setEstado(EstadoProceso estado) {
        this.estado = estado;
    }

    public void setRestante(int restante) {
        this.restante = restante;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public void setRetorno(int retorno) {
        this.retorno = retorno;
    }

    public void setEspera(int espera) {
        this.espera = espera;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }

    public boolean estaTerminado() {
        return restante == 0;
    }

    public Proceso copia() {
        return new Proceso(this);
    }
}