package modelo;

public class Equipaje {
    private final String idEquipaje;
    private int peso;
    private Vagon vagonEquipaje;
    private EstadoEquipaje estado;

    //CONSTRUCTOR
    public Equipaje(String idEquipaje, int peso, Vagon vagon) {
        this.idEquipaje = idEquipaje;
        setPeso(peso);
        setVagon(vagon);
        this.estado = EstadoEquipaje.REGISTRADO;
    }

    //GETTERS Y SETTERS
    public String getIdEquipaje() {
        return idEquipaje;
    }

    public int getPeso() {
        return peso;
    }

    public EstadoEquipaje getEstado() { return estado; }

    public Vagon getVagon() { return vagonEquipaje;}

    //Asegurarse que el vagon sea de equipaje
    //TODO: HACER EN LA VISTA UN AVISO ANTES DE INGRESAR EL EQUIPAJE SOBRE LA RESTRICCION
    public void setVagon(Vagon vagon) {
        if (vagon != null && vagon.getTipo() == TipoVagon.EQUIPAJE) {
            this.vagonEquipaje = vagon;
        } else {
            this.vagonEquipaje = null;
        }
    }

    //Añadir el peso del equipaje segun la restriccion de los 90kg
    //TODO: HACER EN LA VISTA UN AVISO ANTES DE INGRESAR EL EQUIPAJE SOBRE LA RESTRICCION
    public void setPeso(int peso) {
        // Valida
        if (peso > 90) {
            this.peso = 0;
        } else {
            this.peso = peso;
        }
    }

    //METODOS
    //Finalizar estado del equipaje
    public void finalizarEstado(){
        this.estado = EstadoEquipaje.ENTREGADO;
    }

    //Obtener el idVagon de donde esta el equipaje
    public String getIdVagon() {
        return vagonEquipaje.getIdVagon();
    }

    //Mostrar informacion
    @Override
    public String toString() {
        return "Equipaje ID: " + idEquipaje + "\n" +
                " - Peso: " + peso + "\n" +
                " - Vagón: " + getIdVagon() + "\n" +
                " - Estado: " + estado;
    }

}
