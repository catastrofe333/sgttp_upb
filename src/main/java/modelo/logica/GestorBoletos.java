package modelo.logica;

import modelo.entidades.*;
import modelo.enums.EstadoEquipaje;
import modelo.persistencia.GestorArchivos;

import java.util.Arrays;
import java.util.Date;

public class GestorBoletos {
    private static final String rutaArchivo = "boletos";
    private Boleto[] boletos;

    //CONSTRUCTOR
    public GestorBoletos() {
    }

    //METODO PARA CARGAR LO QUE HAY EN EL JSON CADA QUE SE ABRA EL SISTEMA, LO HACE AL INICIAR EL SISTEMA
    public void cargar() {
        boletos = GestorArchivos.leerJSON(rutaArchivo, Boleto[].class);
    }

    //METODO PARA MANTENERLO ACTUALIZADO
    private void guardar() {
        GestorArchivos.escribirJSON(rutaArchivo, boletos);
    }

    //METODO PARA ACTUALIZAR EL LOG
    private void guardarLog(String log) {
        GestorArchivos.escribirTxt("log", log);
    }

    //GETTERS
    public Boleto[] getBoletos() {
        return boletos;
    }

    //METODOS
    public void agregarBoleto(Boleto boleto){
        Boleto[] temporal = Arrays.copyOf(boletos, boletos.length + 1);
        temporal[boletos.length] = boleto;
        boletos = temporal;
        guardar();
    }

    public Boleto buscarBoletoPorIdBoleto(String idBoleto) {
        for(Boleto boleto : boletos){
            if(boleto.getIdBoleto().equals(idBoleto)){
                return boleto;
            }
        }
        return null;
    }

    public Boleto[] buscarBoletoPorIdPasajero(String idPasajero) {
        if (boletos == null) return null;

        Boleto[] boletosEncontrados = new Boleto[0];
        for (Boleto boleto : boletos) {
            if (boleto.getIdPasajero().equals(idPasajero)) {
                boletosEncontrados = Arrays.copyOf(boletosEncontrados, boletosEncontrados.length + 1);
                boletosEncontrados[boletosEncontrados.length - 1] = boleto;
            }
        }
        return boletosEncontrados;
    }

    public boolean agregarEquipajeBoleto(String idBoleto, Equipaje equipaje, Empleado empleado){
        Boleto boleto = buscarBoletoPorIdBoleto(idBoleto);
        if(boleto == null){
            return false;
        }
        boleto.agregarEquipaje(equipaje);
        guardar();
        guardarLog(new Date() + " AGREGAR EQUIPAJE: " + equipaje.getIdEquipaje() + " AL BOLETO: "+ boleto.getIdBoleto() + " EMPLEADO: " + empleado.getUsuario());
        return true;
    }

    public Equipaje[] buscarEquipajeBoleto(String idBoleto){
        Boleto boleto = buscarBoletoPorIdBoleto(idBoleto);
        if(boleto == null){
            return null;
        }
        return boleto.getEquipajes();
    }

    public boolean marcarEntregadoEquipaje(String idBoleto, String idEquipaje, Empleado empleado){
        Boleto boleto = buscarBoletoPorIdBoleto(idBoleto);
        if(boleto == null){
            return false;
        }
        for(Equipaje equipaje : boleto.getEquipajes()){
            if(equipaje.getIdEquipaje().equals(idEquipaje)){
                equipaje.setEstado(EstadoEquipaje.ENTREGADO);
                guardar();
                guardarLog(new Date() + " ENTREGADO EQUIPAJE: " + equipaje.getIdEquipaje() + " AL BOLETO: "+ boleto.getIdBoleto() + " EMPLEADO: " + empleado.getUsuario());
                return true;
            }
        }
        return false;
    }

    public boolean validarBoleto(String idBoleto, Empleado empleado) {
        Boleto boleto = buscarBoletoPorIdBoleto(idBoleto);
        if(boleto == null){
            return false;
        }
        boleto.setValidado(true);
        guardar();
        guardarLog(new Date() + " VALIDADO BOLETO: " + boleto.getIdBoleto() + " EMPLEADO: " + empleado.getUsuario());
        return true;
    }

    public Boleto[] buscarBoletosIdViaje(String idViaje) {
        if (boletos == null) return null;

        Boleto[] boletosEncontrados = new Boleto[0];
        for (Boleto boleto : boletos) {
            if (boleto.getIdViaje().equals(idViaje)) {
                boletosEncontrados = Arrays.copyOf(boletosEncontrados, boletosEncontrados.length + 1);
                boletosEncontrados[boletosEncontrados.length - 1] = boleto;
            }
        }
        return boletosEncontrados;
    }

}
