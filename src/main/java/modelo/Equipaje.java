package modelo;

public class Equipaje {
    private String idEquipaje;
    private int peso;
    private String idVagonCarga;
    private String estado;
    private Pasajero pasajero;

    public Equipaje(String idEquipaje, int peso, String idVagonCarga, String estado, Pasajero pasajero) {
        this.idEquipaje = idEquipaje;
        this.peso = peso;
        this.idVagonCarga = idVagonCarga;
        this.estado = estado;
        this.pasajero = pasajero;
    }

    public String getIdEquipaje() {
        return idEquipaje;
    }

    public void setIdEquipaje(String idEquipaje) {
        this.idEquipaje = idEquipaje;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        if (peso > 90) {
            System.out.println("No se puede asignar un peso mayor a 90 kg");
        } else {
            this.peso = peso;
        }
    }

    public String getIdVagonCarga() {
        return idVagonCarga;
    }

    public void setIdVagonCarga(String idVagonCarga) {
        this.idVagonCarga = idVagonCarga;
    }

    public String getEstado() {
        return estado;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    //Actualizar estado del equipaje
    public void actualizarEstado(String nuevoEstado){
        this.estado = nuevoEstado;
        System.out.println("El equipaje fue actualizado a: " + nuevoEstado);
    }




}
