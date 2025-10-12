package modelo;

public class VagonCarga extends Vagon {
    private ListaEnlazada<Equipaje> equipajes;

    //Constructor
    public VagonCarga(String idVagon){
        super(idVagon);
        this.equipajes = new ListaEnlazada<>();
    }

    //Getters
    public ListaEnlazada<Equipaje> getEquipajes() {
        return equipajes;
    }

    //Registrar un equipaje
    public boolean agregarEquipaje(Equipaje equipaje){
        equipajes.agregar(equipaje);
        return true;
    }

    //Mostrar informacion
    @Override
    public void mostrarInformacion() {
        System.out.println("ID del Vagon: " + getIdVagon());
    }

}
