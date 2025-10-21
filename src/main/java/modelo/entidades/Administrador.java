package modelo.entidades;

import modelo.enums.TipoId;

public class Administrador extends Usuario {
    public Administrador(TipoId tipoId, String id, String nombres, String apellidos, String cargo, String usuario, String contrasena) {
        super(tipoId, id, nombres, apellidos, cargo, usuario, contrasena);
    }
}
