package modelo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Boleto {
    private String idRegistro;
    private LocalDateTime fechaCompra;
    private Pasajero pasajero; //revisar
    private String categoria;
    private Viaje viaje;
    private int valorPasaje;
    private ListaEnlazada<Equipaje> equipajes;

    public Boleto(String idRegistro, LocalDateTime fechaCompra, Pasajero pasajero, String categoria, Viaje viaje, int valorPasaje) {
        this.idRegistro = idRegistro;
        this.fechaCompra = fechaCompra;
        this.pasajero = pasajero;
        this.categoria = categoria;
        this.viaje = viaje;
        this.valorPasaje = valorPasaje;
        this.equipajes = new ListaEnlazada<>();
    }

    public String getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public int getValorPasaje() {
        return valorPasaje;
    }

    public void setValorPasaje(int valorPasaje) {
        this.valorPasaje = valorPasaje;
    }

    public ListaEnlazada<Equipaje> getEquipajes() {
        return equipajes;
    }

    //Agregar equipajes (2 max)
    public void agregarEquipaje(Equipaje equipaje){
        if(equipajes.tamano() >= 2){
            System.out.println("Se ha alcanzado el maximo de equipaje");
        } else {
            equipajes.agregar(equipaje);
            System.out.println("Se añadio exitosamente");
        }
    }
}
