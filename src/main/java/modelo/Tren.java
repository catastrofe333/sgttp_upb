package modelo;

public class Tren {
    private final String idTren;
    private TipoTren tipo;
    private double kilometraje;
    private ListaEnlazada<Vagon> vagones;

    //CONSTRUCTOR
    public Tren(String idTren, TipoTren tipo, double kilometraje) {
        this.idTren = idTren;
        this.tipo = tipo;
        this.kilometraje = kilometraje;
        this.vagones = new ListaEnlazada<>();
    }

    //GETTERS Y SETTERS
    public String getIdTren() {
        return idTren;
    }

    public TipoTren getTipo() {
        return tipo;
    }

    public void setTipo(TipoTren tipo) {
        this.tipo = tipo;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public ListaEnlazada<Vagon> getVagones() {
        return vagones;
    }

    //METODOS
    //Agregar un vagon al tren
    public boolean agregarVagon(Vagon vagon) {
        if (vagones.getTamano() >= tipo.getCapacidadMaxVagones()) {
            return false;
        }

        //Restriccion: 1 vagon de equipaje por cada 2 de pasajeros
        int pasajeros = contarPorTipo(TipoVagon.PASAJEROS);
        int equipaje = contarPorTipo(TipoVagon.EQUIPAJE);

        if (vagon.getTipo() == TipoVagon.EQUIPAJE && equipaje >= pasajeros / 2) {
            return false;
        }

        vagones.agregar(vagon);
        return true;
    }

    //Contar cuantos vagones hay de cada tipo
    public int contarPorTipo(TipoVagon tipo) {
        int contador = 0;
        Nodo<Vagon> actual = vagones.getCabeza();

        while (actual != null) {
            if (actual.getDato().getTipo() == tipo) {
                contador++;
            }
            actual = actual.getSiguiente();
        }
        return contador;
    }

    //Mostrar todos los vagones
    public String mostrarVagones() {
        if (vagones.vacio()) {
            return "El tren no tiene vagones";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Vagones del tren ").append(idTren).append(":\n");

        Nodo<Vagon> actual = vagones.getCabeza();
        while (actual != null) {
            sb.append(" - ").append(actual.getDato().toString()).append("\n");
            actual = actual.getSiguiente();
        }

        return sb.toString();
    }

    //Mostrar informacion del tren
    public String toString() {
        return "ID Tren: " + idTren + "\n" +
                "Tipo: " + tipo + "\n" +
                "Kilometraje: " + kilometraje + "\n" +
                "Cantidad total de vagones: " + vagones.getTamano() + "\n" +
                " - Vagones de pasajeros: " + contarPorTipo(TipoVagon.PASAJEROS) + "\n" +
                " - Vagones de carga: " + contarPorTipo(TipoVagon.EQUIPAJE) + "\n" +
                mostrarVagones();
    }
}
