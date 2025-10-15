package modelo;

public class VagonCarga extends Vagon {

    //Constructor
    public VagonCarga(String idVagon){
        super(idVagon);
    }

    //Registrar un equipaje

    //Mostrar informacion
    @Override
    public void mostrarInformacion() {
        System.out.println("ID del Vagon: " + getIdVagon());
    }

}
