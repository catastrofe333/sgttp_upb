package modelo;

public class VagonPasajeros extends Vagon{
    private ListaEnlazada<Boleto> boletos;
    private int ocupadosPremium;
    private int ocupadosEjecutiva;
    private int ocupadosEstandar;

    public VagonPasajeros(String idVagon) {
        super(idVagon);
        this.boletos = new ListaEnlazada<>();
        this.ocupadosPremium = 0;
        this.ocupadosEjecutiva = 0;
        this.ocupadosEstandar = 0;
    }

    //Getters y setters
    public ListaEnlazada<Boleto> getBoletos() {
        return boletos;
    }

    public void setBoletos(ListaEnlazada<Boleto> boletos) {
        this.boletos = boletos;
    }

    public int getOcupadosPremium() {
        return ocupadosPremium;
    }

    public void setOcupadosPremium(int ocupadosPremium) {
        this.ocupadosPremium = ocupadosPremium;
    }

    public int getOcupadosEjecutiva() {
        return ocupadosEjecutiva;
    }

    public void setOcupadosEjecutiva(int ocupadosEjecutiva) {
        this.ocupadosEjecutiva = ocupadosEjecutiva;
    }

    public int getOcupadosEstandar() {
        return ocupadosEstandar;
    }

    public void setOcupadosEstandar(int ocupadosEstandar) {
        this.ocupadosEstandar = ocupadosEstandar;
    }

    // Asignar boleto al vagón
    public boolean asignarBoleto(Boleto boleto) {
        if (boleto.getCategoria() == CategoriaBoleto.PREMIUM) {
            if (ocupadosPremium >= CategoriaBoleto.PREMIUM.getLugaresPorVagon()) {
                return false;
            }
            ocupadosPremium++;
        }
        else if (boleto.getCategoria() == CategoriaBoleto.EJECUTIVA) {
            if (ocupadosEjecutiva >= CategoriaBoleto.EJECUTIVA.getLugaresPorVagon()) {
                return false;
            }
            ocupadosEjecutiva++;
        }
        else if (boleto.getCategoria() == CategoriaBoleto.ESTANDAR) {
            if (ocupadosEstandar >= CategoriaBoleto.ESTANDAR.getLugaresPorVagon()) {
                return false;
            }
            ocupadosEstandar++;
        }
        boletos.agregar(boleto);
        return true;
    }

    //Mostrar informacion
    @Override
    public void mostrarInformacion() {
        System.out.println("ID del Vagon: " + getIdVagon());
        System.out.println("Lugares disponibles para la categoria premium: " + ocupadosPremium);
        System.out.println("Lugares disponibles para la categoria ejecutiva: " + ocupadosEjecutiva);
        System.out.println("Lugares disponibles para la categoria estandar: " + ocupadosEstandar);
    }

}
