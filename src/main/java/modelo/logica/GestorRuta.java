package modelo.logica;

import modelo.entidades.Administrador;
import modelo.entidades.Ruta;
import modelo.enums.Estacion;
import modelo.estructuras.Grafo;
import modelo.persistencia.GestorArchivos;

import java.util.Arrays;
import java.util.Date;

public class GestorRuta {
    private static final String rutaArchivo = "ruta";
    private Ruta[] rutas;

    //CONSTRUCTOR
    public GestorRuta() {
    }

    //METODO PARA CARGAR LO QUE HAY EN EL JSON CADA QUE SE ABRA EL SISTEMA, LO HACE AL INICIAR EL SISTEMA
    public void cargar() {
        rutas = GestorArchivos.leerJSON(rutaArchivo, Ruta[].class);
    }

    //METODO PARA MANTENERLO ACTUALIZADO
    private void guardar() {
        GestorArchivos.escribirJSON(rutaArchivo, rutas);
    }

    //METODO PARA ACTUALIZAR EL LOG
    private void guardarLog(String log) {
        GestorArchivos.escribirTxt("log", log);
    }

    //GETTERS
    public Ruta[] getRutas() {
        return rutas;
    }

    //METODOS DE LAS RUTAS
    public void agregarRuta(Ruta ruta, Administrador administrador) {
        // Si aún no hay rutas cargadas (primera vez)
        if (rutas == null) {
            rutas = new Ruta[0];
        }

        Ruta[] temporal = Arrays.copyOf(rutas, rutas.length + 1);
        temporal[rutas.length] = ruta;
        rutas = temporal;

        guardar();

        guardarLog(new Date() + " AGREGAR: " + ruta.getIdRuta() + " USUARIO: " + administrador.getUsuario());
    }

    public Ruta[] buscarRutasPorOrigenDestino(Estacion origen, Estacion destino) {
        if (rutas == null) return null;

        Ruta[] rutasEncontradas = new Ruta[0];
        for (Ruta ruta : rutas) {
            if (ruta.getOrigen() == origen && ruta.getDestino() == destino) {
                rutasEncontradas = Arrays.copyOf(rutasEncontradas, rutasEncontradas.length + 1);
                rutasEncontradas[rutasEncontradas.length - 1] = ruta;
            }
        }
        return ordenarAscendentePorKm(rutasEncontradas);
    }

    public Ruta[] buscarRutasPorOrigen(Estacion origen) {
        if (rutas == null) return null;

        Ruta[] rutasEncontradas = new Ruta[0];
        for (Ruta ruta : rutas) {
            if (ruta.getOrigen() == origen) {
                rutasEncontradas = Arrays.copyOf(rutasEncontradas, rutasEncontradas.length + 1);
                rutasEncontradas[rutasEncontradas.length - 1] = ruta;
            }
        }
        return ordenarAscendentePorKm(rutasEncontradas);
    }

    public Ruta[] buscarRutasPorDestino(Estacion destino) {
        if (rutas == null) return null;

        Ruta[] rutasEncontradas = new Ruta[0];
        for (Ruta ruta : rutas) {
            if (ruta.getDestino() == destino) {
                rutasEncontradas = Arrays.copyOf(rutasEncontradas, rutasEncontradas.length + 1);
                rutasEncontradas[rutasEncontradas.length - 1] = ruta;
            }
        }
        return ordenarAscendentePorKm(rutasEncontradas);
    }

    // ORDENAR ARRREGLO DE ACUERDO A LOS KM
    private Ruta[] ordenarAscendentePorKm(Ruta[] rutas) {
        Ruta[] ordenadas = Arrays.copyOf(rutas, rutas.length);

        for (int i = 0; i < ordenadas.length; i++) {
            for (int j = 0; j < ordenadas.length; j++) {
                if (ordenadas[i].getKilometros() < ordenadas[j].getKilometros()) {
                    Ruta temporal = ordenadas[i];
                    ordenadas[i] = ordenadas[j];
                    ordenadas[j] = temporal;
                }
            }
        }

        return ordenadas;
    }


    //CAMINOS

    public Estacion[] generarCamino(Estacion origen, Estacion destino, Estacion[] estacionesVisitar) {
        Grafo grafo = new Grafo();
        if (estacionesVisitar == null) {
            return generarCaminoDirecto(origen, destino, grafo);
        } else {
            return generarCaminoEntreEstaciones(origen, destino, grafo, estacionesVisitar);
        }
    }


    //METODOS PRIVADOS PARA GENERAR CAMINO
    private Estacion[] generarCaminoDirecto(Estacion origen, Estacion destino, Grafo grafo) {
        return grafo.caminoMasCorto(origen, destino, null);
    }

    private Estacion[] generarCaminoEntreEstaciones(Estacion origen, Estacion destino, Grafo grafo, Estacion[] estacionesVisitar) {
        Estacion origenTemporal = origen;
        Estacion destinoTemporal = estacionesVisitar[0];

        Estacion[] caminoTemporal = new Estacion[0];

        Estacion[] caminoAnterior = caminoTemporal;
        Estacion[] caminoSiguiente = grafo.caminoMasCorto(origenTemporal, destinoTemporal, null);

        for (int i = 0; i < estacionesVisitar.length; i++) {
            if (caminoSiguiente == null) {
                return null;
            }
            caminoTemporal = concatenarArrays(caminoAnterior, caminoSiguiente);

            if ((i + 1) < estacionesVisitar.length) {
                origenTemporal = estacionesVisitar[i];
                destinoTemporal = estacionesVisitar[i + 1];
            } else {
                origenTemporal = estacionesVisitar[i];
                destinoTemporal = destino;
            }

            caminoAnterior = caminoTemporal;
            caminoSiguiente = grafo.caminoMasCorto(origenTemporal, destinoTemporal, caminoAnterior);
        }
        caminoTemporal = concatenarArrays(caminoAnterior, caminoSiguiente);
        return caminoTemporal;
    }

    private Estacion[] concatenarArrays(Estacion[] array1, Estacion[] array2) {
        if (array1.length == 0) {
            return array2;
        }
        if (array2.length == 0) {
            return array1;
        }

        int desplazamiento = 0;

        if (array1[array1.length - 1] == array2[0]) {
            desplazamiento = 1;
        }

        Estacion[] resultado = new Estacion[array1.length + array2.length - desplazamiento];

        System.arraycopy(array1, 0, resultado, 0, array1.length);
        System.arraycopy(array2, desplazamiento, resultado, array1.length, array2.length - desplazamiento);

        return resultado;
    }
}
