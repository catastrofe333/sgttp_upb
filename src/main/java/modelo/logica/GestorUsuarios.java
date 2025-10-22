package modelo.logica;

import modelo.entidades.*;
import modelo.persistencia.GestorArchivos;

import java.util.Arrays;
import java.util.Date;

public class GestorUsuarios {
    private static final String rutaArchivoAdministrador = "administrador";
    private static final String rutaArchivoEmpleados = "empleados";
    private Administrador administrador;
    private Empleado[] empleados;

    //CONSTRUCTOR
    public GestorUsuarios() {
    }

    //METODO PARA CARGAR LO QUE HAY EN EL JSON CADA QUE SE ABRA EL SISTEMA, LO HACE AL INICIAR EL SISTEMA
    public void cargar() {
        administrador = GestorArchivos.leerJSON(rutaArchivoAdministrador, Administrador.class);
        empleados = GestorArchivos.leerJSON(rutaArchivoEmpleados, Empleado[].class);
    }

    //METODO PARA MANTENERLO ACTUALIZADO
    private void guardar() {
        GestorArchivos.escribirJSON(rutaArchivoEmpleados, empleados);
    }

    private void guardarLog(String log) {
        GestorArchivos.escribirTxt("log", log);
    }

    //GETTERS
    public Administrador getAdministrador() {
        return administrador;
    }

    public Empleado[] getEmpleados() {
        return empleados;
    }

    //METODOS
    public Administrador iniciarSesionAdministrador(String usuario, String contrasena) {
        if (usuario.equals(administrador.getUsuario()) && contrasena.equals(administrador.getContrasena())) {
            return administrador;
        }
        return null;
    }

    public Empleado iniciarSesionEmpleado(String usuario, String contrasena) {
        for (Empleado empleado : empleados) {
            if (usuario.equals(empleado.getUsuario()) && contrasena.equals(empleado.getContrasena())) {
                return empleado;
            }
        }
        return null;
    }

    public void agregarEmpleado(Empleado empleado, Administrador administrador){
        Empleado[] temporal = Arrays.copyOf(empleados, empleados.length + 1);
        temporal[empleados.length] = empleado;
        empleados = temporal;
        guardar();
        guardarLog(new Date() + " AGREGAR: " + empleado.getId() + " USUARIO: " + administrador.getUsuario());
    }

    public Empleado buscarEmpleado(String idEmpleado) {
        for(Empleado empleado : empleados){
            if(empleado.getId().equals(idEmpleado)){
                return empleado;
            }
        }
        return null;
    }

    public boolean eliminarEmpleado(String idEmpleado, Administrador administrador){
        Empleado empleadoAEliminar = buscarEmpleado(idEmpleado);
        if(empleadoAEliminar == null){
            return false;
        }
        Empleado[] temporal = new Empleado[empleados.length - 1];
        int i = 0;
        for(Empleado empleado : empleados){
            if(!empleado.getId().equals(idEmpleado)){
                temporal[i] = empleado;
                i++;
            }
        }
        empleados = temporal;
        guardar();
        guardarLog(new Date() + " ELIMINAR: " + idEmpleado + " USUARIO: " + administrador.getUsuario());
        return true;
    }

    public boolean modificarCargoEmpleado(String idEmpleado, String nuevoCargo, Administrador administrador){
        Empleado empleado = buscarEmpleado(idEmpleado);
        if(empleado == null){
            return false;
        }
        empleado.setCargo(nuevoCargo);
        guardar();
        guardarLog(new Date() + " MODIFICAR: " + idEmpleado + " NUEVO_CARGO: " + nuevoCargo + " USUARIO: " + administrador.getUsuario());
        return true;
    }

    public boolean modificarUsuarioEmpleado(String idEmpleado, String nuevoUsuario, Administrador administrador){
        Empleado empleado = buscarEmpleado(idEmpleado);
        if(empleado == null){
            return false;
        }
        empleado.setUsuario(nuevoUsuario);
        guardar();
        guardarLog(new Date() + " MODIFICAR: " + idEmpleado + " NUEVO_USUARIO: " + nuevoUsuario + " USUARIO: " + administrador.getUsuario());
        return true;
    }

    public boolean modificarContrasenaEmpleado(String idEmpleado, String nuevaContrasena, Administrador administrador){
        Empleado empleado = buscarEmpleado(idEmpleado);
        if(empleado == null){
            return false;
        }
        empleado.setContrasena(nuevaContrasena);
        guardar();
        guardarLog(new Date() + " MODIFICAR: " + idEmpleado + " NUEVA_CONTRASENA: " + nuevaContrasena + " USUARIO: " + administrador.getUsuario());
        return true;
    }
}
