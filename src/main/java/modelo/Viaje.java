package modelo;

import java.time.LocalDateTime;

public class Viaje {
    private final String idViaje;
    private Tren tren;
    private Ruta ruta;
    private double valorBase;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;
    private EstadoTrayecto estado;
    private ListaEnlazada<Boleto> boletos;
    private int disponiblesPremium;
    private int disponiblesEjecutiva;
    private int disponiblesEstandar;

    //CONSTRUCTOR
    public Viaje(String idViaje, Tren tren, Ruta ruta, double valorBase, LocalDateTime fechaSalida, LocalDateTime fechaLlegada) {
        this.idViaje = idViaje;
        this.tren = tren;
        this.ruta = ruta;
        this.valorBase = valorBase;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.estado = EstadoTrayecto.PENDIENTE;
        this.boletos = new ListaEnlazada<>();

        // Calcular cupos según numero de vagones de pasajeros
        this.disponiblesPremium = tren.contarPorTipo(TipoVagon.PASAJEROS) * CategoriaBoleto.PREMIUM.getLugaresPorVagon();
        this.disponiblesEjecutiva = tren.contarPorTipo(TipoVagon.PASAJEROS) * CategoriaBoleto.EJECUTIVA.getLugaresPorVagon();
        this.disponiblesEstandar = tren.contarPorTipo(TipoVagon.PASAJEROS) * CategoriaBoleto.ESTANDAR.getLugaresPorVagon();
    }

    //GETTER Y SETTERS
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

    public double getValorBase() {
        return valorBase;
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

    public void setValorBase(double valorBase) { this.valorBase = valorBase; }

    //METODOS
    //Cambiar estado del trayecto
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

    // Registrar un boleto (no venderlo)
    public boolean registrarBoleto(Boleto boleto) {
        switch (boleto.getCategoria()) {
            case PREMIUM:
                if (disponiblesPremium <= 0) return false;
                disponiblesPremium--;
                break;
            case EJECUTIVA:
                if (disponiblesEjecutiva <= 0) return false;
                disponiblesEjecutiva--;
                break;
            case ESTANDAR:
                if (disponiblesEstandar <= 0) return false;
                disponiblesEstandar--;
                break;
        }
        boletos.agregar(boleto);
        return true;
    }

    // Mostrar disponibilidad
    public String mostrarDisponibilidad() {
        return "Disponibilidad actual:\n" +
                " - Premium: " + disponiblesPremium + " boletos\n" +
                " - Ejecutiva: " + disponiblesEjecutiva + " boletos\n" +
                " - Estandar: " + disponiblesEstandar + " boletos\n";
    }

    //Mostrar informacion del viaje
    //TODO: AGREGAR RUTA: ORIGINA Y DESTINO (AUN NO TENGO ESA CLASE)
    @Override
    public String toString() {
        return "ID Viaje: " + idViaje + "\n" +
                "Tren asignado: " + tren.getIdTren() + " (" + tren.getTipo() + ")\n" +
                "Salida: " + fechaSalida + "\n" +
                "Llegada: " + fechaLlegada + "\n" +
                "Estado: " + estado + "\n" +
                "Boletos registrados: " + boletos.getTamano() + "\n" +
                mostrarDisponibilidad();
    }

}
