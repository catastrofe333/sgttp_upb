package modelo;

public abstract class Usuario {
    private String idUsuario;
    private String nombre;
    private String contrasena;
    private String cargo;

    public Usuario(String idUsuario, String nombre, String contrasena, String cargo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.cargo = cargo;
    }

    //Metodo para iniciar sesion
    public boolean iniciarSesion(String usuario, String contrasena){
        if(idUsuario.equals(usuario) && contrasena.equals(contrasena)){
            return true;
        } else{
            System.out.println("Usuario o contraseña incorrectos");
              return false;
        }
    }

    //Getters
    public String getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getCargo() { return cargo; }

    //Setters
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    // Metodo para mostrar informacion del usuario
    public abstract void mostrarInformacion();

}
