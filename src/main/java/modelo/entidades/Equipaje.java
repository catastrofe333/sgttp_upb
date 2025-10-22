package modelo.entidades;

import modelo.enums.EstadoEquipaje;

public class Equipaje {
    private final String idEquipaje;
    private final static double maxPeso = 90.0;
    private final double peso;
    private final String idVagonCarga;
    private EstadoEquipaje estado;

    //CONSTRUCTOR
    public Equipaje(String idEquipaje, double peso, String idVagonCarga) {
        this.idEquipaje = idEquipaje;
        this.peso = peso;
        this.idVagonCarga = idVagonCarga;
        this.estado = EstadoEquipaje.REGISTRADO;
    }

    //GETTERS Y SETTERS
    public String getIdEquipaje() {
        return idEquipaje;
    }

    public double getPeso() {
        return peso;
    }

    public String getIdVagonCarga() {
        return idVagonCarga;
    }

    public EstadoEquipaje getEstado() {
        return estado;
    }

    public void setEstado(EstadoEquipaje estado) {
        this.estado = estado;
    }

    public static double getMaxPeso() {
        return maxPeso;
    }

    //Mostrar informacion
    @Override
    public String toString() {
        return "Equipaje ID: " + idEquipaje + "\n" +
                " - Peso: " + peso + "\n" +
                " - Vagón: " + idVagonCarga + "\n" +
                " - Estado: " + estado;
    }

}
