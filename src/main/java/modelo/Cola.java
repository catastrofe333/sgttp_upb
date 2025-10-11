package modelo;

public class Cola<T> {

    //inicio nodo
    private class Nodo {
        private T dato;
        private Nodo siguiente;

        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }

        public T getDato() {
            return dato;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }

        public Nodo getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo siguiente) {
            this.siguiente = siguiente;
        }
    }
    //fin nodo

    //variables de clase
    private Nodo cabeza;
    private Nodo cola;
    private int contador;

    public Cola() {
        this.cabeza = null;
        this. cola = null;
        this. contador = 0;
    }

    //agregar por la cola
    public void agregar (T dato){
        Nodo nuevo = new Nodo(dato);
        if (vacio()){
            cabeza=nuevo;
            cola = nuevo;
        }else{
            cola.setSiguiente(nuevo);
            cola=nuevo;
        }
        contador ++;
    }
    //eliminar por la cabeza
    public T eliminar (){
        if (vacio()){
            System.out.println("la lista esta vacia");
            return null;
        }
        if (contador == 1){
            Nodo mostrar = cabeza;
            cabeza = null;
            cola = null;
            contador --;
            return mostrar.getDato();
        }
        Nodo mostrar = cabeza;
        cabeza=cabeza.getSiguiente();
        contador --;
        return mostrar.getDato();

    }
    //vacio
    public boolean vacio (){
        if (cabeza == null){
            return true;
        }
        return false;
    }
}
