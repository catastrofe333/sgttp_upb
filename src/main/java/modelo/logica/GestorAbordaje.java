package modelo.logica;

import modelo.entidades.Boleto;
import modelo.enums.CategoriaBoleto;
import modelo.estructuras.ColaPrioridad;

public class GestorAbordaje {

    public ColaPrioridad<Boleto> generarAbordaje(Boleto[] boletosViaje) {
        ColaPrioridad<Boleto> ordenAbordaje = new ColaPrioridad<>();

        if (boletosViaje == null) return ordenAbordaje;

        for (Boleto boleto : boletosViaje) {
            if (boleto != null) {
                ordenAbordaje.encolar(boleto, asignarPrioridad(boleto));
            }
        }
        return ordenAbordaje;
    }

    private int asignarPrioridad(Boleto boleto) {
        int prioridadBoleto = boleto.getAsiento();

        if (boleto.getCategoria() == CategoriaBoleto.PREMIUM) {
            prioridadBoleto += 3000;
        }
        else if (boleto.getCategoria() == CategoriaBoleto.EJECUTIVA) {
            prioridadBoleto += 2000;
        }
        else if (boleto.getCategoria() == CategoriaBoleto.ESTANDAR) {
            prioridadBoleto += 1000;
        }

        return prioridadBoleto;
    }

}
