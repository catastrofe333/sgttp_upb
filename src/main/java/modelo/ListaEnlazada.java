package modelo;

public class ListaEnlazada<T> {
    private Nodo<T> cabeza;
    private int tamano;

    //Constructor ListaEnlazada
    public ListaEnlazada() {
        this.cabeza = null;
        this.tamano = 0;
    }

    //METODOS LISTA ENLAZADA
    //Metodo agregrar al inicio
    //TODO: METODO QUE SEA PARA AGREGAR AL FINAL, ALERTA!! NO CAMBIAR EL NOMBRE
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (vacio()){
            cabeza=nuevo;
        }else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente()!=null){
                actual=actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamano ++;
    }

    //Verificar si esta vacia
    public boolean vacio() {
        return cabeza == null;
    }

    //Borrar por dato
    public Boolean borrar(T dato) {
        if (vacio()) {
            return false;
        }
        if (cabeza.getDato() == dato) {
            cabeza = cabeza.getSiguiente();
            tamano --;
            return true;
        }

        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato() == dato) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                tamano --;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    //metodo eliminarlista
    public void eliminarLista (){
        cabeza=null;
    }

    //Getter de cabeza
    public Nodo<T> getCabeza() {
        return cabeza;
    }

    //Getter de tamaño
    public int getTamano() {
        return tamano;
    }
}
