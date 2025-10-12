package modelo;

public class Tren {
    private String idTren;
    private TipoTren tipo;
    private double kilometraje;
    private ListaEnlazada<VagonPasajeros> vagonesPasajeros;
    private ListaEnlazada<VagonCarga> vagonesCarga;


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

    public ListaEnlazada<VagonPasajeros> getVagonesPasajeros() {
        return vagonesPasajeros;
    }

    public ListaEnlazada<VagonCarga> getVagonesCarga() {
        return vagonesCarga;
    }

    //METODOS

    //Agregar vagonPasajero
    public boolean agregarVagonPasajeros(VagonPasajeros vagonPasajeros){
        if(vagonesCarga.getTamano() + vagonesPasajeros.getTamano() >= tipo.getCapacidadMaxVagones()){
            return false;
        }
        vagonesPasajeros.agregar(vagonPasajeros);
        return true;
    }

    //Agregar vagonCarga
    public boolean agregarVagonCarga(VagonCarga vagonCarga){
        if(vagonesCarga.getTamano() + vagonesPasajeros.getTamano() >= tipo.getCapacidadMaxVagones()){
            return false;
        }
        if(vagonesCarga.getTamano() >= vagonesPasajeros.getTamano() / 2){
            return false;
        }
        vagonesCarga.agregar(vagonCarga);
        return true;
    }

   //Buscar por ID Pasajero
    public VagonPasajeros buscarVagonPasajeros(String idVagon){
        if(vagonesPasajeros.vacio()){
            return null;
        }
        Nodo<VagonPasajeros> actual = vagonesPasajeros.getCabeza();
        while (actual != null) {
            if (actual.getDato().getIdVagon().equals(idVagon)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    //Buscar por ID Carga
    public VagonCarga buscarVagonCarga(String idVagon){
        if(vagonesCarga.vacio()){
            return null;
        }
        Nodo<VagonCarga> actual = vagonesCarga.getCabeza();
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
        VagonPasajeros vagonEliminar = buscarVagonPasajeros(idVagon);
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
        VagonCarga vagonEliminar = buscarVagonCarga(idVagon);
        if(vagonEliminar == null){
            return false;
        }
        return vagonesCarga.borrar(vagonEliminar);
    }

    //Mostrar vagones de pasajeros
    public String mostrarVagonesPasajeros(){
        if(vagonesPasajeros.vacio()){
            return "";
        }

        Nodo<VagonPasajeros> actual = vagonesPasajeros.getCabeza();
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
            return "";
        }

        Nodo<VagonCarga> actual = vagonesCarga.getCabeza();
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
