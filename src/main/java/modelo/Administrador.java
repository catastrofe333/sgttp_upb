package modelo;

public class Administrador extends Usuario {

    private SistemaTrenes trenes;

    public Administrador(String idUsuario, String nombre, String contrasena, String cargo) {
        super(idUsuario, nombre, contrasena, cargo);
        trenes = new SistemaTrenes();
    }

    //Metodos de GESTION DE TRENES

    public boolean agregarTren(Tren tren){
        trenes.agregarTren(tren);
        return true;
    }

    public boolean eliminarTren(String idTren){
        trenes.eliminarTren(idTren);
        return false;
    }

    //Metodos de GESTION DE RUTAS(VIAJES)

    public void crearRuta(Ruta ru){

    }

}
