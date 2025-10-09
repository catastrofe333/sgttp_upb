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

    //agregar
    //eliminar
    //buscar
}
