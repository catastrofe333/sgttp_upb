package modelo;

public abstract class Usuario {
    private String id;
    private String nombre;
    private String contrasena;
    private String cargo;

    public Usuario(String idUsuario, String nombre, String contrasena, String cargo) {
        this.id = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.cargo = cargo;
    }

    //Metodo para iniciar sesion
    public boolean iniciarSesion(String usuario, String contrasena){
        if(id.equals(usuario) && contrasena.equals(contrasena)){
            return true;
        } else{
            System.out.println("Usuario o contraseña incorrectos");
              return false;
        }
    }

    //Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCargo() { return cargo; }

    //Setters
    public void setId(String id) {
        this.id = id;
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
