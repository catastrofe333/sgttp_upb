package modelo;

import java.time.LocalDateTime;

public class Viaje {
    private String idViaje;
    private Tren tren;
    private Ruta ruta;
    private double valorBase;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;
    private String estadoTrayecto; //pendiente, en viaje, finalizado

    public Viaje(String idViaje, Tren tren, Ruta ruta, LocalDateTime fechaSalida, LocalDateTime fechaLlegada) {
        this.idViaje = idViaje;
        this.tren = tren;
        this.ruta = ruta;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.estadoTrayecto = "pendiente";
    }

    public String getIdViaje() {
        return idViaje;
    }

    public Tren getTren() {
        return tren;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    public String getEstadoTrayecto() {
        return estadoTrayecto;
    }

    public void setIdViaje(String idViaje) {
        this.idViaje = idViaje;
    }

    public void setTren(Tren tren) {
        this.tren = tren;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void setFechaLlegada(LocalDateTime fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public double getValorBase() {
        return valorBase;
    }

    //Actualizar el estado del viaje
    public void setEstadoTrayecto(String estadoTrayecto) {
        this.estadoTrayecto = estadoTrayecto;
        System.out.println("Estado del viaje actualizado a: " + estadoTrayecto);
    }


}
