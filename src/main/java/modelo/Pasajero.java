package modelo;

import java.util.ArrayList;
import java.util.List;

public class Pasajero {
    private String idPasajero;
    private String nombres;
    private String apellidos;
    private String tipoIdentificacion;
    private String direccionActual;
    private String numeroTelefono;
    private String nombreContacto;
    private String apellidoContacto;
    private String telefonoContacto;
    private ListaEnlazada<Boleto> boletos;

    public Pasajero(String idPasajero, String nombres, String apellidos, String tipoIdentificacion, String direccionActual, String numeroTelefono, String nombreContacto, String apellidoContacto, String telefonoContacto) {
        this.idPasajero = idPasajero;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoIdentificacion = tipoIdentificacion;
        this.direccionActual = direccionActual;
        this.numeroTelefono = numeroTelefono;
        this.nombreContacto = nombreContacto;
        this.apellidoContacto = apellidoContacto;
        this.telefonoContacto = telefonoContacto;
        this.boletos = new ListaEnlazada<>();
    }

    public String getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(String idPasajero) {
        this.idPasajero = idPasajero;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getDireccionActual() {
        return direccionActual;
    }

    public void setDireccionActual(String direccionActual) {
        this.direccionActual = direccionActual;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getApellidoContacto() {
        return apellidoContacto;
    }

    public void setApellidoContacto(String apellidoContacto) {
        this.apellidoContacto = apellidoContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public ListaEnlazada<Boleto> getBoletos() {
        return boletos;
    }

    //Agregar boletos
    public void agregarBoleto(Boleto boleto) {
        boletos.agregar(boleto);
        System.out.println("Boleto" + boleto.getIdRegistro() + " añadido");
    }


}
