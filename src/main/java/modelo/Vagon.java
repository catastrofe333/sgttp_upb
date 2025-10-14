package modelo;

public abstract class Vagon {
    private final String idVagon;

    //Constructor
    public Vagon(String idVagon) {
        this.idVagon = idVagon;
    }

    //Getters y setters
    public String getIdVagon() {
        return idVagon;
    }

    //Mostrar informacion
    public abstract void mostrarInformacion();

}
