package modelo.logica;

import modelo.persistencia.GestorArchivos;

public class GestorIds {
    private static final String rutaArchivo = "contadoresIds";
    private int boleto;
    private int equipaje;
    private int ruta;
    private int tren;
    private int vagonPasajeros;
    private int vagonCarga;
    private int viaje;

    //CONSTRUCTOR
    public GestorIds() {
    }

    //METODO PARA MANTENERLO ACTUALIZADO
    private void guardar() {
        GestorArchivos.escribirJSON(rutaArchivo, this);
    }

    //METODO PARA CARGAR LO QUE HAY EN EL JSON CADA QUE SE ABRA EL SISTEMA, LO HACE AL INICIAR EL SISTEMA
    public void cargar() {
        GestorIds datos = GestorArchivos.leerJSON(rutaArchivo, GestorIds.class);
        if (datos != null) {
            this.boleto = datos.boleto;
            this.equipaje = datos.equipaje;
            this.ruta = datos.ruta;
            this.tren = datos.tren;
            this.vagonPasajeros = datos.vagonPasajeros;
            this.vagonCarga = datos.vagonCarga;
            this.viaje = datos.viaje;
        }
    }

    //METODOS PARA GENERAR IDS
    //BOLETO
    public String generarIdBoleto() {
        boleto++;
        guardar();
        return "BO" + String.format("%06d", boleto);
    }
    //EQUIPAJE
    public String generarIdEquipaje() {
        equipaje++;
        guardar();
        return "EQ" + String.format("%06d", equipaje);
    }
    //RUTA
    public String generarIdRuta() {
        ruta++;
        guardar();
        return "RU" + String.format("%06d", ruta);
    }
    //TREN
    public String generarIdTren() {
        tren++;
        guardar();
        return "TR" + String.format("%06d", tren);
    }
    //VAGON PASAJEROS
    public String generarIdVagonPasajeros() {
        vagonPasajeros++;
        guardar();
        return "VP" + String.format("%06d", vagonPasajeros);
    }
    //VAGON CARGA
    public String generarIdVagonCarga() {
        vagonCarga++;
        guardar();
        return "VC" + String.format("%06d", vagonCarga);
    }
    //VIAJE
    public String generarIdViaje() {
        viaje++;
        guardar();
        return "VI" + String.format("%06d", viaje);
    }

    public String toString() {
        return
                "Boleto: " + boleto + "\n" +
                "Equipaje: " + equipaje + "\n" +
                "Ruta: " + ruta + "\n" +
                "Tren: " + tren + "\n" +
                "Vagon Pasajeros: " + vagonPasajeros + "\n" +
                "Vagon Carga: " + vagonCarga + "\n" +
                "Viaje: " + viaje + "\n"
                ;
    }

}
