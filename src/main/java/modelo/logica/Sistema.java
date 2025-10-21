package modelo.logica;

import modelo.entidades.Usuario;

public class Sistema {
    private final GestorBoletos gestorBoletos;
    private final GestorIds gestorIds;
    private final GestorRuta gestorRuta;
    private final GestorTrenes gestorTrenes;
    private final GestorUsuarios gestorUsuarios;
    private final GestorViajes gestorViajes;
    private Usuario sesionActual;

    //CONSTRUCTOR
    public Sistema() {
        //INICIALIZAR OBJETOS
        gestorBoletos = new GestorBoletos();
        gestorIds = new GestorIds();
        gestorRuta = new GestorRuta();
        gestorTrenes = new GestorTrenes();
        gestorUsuarios = new GestorUsuarios();
        gestorViajes = new GestorViajes();
        sesionActual = null;

        //CARGAR
        gestorBoletos.cargar();
        gestorIds.cargar();
        gestorRuta.cargar();
        gestorTrenes.cargar();
        gestorUsuarios.cargar();
        gestorViajes.cargar();
    }

    //GETTERS
    public GestorBoletos getGestorBoletos() {
        return gestorBoletos;
    }

    public GestorIds getGestorIds() {
        return gestorIds;
    }

    public GestorRuta getGestorRuta() {
        return gestorRuta;
    }

    public GestorTrenes getGestorTrenes() {
        return gestorTrenes;
    }

    public GestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }

    public GestorViajes getGestorViajes() {
        return gestorViajes;
    }

    public Usuario getSesionActual() {
        return sesionActual;
    }

    //SET SESION
    public void setSesionActual(Usuario sesionActual) {
        this.sesionActual = sesionActual;
    }
}
