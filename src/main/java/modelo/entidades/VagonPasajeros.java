package modelo.entidades;

import modelo.enums.CategoriaBoleto;

public class VagonPasajeros extends Vagon {

    //Capacidad fija de asientos por categoria
    private final int capacidadPremium = 4;
    private final int capacidadEjecutiva = 8;
    private final int capacidadEstandar = 22;

    private boolean[] ocupacionPremium;
    private int numPremium;
    private boolean[] ocupacionEjecutiva;
    private int numEjecutiva;
    private boolean[] ocupacionEstandar;
    private int numEstandar;

    // CONSTRUCTOR
    public VagonPasajeros(String idVagon) {
        // Llama al constructor de Vagon
        super(idVagon);

        ocupacionPremium = new boolean[capacidadPremium];
        ocupacionEjecutiva = new boolean[capacidadEjecutiva];
        ocupacionEstandar = new boolean[capacidadEstandar];
        numPremium = 0;
        numEjecutiva = 0;
        numEstandar = 0;
    }

    //GETTERS
    public int getCapacidadPremium() {
        return capacidadPremium;
    }

    public int getCapacidadEjecutiva() {
        return capacidadEjecutiva;
    }

    public int getCapacidadEstandar() {
        return capacidadEstandar;
    }

    public boolean[] getOcupacionPremium() {
        return ocupacionPremium;
    }

    public int getNumPremium() {
        return numPremium;
    }

    public boolean[] getOcupacionEjecutiva() {
        return ocupacionEjecutiva;
    }

    public int getNumEjecutiva() {
        return numEjecutiva;
    }

    public boolean[] getOcupacionEstandar() {
        return ocupacionEstandar;
    }

    public int getNumEstandar() {
        return numEstandar;
    }

    //METODOS
    //Agregar asiento ocupado al arreglo dependiendo la categoria
    public boolean ocuparAsiento(CategoriaBoleto categoria){
        if(categoria == CategoriaBoleto.PREMIUM){
            if(numPremium < capacidadPremium){
                ocupacionPremium[numPremium] = true;
                numPremium++;
                return true;
            }
        }
        if(categoria == CategoriaBoleto.EJECUTIVA){
            if(numEjecutiva < capacidadEjecutiva){
                ocupacionEjecutiva[numEjecutiva] = true;
                numEjecutiva++;
                return true;
            }
        }
        if(categoria == CategoriaBoleto.ESTANDAR){
            if(numEstandar < capacidadEstandar){
                ocupacionEstandar[numEstandar] = true;
                numEstandar++;
                return true;
            }
        }
        return false;
    }

    //Asignar asiento
    public int asignarAsientoVagon(CategoriaBoleto categoria){
        if(categoria == CategoriaBoleto.PREMIUM){
            return numPremium;
        }
        if(categoria == CategoriaBoleto.EJECUTIVA){
            return numEjecutiva + capacidadPremium;
        }
        if(categoria == CategoriaBoleto.ESTANDAR){
            return numEstandar + (capacidadPremium+capacidadEjecutiva) ;
        }
        return -1;
    }

    //Vaciar asientos
    public void vaciarAsientos(){
        this.ocupacionPremium = new boolean[capacidadPremium];
        this.ocupacionEjecutiva = new boolean[capacidadEjecutiva];
        this.ocupacionEstandar = new boolean[capacidadEstandar];
        this.numPremium = 0;
        this.numEjecutiva = 0;
        this.numEstandar = 0;
    }

}
