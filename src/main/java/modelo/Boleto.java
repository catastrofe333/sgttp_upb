package modelo;
import java.time.LocalDateTime;

public class Boleto {
    private final String idBoleto;
    private LocalDateTime fechaCompra;
    private TipoIdPasajero tipoIdPasajero;
    private String idPasajero;
    private String nombrePasajero;
    private String direccionPasajero;
    private String telefonoPasajero;
    private String nombreContactoPasajero;
    private String telefonoContactoPasajero;
    private Viaje viaje;
    private CategoriaBoleto categoria;
    private double valorBoleto;
    private ListaEnlazada<Equipaje> equipajes;
    private boolean validado;

    //CONSTRUCTOR
    public Boleto(String idBoleto, TipoIdPasajero tipoIdPasajero, String idPasajero, String nombrePasajero, String direccionPasajero, String telefonoPasajero, String nombreContactoPasajero, String telefonoContactoPasajero, Viaje viaje, CategoriaBoleto categoria) {
        this.idBoleto = idBoleto;
        this.tipoIdPasajero = tipoIdPasajero;
        this.idPasajero = idPasajero;
        this.nombrePasajero = nombrePasajero;
        this.direccionPasajero = direccionPasajero;
        this.telefonoPasajero = telefonoPasajero;
        this.nombreContactoPasajero = nombreContactoPasajero;
        this.telefonoContactoPasajero = telefonoContactoPasajero;
        this.viaje = viaje;
        this.categoria = categoria;
        this.equipajes = new ListaEnlazada<>();
        this.validado = false;
        this.valorBoleto = calcularValorBoleto();
        this.fechaCompra = LocalDateTime.now();
    }

    //GETTERS Y SETTERS
    public String getIdBoleto() {
        return idBoleto;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public TipoIdPasajero getTipoIdPasajero() {
        return tipoIdPasajero;
    }

    public String getIdPasajero() {
        return idPasajero;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public String getDireccionPasajero() {
        return direccionPasajero;
    }

    public String getTelefonoPasajero() {
        return telefonoPasajero;
    }

    public String getNombreContactoPasajero() {
        return nombreContactoPasajero;
    }

    public String getTelefonoContactoPasajero() {
        return telefonoContactoPasajero;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public CategoriaBoleto getCategoria() {
        return categoria;
    }

    public double getValorBoleto() {
        return valorBoleto;
    }

    public ListaEnlazada<Equipaje> getEquipajes() {
        return equipajes;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    //METODOS
    //Calcular valorBoleto segun la categoria
    private double calcularValorBoleto(){
        return viaje.getValorBase() * categoria.getIncrementoPrecio();
    }

    //Agregar equipajes (2 max)
    public boolean agregarEquipaje(Equipaje equipaje){
        if(equipajes.getTamano() >= 2){
            return false;
        } else {
            equipajes.agregar(equipaje);
            return true;
        }
    }

    //Mostrar equipajes del boleto
    public String mostrarEquipajes() {
        if (equipajes.vacio()) {
            return "No hay equipajes registrados para este boleto";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Equipajes del boleto ").append(idBoleto).append(":\n");

        Nodo<Equipaje> actual = equipajes.getCabeza();
        while (actual != null) {
            sb.append(" - ").append(actual.getDato().toString()).append("\n");
            actual = actual.getSiguiente();
        }

        return sb.toString();
    }

    //Mostrar informacion
    @Override
    public String toString() {
        return "ID Boleto: " + idBoleto + "\n" +
                "Fecha de compra: " + fechaCompra + "\n" +
                "Pasajero: " + nombrePasajero + " - " + tipoIdPasajero + " - " + idPasajero + "\n" +
                "Direccion: " + direccionPasajero + "\n" +
                "Teléfono: " + telefonoPasajero + "\n" +
                "Contacto de emergencia: " + nombreContactoPasajero + " - " + telefonoContactoPasajero + "\n" +
                "Categoria: " + categoria + "\n" +
                "Valor del boleto: $" + valorBoleto + "\n" +
                "Estado de validacion: " + (validado ? "Validado" : "No validado") + "\n" +
                "Cantidad de equipajes: " + equipajes.getTamano() + "\n" +
                "Equipajes:\n" + mostrarEquipajes();
    }
}
