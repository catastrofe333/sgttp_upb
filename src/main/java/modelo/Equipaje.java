package modelo;

public class Equipaje {
    private String idEquipaje;
    private int peso;
    private VagonCarga vagonCarga;
    private String estado;

    public Equipaje(String idEquipaje, int peso, VagonCarga vagonCarga, String estado) {
        this.idEquipaje = idEquipaje;
        this.peso = peso;
        this.vagonCarga = vagonCarga;
        this.estado = estado;
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

    public String getEstado() {
        return estado;
    }

    //Actualizar estado del equipaje
    public void actualizarEstado(String nuevoEstado){
        this.estado = nuevoEstado;
        System.out.println("El equipaje fue actualizado a: " + nuevoEstado);
    }

    //Obtener el idVagon de donde esta el equipaje
    public String getIdVagon(){
        return vagonCarga.getIdVagon();
    }

}
