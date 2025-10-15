package modelo;

import java.time.LocalDateTime;

public class Viaje {
    private String idViaje;
    private Tren tren;
    private Ruta ruta;
    private double valorBase;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;
    private EstadoTrayecto estado;
    private ListaEnlazada<Boleto> boletos;
    private int disponiblePremium;
    private int disponibleEjecutivo;
    private int disponibleEstandar;



    public Viaje(String idViaje, Tren tren, Ruta ruta, LocalDateTime fechaSalida, LocalDateTime fechaLlegada) {
        this.idViaje = idViaje;
        this.tren = tren;
        this.ruta = ruta;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.estado = EstadoTrayecto.PENDIENTE;
        this.boletos= new ListaEnlazada<>();
        this.disponiblePremium= getTren().totalVagonesPasajeros()*CategoriaBoleto.PREMIUM.getLugaresPorVagon();
        this.disponibleEjecutivo= getTren().totalVagonesPasajeros()*CategoriaBoleto.EJECUTIVA.getLugaresPorVagon();
        this.disponibleEstandar= getTren().totalVagonesPasajeros() *CategoriaBoleto.ESTANDAR.getLugaresPorVagon();
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

    public EstadoTrayecto getEstado() {
        return estado;
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

    public boolean setViajando (){
        if(estado== EstadoTrayecto.PENDIENTE){
            this.estado=EstadoTrayecto.VIAJANDO;
            return true;
        }
        return false;
    }
    public boolean setFinalizado (){
        if(estado== EstadoTrayecto.VIAJANDO){
            this.estado=EstadoTrayecto.FINALIZADO;
            return true;
        }
        return false;
    }

    public void agregarboleto(Boleto boleto) {
        boletos.agregar(boleto);
        if (boleto.getCategoria() == CategoriaBoleto.PREMIUM) {
            disponiblePremium--;
        } else if (boleto.getCategoria() == CategoriaBoleto.EJECUTIVA) {
            disponibleEjecutivo--;
        } else if(boleto.getCategoria()==CategoriaBoleto.ESTANDAR){
            disponibleEstandar --;
        }
    }
}
