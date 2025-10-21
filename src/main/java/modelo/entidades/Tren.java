package modelo.entidades;

import modelo.enums.CategoriaBoleto;
import modelo.enums.TipoTren;

import java.util.Date;

public class Tren {
    private final String idTren;
    private final TipoTren tipo;
    private double kilometraje;
    private VagonPasajeros[] vagonesPasajeros;
    private VagonCarga[] vagonesCarga;
    private int numVagonesPasajeros;
    private int numVagonesCarga;

    //CONSTRUCTOR
    public Tren(String idTren, TipoTren tipo) {
        this.idTren = idTren;
        this.tipo = tipo;
        this.kilometraje = 0;
        this.vagonesPasajeros = new VagonPasajeros[tipo.getCapacidadMaxVagonesPasajeros()];
        this.vagonesCarga = new VagonCarga[tipo.getCapacidadMaxVagonesCarga()];
        this.numVagonesPasajeros = 0;
        this.numVagonesCarga = 0;
    }

    //GETTERS Y SETTERS
    public String getIdTren() {
        return idTren;
    }

    public TipoTren getTipo() {
        return tipo;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public VagonPasajeros[] getVagonesPasajeros() {
        return vagonesPasajeros;
    }

    public VagonCarga[] getVagonesCarga() {
        return vagonesCarga;
    }

    public int getNumVagonesPasajeros() {
        return numVagonesPasajeros;
    }

    public int getNumVagonesCarga() {
        return numVagonesCarga;
    }

    //METODOS
    //Agregar un vagon de pasajeros al tren
    public boolean agregarVagonPasajeros(VagonPasajeros vagonPasajeros) {
        if(disponibleVagonPasajeros()){
            vagonesPasajeros[numVagonesPasajeros] = vagonPasajeros;
            numVagonesPasajeros++;
            return true;
        }
        return false;
    }

    //Agregar un vagon de carga al tren
    public boolean agregarVagonCarga(VagonCarga vagonCarga) {
        if(disponibleVagonCarga()){
            vagonesCarga[numVagonesCarga] = vagonCarga;
            numVagonesCarga++;
            return true;
        }
        return false;
    }

    //Eliminar un vagon de pasajeros del tren
    public boolean eliminarVagonPasajeros(String idVagonPasajeros){
        VagonPasajeros vagonAEliminar = buscarVagonPasajeros(idVagonPasajeros);
        if(vagonAEliminar == null){
            return false;
        }
        VagonPasajeros[] temporal = new VagonPasajeros[vagonesPasajeros.length - 1];
        int i = 0;
        for(VagonPasajeros vagonPasajeros : vagonesPasajeros){
            if(!vagonPasajeros.getIdVagon().equals(idVagonPasajeros)){
                temporal[i] = vagonPasajeros;
                i++;
            }
        }
        vagonesPasajeros = temporal;
        return true;
    }

    //Eliminar un vagon de carga del tren
    public boolean eliminarVagonCarga(String idVagonCarga){
        VagonCarga vagonAEliminar = buscarVagonCarga(idVagonCarga);
        if(vagonAEliminar == null){
            return false;
        }
        VagonCarga[] temporal = new VagonCarga[vagonesCarga.length - 1];
        int i = 0;
        for(VagonCarga vagonCarga : vagonesCarga){
            if(!vagonCarga.getIdVagon().equals(idVagonCarga)){
                temporal[i] = vagonCarga;
                i++;
            }
        }
        vagonesCarga = temporal;
        return true;
    }

    //Buscar si existe un vagon de pasajeros
    public VagonPasajeros buscarVagonPasajeros(String idVagonPasajeros) {
        for(VagonPasajeros vagonPasajeros : vagonesPasajeros){
            if(vagonPasajeros.getIdVagon().equals(idVagonPasajeros)){
                return vagonPasajeros;
            }
        }
        return null;
    }

    //Buscar si existe un vagon de carga
    public VagonCarga buscarVagonCarga(String idVagonCarga) {
        for(VagonCarga vagonCarga : vagonesCarga){
            if(vagonCarga.getIdVagon().equals(idVagonCarga)){
                return vagonCarga;
            }
        }
        return null;
    }

    //Obtener asientos
    public int obtenerAsiento(CategoriaBoleto categoria){
        for (int i = 0; i < numVagonesPasajeros; i++) {
            if(vagonesPasajeros[i].ocuparAsiento(categoria)){
                int posicionVagon = vagonesPasajeros[i].asignarAsientoVagon(categoria);
                int cantidadPasajerosVagon = 34;
                return (i * cantidadPasajerosVagon) + posicionVagon;
            }
        }
        return -1;
    }

    //Vaciar vagonesPasajeros para nuevo viaje
    public void vaciarPasajeros(){
        for (int i = 0; i < numVagonesPasajeros; i++) {
            vagonesPasajeros[i].vaciarAsientos();
        }
    }

    public boolean disponibleVagonPasajeros() {
        return numVagonesPasajeros < tipo.getCapacidadMaxVagonesPasajeros();
    }

    public boolean disponibleVagonCarga() {
        return numVagonesCarga < tipo.getCapacidadMaxVagonesCarga();
    }

    //Mostrar informacion del tren
    public String toString() {
        return "ID Tren: " + idTren + "\n" +
                "Tipo: " + tipo + "\n" +
                "Kilometraje: " + kilometraje + "\n" +
                "Cantidad total de vagones: " + (numVagonesPasajeros + numVagonesCarga) + "\n" +
                " - Vagones de pasajeros: " + numVagonesPasajeros + "\n" +
                " - Vagones de carga: " + numVagonesCarga + "\n";
    }
}
