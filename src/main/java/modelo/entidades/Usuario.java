package modelo.entidades;
import modelo.enums.TipoId;

public abstract class Usuario {
    private final TipoId tipoId;
    private final String id;
    private final String nombres;
    private final String apellidos;
    private String cargo;

    private String usuario;
    private String contrasena;

    //CONSTRUCTOR
    public Usuario(TipoId tipoId, String id, String nombres, String apellidos, String cargo, String usuario, String contrasena) {
        this.tipoId = tipoId;
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cargo = cargo;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    //GETTERS Y SETTERS
    public TipoId getTipoId() {
        return tipoId;
    }

    public String getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    //Mostrar informacion
    @Override
    public String toString(){
        return "ID: " + id + "\n" +
                "Nombre Completo: "+ nombres + " " + apellidos + "\n" +
                "Cargo: " + cargo + "\n" +
                "Usuario: " + usuario;
    }

}
