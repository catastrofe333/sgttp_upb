package modelo.entidades;

import modelo.enums.CategoriaBoleto;
import modelo.enums.EstadoTrayecto;
import java.util.Date;

public class Viaje {
    
    //DATOS VIAJE
    private final String idViaje;
    private EstadoTrayecto estado;
    private Date fechaSalida;
    private Date fechaLlegada;
    
    //IDS CLAVES PARA CONSULTAR GestorRutas
    private String idRuta;

    //INVENTARIO DE ASIENTOS
    private int disponiblesPremium;
    private int disponiblesEjecutiva;
    private int disponiblesEstandar;

    //ATRIBUTO PARA SABER SI ESTA VISIBLE
    private boolean visible;

    //CONSTRUCTOR
    public Viaje(String idViaje, Date fechaSalida, Date fechaLlegada, String idRuta,
                 int disponiblesPremium, int disponiblesEjecutiva, int disponiblesEstandar) {
        this.idViaje = idViaje;
        this.idRuta = idRuta;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.estado = EstadoTrayecto.PENDIENTE;
        this.disponiblesPremium = disponiblesPremium;
        this.disponiblesEjecutiva = disponiblesEjecutiva;
        this.disponiblesEstandar = disponiblesEstandar;
        this.visible = false;
    }

    //GETTER Y SETTERS
    public String getIdViaje() {
        return idViaje;
    }

    public EstadoTrayecto getEstado() {
        return estado;
    }

    public void setEstado(EstadoTrayecto estado) {
        this.estado = estado;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    public int getDisponiblesPremium() {
        return disponiblesPremium;
    }

    public int getDisponiblesEjecutiva() {
        return disponiblesEjecutiva;
    }

    public int getDisponiblesEstandar() {
        return disponiblesEstandar;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    //METODOS
    //Verificar si hay asientos disponibles
    public boolean hayAsientosDisponibles(CategoriaBoleto categoria) {
        if (categoria == CategoriaBoleto.PREMIUM) return disponiblesPremium > 0;
        if (categoria == CategoriaBoleto.EJECUTIVA) return disponiblesEjecutiva > 0;
        if (categoria == CategoriaBoleto.ESTANDAR) return disponiblesEstandar > 0;
        return false;
    }

    //Descontar disponibilidad de los asientos
    public boolean descontarAsiento(CategoriaBoleto categoria) {
        if (hayAsientosDisponibles(categoria)) {
            if (categoria == CategoriaBoleto.PREMIUM) {
                disponiblesPremium--;
            } else if (categoria == CategoriaBoleto.EJECUTIVA) {
                disponiblesEjecutiva--;
            } else if (categoria == CategoriaBoleto.ESTANDAR) {
                disponiblesEstandar--;
            }
            return true;
        }
        return false;
    }

    //Mostrar informacion del viaje
    @Override
    public String toString() {
        return "ID Viaje: " + idViaje + "\n" +
                "Ruta asignada: " + idRuta +
                "Asientos disponibles PREMIUM: " + disponiblesPremium +
                "Asientos disponibles EJECUTIVA: " + disponiblesEjecutiva +
                "Asientos disponibles ESTANDAR: " + disponiblesEstandar +
                "Salida: " + fechaSalida + "\n" +
                "Llegada: " + fechaLlegada + "\n" +
                "Estado: " + estado + "\n";
    }
}
