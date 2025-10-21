package modelo.logica;

import modelo.entidades.*;
import modelo.enums.CategoriaBoleto;
import modelo.enums.Estacion;
import modelo.enums.EstadoTrayecto;
import modelo.persistencia.GestorArchivos;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class GestorViajes {
    private static final String rutaArchivo = "viajes";
    private Viaje[] viajes;

    //CONSTRUCTOR
    public GestorViajes() {
    }

    //METODO PARA CARGAR LO QUE HAY EN EL JSON CADA QUE SE ABRA EL SISTEMA, LO HACE AL INICIAR EL SISTEMA
    public void cargar() {
        viajes = GestorArchivos.leerJSON(rutaArchivo, Viaje[].class);
    }

    //METODO PARA MANTENERLO ACTUALIZADO
    private void guardar() {
        GestorArchivos.escribirJSON(rutaArchivo, viajes);
    }

    //METODO PARA ACTUALIZAR EL LOG
    private void guardarLog(String log) {
        GestorArchivos.escribirTxt("log", log);
    }

    //GETTERS
    public Viaje[] getViajes() {
        return viajes;
    }

    //METODOS DE LOS VIAJES
    public void agregarViaje(Viaje viaje, Administrador administrador){
        if (viajes == null) {
            viajes = new Viaje[0];
        }
        Viaje[] temporal = Arrays.copyOf(viajes, viajes.length + 1);
        temporal[viajes.length] = viaje;
        viajes = temporal;
        guardar();
        guardarLog(new Date() + " AGREGAR: " + viaje.getIdViaje() + " USUARIO: " + administrador.getUsuario());
    }

    public Viaje buscarViaje(String idViaje) {
        for(Viaje viaje : viajes){
            if(viaje.getIdViaje().equals(idViaje)){
                return viaje;
            }
        }
        return null;
    }

    public boolean eliminarViaje(String idViaje, Administrador administrador){
        if (viajes == null || viajes.length == 0) return false;
        Viaje viajeAEliminar = buscarViaje(idViaje);
        if(viajeAEliminar == null){
            return false;
        }
        Viaje[] temporal = new Viaje[viajes.length - 1];
        int i = 0;
        for(Viaje viaje : viajes){
            if(!viaje.getIdViaje().equals(idViaje)){
                temporal[i] = viaje;
                i++;
            }
        }
        viajes = temporal;
        guardar();
        guardarLog(new Date() + " ELIMINAR: " + idViaje + " USUARIO: " + administrador.getUsuario());
        return true;
    }

    public boolean hacerVisible(String idViaje, Administrador administrador){
        Viaje viajeVisible = buscarViaje(idViaje);
        if(viajeVisible == null || viajeVisible.isVisible()){
            return false;
        }
        viajeVisible.setVisible(true);
        guardar();
        guardarLog(new Date() + " HACER VISIBLE VIAJE: " + idViaje + " USUARIO: " + administrador.getUsuario());
        return true;
    }


    public boolean modificarRuta(String idViaje, String idNuevaRuta, Administrador administrador){
        Viaje viaje = buscarViaje((idViaje));
        if(viaje == null){
            return false;
        }
        if(viaje.getEstado() != EstadoTrayecto.PENDIENTE){
            return false;
        }
        viaje.setIdRuta(idNuevaRuta);
        guardar();
        guardarLog(new Date() + " MODIFICAR RUTA DE VIAJE: " + idViaje + " NUEVA RUTA: " + idNuevaRuta + " USUARIO: " + administrador.getUsuario());
        return true;
    }

    public boolean modificarHorarios(String idViaje, Date nuevaFechaSalida, Date nuevaFechaLlegada, Administrador administrador){
      Viaje viaje = buscarViaje(idViaje);
      if(viaje == null){
          return false;
      }
      if(viaje.getEstado() != EstadoTrayecto.PENDIENTE){
          return false;
      }
      viaje.setFechaSalida(nuevaFechaSalida);
      viaje.setFechaLlegada(nuevaFechaLlegada);
      guardar();
      guardarLog(new Date() + " MODIFICAR HORARIO DE VIAJE: " + idViaje + " NUEVA SALIDA: " + nuevaFechaSalida +
              "NUEVA LLEGADA: " + nuevaFechaLlegada + " USUARIO: " + administrador.getUsuario() + "\n");
      return true;
    }

    public boolean modificarEstadoTrayecto(String idViaje, EstadoTrayecto nuevoEstadoTrayecto, Empleado empleado) {
        Viaje viaje = buscarViaje(idViaje);
        if(viaje == null){
            return false;
        }
        viaje.setEstado(nuevoEstadoTrayecto);
        guardar();
        guardarLog(new Date() + " MODIFICAR ESTADO DE VIAJE: " + idViaje + " NUEVA ESTADO: " + nuevoEstadoTrayecto +
                " USUARIO: " + empleado.getUsuario() + "\n");
        return true;
    }

    public Viaje[] buscarViajesPorRuta(Ruta[] rutasEncontradas, Date fechaSalida) {
        if (viajes == null || rutasEncontradas == null) return null;
        Viaje[] viajesEncontrados = new Viaje[0];

        for (Ruta ruta : rutasEncontradas) {
            for (Viaje viaje : viajes) {
                if (viaje.isVisible() && sonMismoDia(viaje.getFechaSalida(), fechaSalida)
                        && viaje.getIdRuta().equals(ruta.getIdRuta())) {
                    viajesEncontrados = Arrays.copyOf(viajesEncontrados, viajesEncontrados.length + 1);
                    viajesEncontrados[viajesEncontrados.length - 1] = viaje;
                }
            }
        }
        return viajesEncontrados;
    }

    public Viaje[] concatenarViajes(Viaje[] viajesOrigen, Viaje[] viajesDestino) {
        if (viajesOrigen.length == 0) {
            return null;
        }
        if (viajesDestino.length == 0) {
            return null;
        }

        Viaje[] resultado = new Viaje[viajesOrigen.length + viajesDestino.length];

        System.arraycopy(viajesOrigen, 0, resultado, 0, viajesOrigen.length);
        System.arraycopy(viajesDestino, 0, resultado, viajesOrigen.length, viajesDestino.length);

        return resultado;
    }

    public Viaje[] buscarViajesVisibles() {
        if (viajes == null) return null;
        Viaje[] viajesEncontrados = new Viaje[0];

        for (Viaje viaje : viajes) {
            if (viaje.isVisible()) {
                viajesEncontrados = Arrays.copyOf(viajesEncontrados, viajesEncontrados.length + 1);
                viajesEncontrados[viajesEncontrados.length - 1] = viaje;
            }
        }

        return viajesEncontrados;
    }

    public boolean descontarAsiento(String idViaje, CategoriaBoleto categoria){
        Viaje viaje = buscarViaje(idViaje);
        if(viaje == null){
            return false;
        }
        if(viaje.descontarAsiento(categoria)){
            guardar();
            return true;
        }
        return false;
    }

    private boolean sonMismoDia(Date fecha1, Date fecha2) {
        if (fecha1 == null || fecha2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fecha1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(fecha2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /*public boolean generarViajesSemana(String idViaje, Administrador administrador) {
        Viaje viajeBase = buscarViaje(idViaje);
        if (viajeBase == null) {
            return false;
        }

        //Generar 6 viajes más (para tener un total de 7 días)
        for (int i = 1; i <= 6; i++) {
            // 3️⃣ Crear nuevas fechas sumando días a la salida y llegada
            Date nuevaSalida = new Date(viajeBase.getFechaSalida().getTime() + (i * 86400000)); //86400000 = milisegundos en un día
            Date nuevaLlegada = new Date(viajeBase.getFechaLlegada().getTime() + (i * 86400000));

            // Crear el nuevo viaje con los mismos datos base
            Viaje nuevoViaje = new Viaje(
                    nuevoId,
                    nuevaSalida,
                    nuevaLlegada,
                    viajeBase.getIdRuta(),
                    viajeBase.getDisponiblesPremium(),
                    viajeBase.getDisponiblesEjecutiva(),
                    viajeBase.getDisponiblesEstandar()
            );


            //Hacerlo visible igual que el original
            nuevoViaje.setVisible(viajeBase.isVisible());

            //Agregarlo al arreglo de viajes
            Viaje[] temporal = Arrays.copyOf(viajes, viajes.length + 1);
            temporal[viajes.length] = nuevoViaje;
            viajes = temporal;
        }
        return true;
        // 8️⃣ Guardar los cambios y registrar en log
        guardar();
        guardarLog(new Date() + " SE CREARON VIAJES PARA LA SEMANA DESDE: " + idViaje +
                " USUARIO: " + administrador.getUsuario());
    }*/

}
