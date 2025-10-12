package modelo;

public enum TipoIdPasajero {
    CC("Cedula de Ciudadania"),
    CE("Cedula Extranjera"),
    TI("Tarjeta de Identidad");

    private final String descripcion;

    TipoIdPasajero(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
