package modelo.entidades;

public abstract class Vagon {
    private final String idVagon;

    //CONSTRUCTOR
    public Vagon(String idVagon) {
        this.idVagon = idVagon;
    }

    //GETTERS Y SETTERS
    public String getIdVagon() {
        return idVagon;
    }
}
