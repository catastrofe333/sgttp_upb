package modelo.enums;

public enum TipoTren {
    TRANSIBERIANO(30, 20, 10),
    CARABELA(34, 22, 12);

    private final int capacidadMaxVagones;
    private final int capacidadMaxVagonesPasajeros;
    private final int capacidadMaxVagonesCarga;

    TipoTren(int capacidadMaxVagones, int capacidadMaxVagonesPasajeros, int capacidadMaxVagonesCarga) {
        this.capacidadMaxVagones = capacidadMaxVagones;
        this.capacidadMaxVagonesPasajeros = capacidadMaxVagonesPasajeros;
        this.capacidadMaxVagonesCarga = capacidadMaxVagonesCarga;
    }

    public int getCapacidadMaxVagones() {
        return capacidadMaxVagones;
    }

    public int getCapacidadMaxVagonesPasajeros() {
        return capacidadMaxVagonesPasajeros;
    }

    public int getCapacidadMaxVagonesCarga() {
        return capacidadMaxVagonesCarga;
    }
}
