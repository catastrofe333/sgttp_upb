package modelo;
import java.time.LocalDateTime;

public class Boleto {
    private String idBoleto;
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

    //Constructor
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

    //Getters
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

    public boolean isValidado() {
        return validado;
    }

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

    //Validar boleto
    public void validarBoleto(){
        this.validado = true;
    }

    //Mostrar boleto
    public void mostrarBoleto(){
        System.out.println("ID del Boleto: " + idBoleto);
        System.out.println("Fecha de compra: " + fechaCompra);
        System.out.println("Pasajero: " + nombrePasajero);
        System.out.println("Tipo de identificación: " + tipoIdPasajero + " - " + idPasajero);
        System.out.println("Dirección: " + direccionPasajero);
        System.out.println("Teléfono: " + telefonoPasajero);
        System.out.println("Contacto de emergencia: " + nombreContactoPasajero + " - " + telefonoContactoPasajero );
        System.out.println("Categoría: " + categoria);
        System.out.println("Valor del boleto: $" + valorBoleto);
        System.out.println("Validado: " + (validado ? "Sí" : "No"));
        System.out.println("Equipajes registrados: " + equipajes.getTamano());
    }
}
