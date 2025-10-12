package modelo;

public enum TipoTren {
    TRANSIBERIANO(30),
    CARABELA(34);

    private final int capacidadMaxVagones;

    TipoTren(int capacidadMaxVagones) {
        this.capacidadMaxVagones = capacidadMaxVagones;
    }

    public int getCapacidadMaxVagones() {
        return capacidadMaxVagones;
    }
}
