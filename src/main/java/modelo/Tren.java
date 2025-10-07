package modelo;

import java.util.List;

public abstract class Tren {
    // TODO: agregar herencia y abstraccion y crear clases tipoTren
    private String idTren;
    private String nombreTren;
    private int kilometraje;
    private int capacidadCarga;
    private int capacidadPasajeros;
    private List<Vagon> vagones;

    public Tren(String idTren, String nombreTren, int kilometraje, int numVagones) {
        this.idTren = idTren;
        this.nombreTren = nombreTren;
        this.kilometraje = kilometraje;
        this.setCapacidadCarga(numVagones);
    }

    public abstract int getMaxCapacidadCarga();

    public int getCapacidadCarga() {
        return capacidadCarga;
    }

    public int getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public void setCapacidadCarga(int vagones){
        if(vagones >= 1 && vagones <= getMaxCapacidadCarga()){
            this.capacidadCarga = vagones;

        }
        else {
            System.out.println("Error");
        }
    }


}
