package modelo;

import modelo.entidades.Ruta;
import modelo.enums.Estacion;
import modelo.logica.Sistema;

public class PrincipalTemporal {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();

        Estacion[] estacionesVisitar = {Estacion.MANIZALES, Estacion.BOGOTA, Estacion.CARTAGENA};

        Estacion[] camino = sistema.getGestorRuta().generarCamino(Estacion.CALI, Estacion.BARRANQUILLA, estacionesVisitar);

        if (camino == null) {
            System.out.println("La ruta contiene estaciones repetidas");
        } else {
            Ruta ruta = new Ruta("1", "1", Estacion.CALI, Estacion.BARRANQUILLA, camino);

            for(Estacion estacion : ruta.getCamino()) {
                System.out.print( " -> " + estacion);
            }

            System.out.println();
            System.out.println(ruta.getKilometros());
            System.out.println(ruta.getValorBase());
        }


        /*
        Grafo grafo = new Grafo();

        Estacion[] caminoMasCorto = grafo.caminoMasCorto(Estacion.NEIVA, Estacion.PEREIRA);

        for (Estacion estacion : caminoMasCorto) {
            System.out.print(estacion + " ");
        }
        */
    }
}
