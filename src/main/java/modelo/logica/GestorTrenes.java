package modelo.logica;

import modelo.entidades.*;
import modelo.enums.CategoriaBoleto;
import modelo.persistencia.GestorArchivos;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

public class GestorTrenes {
    private static final String rutaArchivo = "trenes";
    private Tren[] trenes;

    //CONSTRUCTOR
    public GestorTrenes() {
    }

    //METODO PARA CARGAR LO QUE HAY EN EL JSON CADA QUE SE ABRA EL SISTEMA, LO HACE AL INICIAR EL SISTEMA
    public void cargar() {
        trenes = GestorArchivos.leerJSON(rutaArchivo, Tren[].class);
    }

    //METODO PARA MANTENERLO ACTUALIZADO
    private void guardar() {
        GestorArchivos.escribirJSON(rutaArchivo, trenes);
    }

    //METODO PARA ACTUALIZAR EL LOG
    private void guardarLog(String log) {
        GestorArchivos.escribirTxt("log", log);
    }

    //GETTERS
    public Tren[] getTrenes() {
        return trenes;
    }

    //METODOS DE LOS TRENES
    public void agregarTren(Tren tren, Usuario usuario){
        Tren[] temporal = Arrays.copyOf(trenes, trenes.length + 1);
        temporal[trenes.length] = tren;
        trenes = temporal;
        guardar();
        guardarLog(new Date() + " AGREGAR: " + tren.getIdTren() + " USUARIO: " + usuario.getUsuario());
    }

    public Tren buscarTren(String idTren) {
        for(Tren tren : trenes){
            if(tren.getIdTren().equals(idTren)){
                return tren;
            }
        }
        return null;
    }

    public boolean eliminarTren(String idTren, Usuario usuario){
        Tren trenAEliminar = buscarTren(idTren);
        if(trenAEliminar == null){
            return false;
        }
        Tren[] temporal = new Tren[trenes.length - 1];
        int i = 0;
        for(Tren tren : trenes){
            if(!tren.getIdTren().equals(idTren)){
                temporal[i] = tren;
                i++;
            }
        }
        trenes = temporal;
        guardar();
        guardarLog(new Date() + " ELIMINAR: " + idTren + " USUARIO: " + usuario.getUsuario());
        return true;
    }

    public boolean agregarVagonPasajeros(String idTren, String idVagon, Usuario usuario){
        Tren tren = buscarTren(idTren);
        if(tren == null){
            return false;
        }
        VagonPasajeros vagon = new VagonPasajeros(idVagon);
        boolean agregado = tren.agregarVagonPasajeros(vagon);
        if(agregado){
            guardar();
            guardarLog(new Date() + " AGREGAR EN: " + idTren + " VAGON PASAJEROS: " + idVagon + " USUARIO: " + usuario.getUsuario());
        }
        return agregado;
    }

    public boolean agregarVagonCarga(String idTren, String idVagon, Usuario usuario){
        Tren tren = buscarTren(idTren);
        if(tren == null){
            return false;
        }
        VagonCarga vagon = new VagonCarga(idVagon);
        boolean agregado = tren.agregarVagonCarga(vagon);
        if(agregado){
            guardar();
            guardarLog(new Date() + " AGREGAR EN: " + idTren + " VAGON CARGA: " + idVagon + " USUARIO: " + usuario.getUsuario());
        }
        return agregado;
    }

    public boolean eliminarVagonPasajeros(String idTren, String idVagon, Usuario usuario){
        Tren tren = buscarTren(idTren);
        if(tren == null){
            return false;
        }
        boolean agregado = tren.eliminarVagonPasajeros(idVagon);
        if(agregado){
            guardar();
            guardarLog(new Date() + " ELIMINAR EN: " + idTren + " VAGON PASAJEROS: " + idVagon + " USUARIO: " + usuario.getUsuario());
        }
        return agregado;
    }

    public boolean eliminarVagonCarga(String idTren, String idVagon, Usuario usuario){
        Tren tren = buscarTren(idTren);
        if(tren == null){
            return false;
        }
        boolean agregado = tren.eliminarVagonCarga(idVagon);
        if(agregado){
            guardar();
            guardarLog(new Date() + " ELIMINAR EN: " + idTren + " VAGON CARGA: " + idVagon + " USUARIO: " + usuario.getUsuario());
        }
        return agregado;
    }

    public int asignarAsiento(String idTren, CategoriaBoleto categoria){
        Tren tren = buscarTren(idTren);
        if(tren == null){
            return -1;
        }
        int numeroAsiento = tren.obtenerAsiento(categoria);
        if(numeroAsiento == -1){
            return -1;
        }
        guardar();
        return numeroAsiento;
    }

    public boolean vaciarPasajerosTren(String idTren, Empleado empleado) {
        Tren tren = buscarTren(idTren);
        if(tren == null){
            return false;
        }
        tren.vaciarPasajeros();
        guardar();
        guardarLog(new Date() + " VACIAR PASAJEROS EN: " + idTren  + " USUARIO: " + empleado.getUsuario());
        return true;
    }

}
