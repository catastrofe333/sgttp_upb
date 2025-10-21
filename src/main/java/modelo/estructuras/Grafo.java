package modelo.estructuras;

import modelo.enums.Estacion;

import java.util.Arrays;

public class Grafo {
    private final Estacion[] estaciones;
    private final int numEstaciones;
    private final int[][] matrizAdy;

    //CONSTRUCTOR
    public Grafo() {
        estaciones = new Estacion[]{
                Estacion.BOGOTA, Estacion.MEDELLIN, Estacion.CALI, Estacion.CARTAGENA,
                Estacion.BARRANQUILLA, Estacion.BUCARAMANGA, Estacion.SANTA_MARTA,
                Estacion.PEREIRA, Estacion.MANIZALES, Estacion.CUCUTA,
                Estacion.IBAGUE, Estacion.NEIVA, Estacion.PASTO
        };
        numEstaciones = estaciones.length;
        matrizAdy = new int[][]{
                /* A  B   C   D   E    F    G   H   I   J    K    L    M */
                {0,  30, 40,  50,  0,  60,  0,   0,  0,  0,   0,   0,   0},    // A
                {30, 0,  0,    0,  0,  0,   0,   0,  0,  0,   0,   0,   0},       // B
                {40, 0,  0,    0,  0,  0,   0,   0,  80, 120, 100, 130, 120}, // C
                {50, 0,  0,    0,  20, 0,   0,   0,  0,    0, 0,    0,   0},       // D
                {0,  0,  0,    20,  0, 65,  0,   0,  0,    0, 0,     0,  0},       // E
                {60, 0,  0,     0,  65, 0,  80,  0,  0,    0, 0,     0,  0},       // F
                {0,  0,  0,     0,  0,  80,  0,  30, 145,  0, 0,     0,  0},      // G
                {0,  0,  0,     0,  0,   0,  30, 0,    0,  0, 0,    0,   0},         // H
                {0,  0,  80,    0,  0,   0,  145, 0,   0,  0, 0,     0,  0},       // I
                {0,  0,  120,   0,  0,   0,  0,  0,   0,  0, 0,     0,  0},        // J
                {0,  0,  100,   0,  0,   0,  0,  0,   0,  0, 0,     0,  0},        // K
                {0,  0,  130,   0,  0,   0,  0,  0,   0,  0, 0,     0,  0},        // L
                {0,  0,  120,   0,  0,   0,  0,  0,   0,  0, 0,     0,  0}         // M
        };
    }

    //GETTERS
    public int[][] getMatrizAdy() {
        return matrizAdy;
    }

    //METODOS
    //Obtener indices de las estaciones
    public int obtenerIndice(Estacion estacion) {
        for (int i = 0; i < numEstaciones; i++) {
            if (estaciones[i] == estacion) {
                return i;
            }
        }
        return -1;
    }

    //Obtener caminos mas cortos desde un vertice a todos los demas
    private int[] caminosCortos(int origen, Estacion[] visitadas){
        int[] distanciasMinimas = new int[numEstaciones];
        int[] padre = new int[numEstaciones];
        Arrays.fill(padre, -1);
        int infinito = Integer.MAX_VALUE;
        Arrays.fill(distanciasMinimas, infinito);
        distanciasMinimas[origen] = 0;
        boolean[] estacionesVisitadas = new boolean[numEstaciones];
        int estacionActual = origen;

        if (visitadas != null) {
            for (Estacion estacion : visitadas) {
                int indice = obtenerIndice(estacion);
                if (indice != origen) {
                    estacionesVisitadas[indice] = true;
                }
            }
        }

        while(!estacionesVisitadas[estacionActual]){
            estacionesVisitadas[estacionActual] = true;
            for (int i = 0; i < numEstaciones ; i++) {
                int kmsIntercepcion = matrizAdy[estacionActual][i];
                if(kmsIntercepcion != 0){
                    int distancia = kmsIntercepcion + distanciasMinimas[estacionActual];
                    if(distancia < distanciasMinimas[i]){
                        distanciasMinimas[i] = distancia;
                        padre[i] = estacionActual;
                    }
                }
            }
            int masCortaSinVisitar = infinito;
            for (int i = 0; i < numEstaciones; i++) {
                if(!estacionesVisitadas[i]){
                    if(masCortaSinVisitar > distanciasMinimas[i]) {
                        masCortaSinVisitar = distanciasMinimas[i];
                        estacionActual = i;
                    }
                }
            }
        }

        return padre;
    }

    //Camino mas corto desde un vertice a otro
    public Estacion[] caminoMasCorto(Estacion origen, Estacion destino, Estacion[] visitadas) {
        int indiceOrigen = obtenerIndice(origen);
        int indiceDestino = obtenerIndice(destino);
        if (indiceOrigen == -1 || indiceDestino == -1) {
            return null;
        }

        int[] padre = caminosCortos(indiceOrigen, visitadas);
        int estacionActual = indiceDestino;
        ListaEnlazada<Estacion> camino = new ListaEnlazada<>();

        while (estacionActual != -1) {
            camino.agregar(estaciones[estacionActual]);
            if (estacionActual == indiceOrigen) {
                break;
            }
            estacionActual = padre[estacionActual];
        }

        if(camino.vacio() || camino.getCabeza().getDato() != origen){
            return null;
        }

        return toArray(camino);
    }

    private Estacion[] toArray(ListaEnlazada<Estacion> camino) {
        Estacion[] arreglo = new Estacion[camino.getTamano()];

        ListaEnlazada.Nodo<Estacion> actual = camino.getCabeza();
        for (int i = 0 ; i < arreglo.length ; i++) {
            arreglo[i] = actual.getDato();
            actual = actual.getSiguiente();
        }

        return arreglo;
    }


}
