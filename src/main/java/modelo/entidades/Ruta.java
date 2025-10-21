package modelo.entidades;

import modelo.enums.Estacion;
import modelo.estructuras.Grafo;

public class Ruta {
    private final String idRuta;
    private final String idTren;
    private final Estacion origen;
    private final Estacion destino;
    private final Estacion[] camino;
    private final int kilometros;
    private final int valorBase;
    private final static int valorKilometro = 500;

    //CONSTRUCTOR
    public Ruta(String idRuta, String idTren, Estacion origen, Estacion destino, Estacion[] camino) {
        this.idRuta = idRuta;
        this.idTren = idTren;
        this.origen = origen;
        this.destino = destino;
        this.camino = camino;
        this.kilometros = getKilometrosPorCamino();
        this.valorBase = kilometros * valorKilometro;
    }

    //GETTERS
    public String getIdRuta() {
        return idRuta;
    }

    public String getIdTren() {
        return idTren;
    }

    public Estacion getOrigen() {
        return origen;
    }

    public Estacion getDestino() {
        return destino;
    }

    public Estacion[] getCamino() {
        return camino;
    }

    public int getKilometros() {
        return kilometros;
    }

    public int getValorBase() {
        return valorBase;
    }

    private int getKilometrosPorCamino() {
        int kilometros = 0;

        Grafo grafo = new Grafo();
        int[][] distancias = grafo.getMatrizAdy();

        for (int i = 0; i < camino.length - 1; i++) {
            kilometros += distancias[grafo.obtenerIndice(camino[i])][grafo.obtenerIndice(camino[i+1])];
        }

        return kilometros;
    }
}
