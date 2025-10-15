package modelo;

public class SistemaTrenes {
    private ListaEnlazada<Tren> trenes;
    private ListaEnlazada<Vagon> vagones;
    private ListaEnlazada<Viaje> viajes;
    private ListaEnlazada<Empleado> empleados;
    private ListaEnlazada<Administrador> administradores;

    // CONSTRUCTOR
    public SistemaTrenes() {
        this.trenes = new ListaEnlazada<>();
        this.vagones = new ListaEnlazada<>();
        this.viajes = new ListaEnlazada<>();
        this.empleados = new ListaEnlazada<>();
        this.administradores = new ListaEnlazada<>();
    }

    // GETTERS
    public ListaEnlazada<Tren> getTrenes() {
        return trenes;
    }

    public ListaEnlazada<Vagon> getVagones() {
        return vagones;
    }

    public ListaEnlazada<Viaje> getViajes() {
        return viajes;
    }

    public ListaEnlazada<Empleado> getEmpleados() {
        return empleados;
    }

    public ListaEnlazada<Administrador> getAdministradores() {
        return administradores;
    }

    //METODOS
    //TRENES:
    //Agregar un tren
    public boolean agregarTren(Tren tren) {
        if (buscarTren(tren.getIdTren()) != null) return false;
        trenes.agregar(tren);
        return true;
    }

    //Buscar un tren
    public Tren buscarTren(String idTren) {
        Nodo<Tren> actual = trenes.getCabeza();
        while (actual != null) {
            if (actual.getDato().getIdTren().equals(idTren)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    //Eliminar un tren
    public boolean eliminarTren(String idTren) {
        Tren t = buscarTren(idTren);
        if (t != null) {
            trenes.borrar(t);
            return true;
        }
        return false;
    }

    //Mostrar trenes
    public String mostrarTrenes() {
        if (trenes.vacio()) {
            return "No hay trenes registrados";
        }
        StringBuilder sb = new StringBuilder("Listado de trenes:\n");
        Nodo<Tren> actual = trenes.getCabeza();
        while (actual != null) {
            sb.append(" - ").append(actual.getDato().toString()).append("\n");
            actual = actual.getSiguiente();
        }
        return sb.toString() + "\n";
    }

    //VAGONES:
    //Agregar un vagon
    public boolean agregarVagon(Vagon vagon) {
        if (buscarVagon(vagon.getIdVagon()) != null) {
            return false;
        }
        vagones.agregar(vagon);
        return true;
    }

    //Buscar vagon por ID
    public Vagon buscarVagon(String idVagon) {
        Nodo<Vagon> actual = vagones.getCabeza();
        while (actual != null) {
            if (actual.getDato().getIdVagon().equals(idVagon)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    // Eliminar vagon
    public boolean eliminarVagon(String idVagon) {
        Vagon vagon = buscarVagon(idVagon);
        if (vagon == null) return false;
        return vagones.borrar(vagon);
    }

    //Mostrar vagones
    public String mostrarVagones() {
        if (vagones.vacio()) return "No hay vagones registrados\n";

        StringBuilder sb = new StringBuilder("Listado de vagones:\n");
        Nodo<Vagon> actual = vagones.getCabeza();
        while (actual != null) {
            sb.append(actual.getDato().toString()).append("\n");
            actual = actual.getSiguiente();
        }
        return sb.toString() + "\n";
    }

    //VIAJES:
    //Agregar un viaje
    public boolean agregarViaje(Viaje viaje) {
        if (buscarViaje(viaje.getIdViaje()) != null) return false;
        viajes.agregar(viaje);
        return true;
    }

    //Buscar un viaje
    public Viaje buscarViaje(String idViaje) {
        Nodo<Viaje> actual = viajes.getCabeza();
        while (actual != null) {
            if (actual.getDato().getIdViaje().equals(idViaje)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    //Eliminar viaje
    public boolean eliminarViaje(String idViaje) {
        Viaje viaje = buscarViaje(idViaje);
        if (viaje == null) return false;
        return viajes.borrar(viaje);
    }

    //Mostrar viajes
    public String mostrarViajes() {
        if (viajes.vacio()) {
            return "No hay viajes registrados";
        }
        StringBuilder sb = new StringBuilder("Listado de viajes:\n");
        Nodo<Viaje> actual = viajes.getCabeza();
        while (actual != null) {
            sb.append(" - ").append(actual.getDato().toString()).append("\n");
            actual = actual.getSiguiente();
        }
        return sb.toString() + "\n";
    }

    //EMPLEADOS
    //Agregar empleados
    public void agregarEmpleado(Empleado empleado) {
        empleados.agregar(empleado);
    }

    //Buscar empleado por ID
    public Empleado buscarEmpleado(String id) {
        Nodo<Empleado> actual = empleados.getCabeza();
        while (actual != null) {
            if (actual.getDato().getId().equals(id)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    //Eliminar empleado por ID
    public boolean eliminarEmpleado(String id) {
        Empleado empleado = buscarEmpleado(id);
        if (empleado == null) return false;
        return empleados.borrar(empleado);
    }

    //Mostrar todos los empleados
    //TODO: HACER TOSTRING EN CLASE EMPLEADO
    public String mostrarEmpleados() {
        if (empleados.vacio()) {
            return "No hay empleados registrados\n";
        }

        StringBuilder sb = new StringBuilder("Listado de empleados:\n");
        Nodo<Empleado> actual = empleados.getCabeza();
        while (actual != null) {
            sb.append(" - ").append(actual.getDato().toString()).append("\n");
            actual = actual.getSiguiente();
        }
        return sb.toString() + "\n";
    }

    //ADMINSTRADORES
    //Agregar administrador
    public void agregarAdministrador(Administrador admin) {
        administradores.agregar(admin);
    }

    //Buscar admin por ID
    public Administrador buscarAdministrador(String id) {
        Nodo<Administrador> actual = administradores.getCabeza();
        while (actual != null) {
            if (actual.getDato().getId().equals(id)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    //Eliminar admin por ID
    public boolean eliminarAdministrador(String id) {
        Administrador admin = buscarAdministrador(id);
        if (admin == null) return false;
        return administradores.borrar(admin);
    }

    //Mostrar todos los administradores
    //TODO: HACER TOSTRING EN CLASE ADMIN
    public String mostrarAdministradores() {
        if (administradores.vacio()) {
            return "No hay administradores registrados\n";
        }

        StringBuilder sb = new StringBuilder("Listado de administradores:\n");
        Nodo<Administrador> actual = administradores.getCabeza();
        while (actual != null) {
            sb.append(" - ").append(actual.getDato().toString()).append("\n");
            actual = actual.getSiguiente();
        }
        return sb.toString() + "\n";
    }



}
