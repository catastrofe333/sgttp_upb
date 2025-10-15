package modelo;

public class Administrador extends Usuario {

    public Administrador(String idUsuario, String nombre, String contrasena, String cargo) {
        super(idUsuario, nombre, contrasena, cargo);
    }

    //Metodos de GESTION DE TRENES

    public boolean agregarTren(Tren tren){
        return false;
    }

    public boolean eliminarTren(String idTren){
        return false;
    }

    //Metodos de GESTION DE RUTAS(VIAJES)

    public void crearRuta(Ruta ru){

    }



    @Override
    public void mostrarInformacion() {

    }
}
