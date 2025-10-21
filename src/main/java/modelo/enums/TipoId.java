package modelo.enums;

public enum TipoId {
    CC("Cedula de Ciudadania"),
    CE("Cedula Extranjera"),
    TI("Tarjeta de Identidad");

    private final String descripcion;

    TipoId(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
