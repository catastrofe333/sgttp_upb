package modelo.entidades;

import modelo.enums.TipoId;

public class Empleado extends Usuario {
    public Empleado(TipoId tipoId, String id, String nombres, String apellidos, String cargo, String usuario, String contrasena) {
        super(tipoId, id, nombres, apellidos, cargo, usuario, contrasena);
    }
}
