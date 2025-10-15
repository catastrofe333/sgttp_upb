package modelo;

public class Vagon {
    private final String idVagon;
    private TipoVagon tipo;

    //Constructor
    public Vagon(String idVagon, TipoVagon tipo) {
        this.idVagon = idVagon;
        this.tipo = tipo;
    }

    //Getters y setters
    public String getIdVagon() {
        return idVagon;
    }

    public TipoVagon getTipo() {
        return tipo;
    }

    public void setTipo(TipoVagon tipo) {
        this.tipo = tipo;
    }

    //Mostrar informacion
    @Override
    public String toString() {
        return "ID: " + idVagon + " | Tipo: " + tipo;
    }

}
