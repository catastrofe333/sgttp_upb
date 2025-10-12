package modelo;

public class Tren {
    private String idTren;
    private TipoTren tipo;
    private double kilometraje;
    private ListaEnlazada<Vagon> vagonesPasajeros;
    private ListaEnlazada<Vagon> vagonesCarga;


    //Constructor
    public Tren(String idTren, TipoTren tipo, double kilometraje) {
        this.idTren = idTren;
        this.tipo = tipo;
        this.kilometraje = kilometraje;
        this.vagonesPasajeros = new ListaEnlazada<>();
        this.vagonesCarga = new ListaEnlazada<>();
    }

    //Getters y setters
    public String getIdTren() {
        return idTren;
    }

    public void setIdTren(String idTren) {
        this.idTren = idTren;
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

    public ListaEnlazada<Vagon> getVagonesPasajeros() {
        return vagonesPasajeros;
    }

    public ListaEnlazada<Vagon> getVagonesCarga() {
        return vagonesCarga;
    }

    //METODOS

    //Agregar vagonPasajero
    public boolean agregarVagonPasajeros(Vagon vagon){
        if(vagon.getTipo() != TipoVagon.PASAJEROS){
            return false;
        }
        if(vagonesCarga.getTamano() + vagonesPasajeros.getTamano() >= tipo.getCapacidadMaxVagones()){
            return false;
        }
        vagonesPasajeros.agregar(vagon);
        return true;
    }

    //Agregar vagonCarga
    public boolean agregarVagonCarga(Vagon vagon){
        if(vagon.getTipo() != TipoVagon.EQUIPAJE){
            return false;
        }
        if(vagonesCarga.getTamano() + vagonesPasajeros.getTamano() >= tipo.getCapacidadMaxVagones()){
            return false;
        }
        if(vagonesCarga.getTamano() >= vagonesPasajeros.getTamano() / 2){
            return false;
        }
        vagonesCarga.agregar(vagon);
        return true;
    }

   //Buscar por ID Pasajero
    public Vagon buscarVagonPasajeros(String idVagon){
        if(vagonesPasajeros.vacio()){
            return null;
        }
        Nodo<Vagon> actual = vagonesPasajeros.getCabeza();
        while (actual != null) {
            if (actual.getDato().getIdVagon().equals(idVagon)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    //Buscar por ID Carga
    public Vagon buscarVagonCarga(String idVagon){
        if(vagonesCarga.vacio()){
            return null;
        }
        Nodo<Vagon> actual = vagonesCarga.getCabeza();
        while (actual != null) {
            if (actual.getDato().getIdVagon().equals(idVagon)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    //Eliminar vagonPasajero
    public boolean eliminarVagonPasajero(String idVagon){
        Vagon vagonEliminar = buscarVagonPasajeros(idVagon);
        if(vagonEliminar == null){
            return false;
        }
        return vagonesPasajeros.borrar(vagonEliminar);
    }

    //Eliminar vagonCarga
    public boolean eliminarVagonCarga(String idVagon){
        if(vagonesCarga.getTamano() <= vagonesPasajeros.getTamano() / 2){
            return false;
        }
        Vagon vagonEliminar = buscarVagonCarga(idVagon);
        if(vagonEliminar == null){
            return false;
        }
        return vagonesCarga.borrar(vagonEliminar);
    }

    //Mostrar vagones de pasajeros
    public String mostrarVagonesPasajeros(){
        if(vagonesPasajeros.vacio()){
            return null;
        }

        Nodo<Vagon> actual = vagonesPasajeros.getCabeza();
        StringBuilder resultado = new StringBuilder();
        while(actual!=null){
            resultado.append(actual.getDato().getIdVagon()).append(" - ");
            actual = actual.getSiguiente();
        }
        return resultado.toString();
    }

    //Mostrar vagones de pasajeros
    public String mostrarVagonesCarga(){
        if(vagonesCarga.vacio()){
            return null;
        }

        Nodo<Vagon> actual = vagonesCarga.getCabeza();
        StringBuilder resultado = new StringBuilder();
        while(actual!=null){
            resultado.append(actual.getDato().getIdVagon()).append(" - ");
            actual = actual.getSiguiente();
        }
        return resultado.toString();
    }

    //Mostrar total vagonesPasajeros
    public int totalVagonesPasajeros(){
        return vagonesPasajeros.getTamano();
    }

    //Mostrar total vagonesCarga
    public int totalVagonesCarga(){
        return vagonesCarga.getTamano();
    }

    //Mostrar total vagones
    public int totalVagones(){
        return vagonesCarga.getTamano() + vagonesPasajeros.getTamano();
    }

    //Mostrar tren
    public void mostrarTren(){
        System.out.println("ID del Tren: " + idTren);
        System.out.println("TipoTren: " + tipo);
        System.out.println("Kilometraje: " + kilometraje);
        System.out.println("Cantidad de vagones para pasajeros: " + totalVagonesPasajeros());
        System.out.println("Vagones para pasajeros: " + mostrarVagonesPasajeros());
        System.out.println("Cantidad de vagones para carga: " + totalVagonesCarga());
        System.out.println("Vagones para carga: " + mostrarVagonesCarga());
    }
}
