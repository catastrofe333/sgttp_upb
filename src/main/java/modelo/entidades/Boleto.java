package modelo.entidades;

import modelo.enums.CategoriaBoleto;
import modelo.enums.TipoId;
import java.util.Date;

public class Boleto {

    //DATOS BOLETO
    private final String idBoleto;
    private final Date fechaHoraCompra;
    private final CategoriaBoleto categoria;
    private final int asiento;
    private final double valorBoleto;
    private boolean validado;

    //DATOS VIAJE
    private final String idViaje;

    //DATOS PASAJERO
    private final TipoId tipoId;
    private final String idPasajero;
    private final String nombrePasajero;
    private final String apellidoPasajero;
    private final String direccionPasajero;
    private final String telefonoPasajero;
    private final String nombreContactoPasajero;
    private final String apellidoContactoPasajero;
    private final String telefonoContactoPasajero;

    //DATOS EQUIPAJE
    private final Equipaje[] equipajes;
    private int numEquipajes;
    private final int maxEquipaje = 2;

    //CONSTRUCTOR
    public Boleto(String idBoleto, CategoriaBoleto categoria, int asiento, double valorBoleto, String idViaje,
                  TipoId tipoId, String idPasajero, String nombrePasajero,
                  String apellidoPasajero, String direccionPasajero, String telefonoPasajero,
                  String nombreContactoPasajero, String apellidoContactoPasajero, String telefonoContactoPasajero) {

        this.idBoleto = idBoleto;
        this.fechaHoraCompra = new Date();
        this.categoria = categoria;
        this.asiento = asiento;
        this.valorBoleto = valorBoleto;
        this.validado = false;
        this.idViaje = idViaje;
        this.tipoId = tipoId;
        this.idPasajero = idPasajero;
        this.nombrePasajero = nombrePasajero;
        this.apellidoPasajero = apellidoPasajero;
        this.direccionPasajero = direccionPasajero;
        this.telefonoPasajero = telefonoPasajero;
        this.nombreContactoPasajero = nombreContactoPasajero;
        this.apellidoContactoPasajero = apellidoContactoPasajero;
        this.telefonoContactoPasajero = telefonoContactoPasajero;
        this.equipajes = new Equipaje[maxEquipaje];
        this.numEquipajes = 0;
    }

    //GETTERS Y SETTERS
    public String getIdBoleto() {
        return idBoleto;
    }

    public Date getFechaHoraCompra() {
        return fechaHoraCompra;
    }

    public CategoriaBoleto getCategoria() {
        return categoria;
    }

    public int getAsiento() {
        return asiento;
    }

    public double getValorBoleto() {
        return valorBoleto;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public String getIdViaje() {
        return idViaje;
    }

    public TipoId getTipoIdPasajero() {
        return tipoId;
    }

    public String getIdPasajero() {
        return idPasajero;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public String getApellidoPasajero() {
        return apellidoPasajero;
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

    public String getApellidoContactoPasajero() {
        return apellidoContactoPasajero;
    }

    public String getTelefonoContactoPasajero() {
        return telefonoContactoPasajero;
    }

    public Equipaje[] getEquipajes() {
        return equipajes;
    }

    public int getNumEquipajes() {
        return numEquipajes;
    }

    public int getMaxEquipaje() {
        return maxEquipaje;
    }

    //METODOS
    //METODOS GESTION DE EQUIPAJE
    public boolean agregarEquipaje(Equipaje equipaje) {
        if (equipaje.getPeso() > 90) {
            return false;
        }
        if (numEquipajes < maxEquipaje) {
            equipajes[numEquipajes] = equipaje;
            numEquipajes++;
            return true;
        }
        return false;
    }

    public String mostrarEquipajes() {
        if (numEquipajes == 0) {
            return "No hay equipajes registrados para este boleto";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Equipajes del boleto ").append(idBoleto).append(":\n");

        for(int i = 0; i < numEquipajes; i++){
            sb.append(" - ").append(equipajes[i].toString()).append("\n");
        }

        return sb.toString();
    }

    //METODO TOSTRING
    @Override
    public String toString() {
        return "ID Boleto: " + idBoleto + "\n" +
                "Fecha de compra: " + fechaHoraCompra + "\n" +
                "Valor del boleto: $" + valorBoleto + "\n" +
                "Categoria: " + categoria + "\n" +
                "Estado de validacion: " + (validado ? "Validado" : "No validado") + "\n" +
                "Asiento: " + asiento + "\n" +
                "ID Viaje: " + idViaje + "\n" +
                "Pasajero: " + nombrePasajero + " " + apellidoPasajero + " - " + tipoId + " - " + idPasajero + "\n" +
                "Direccion: " + direccionPasajero + "\n" +
                "Teléfono: " + telefonoPasajero + "\n" +
                "Contacto de emergencia: " + nombreContactoPasajero + " " + apellidoContactoPasajero + " - " + telefonoContactoPasajero + "\n" +
                "Cantidad de equipajes: " + numEquipajes + "\n" +
                "Equipajes:\n" + mostrarEquipajes();
    }
}
