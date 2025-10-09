package modelo;

public class ListaEnlazada<T> {
    //nodo
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
    //fin Nodo

    //atributos de clase ListaEnlazada
    private Nodo cabeza;
    private int cantidad;

    //constructor ListaEnlazada

    public ListaEnlazada() {
        this.cabeza = null;
        this.cantidad = 0;
    }

    //metodos

    //metodo agregrar al inicio
    public void agregar(T dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            nuevo.setSiguiente(cabeza);
            cabeza = nuevo;
        }
        cantidad++;
    }

    public T buscar(T dato) {
        if (cabeza == null) {
            return null;
        }
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getDato() == dato) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public int tamano() {
        return cantidad;
    }

    //verificar si esta vacia
    public boolean vacio() {
        if (cabeza == null) {
            return true;
        }
        return false;
    }

    public void borrar(T dato) {
        if (vacio()) {
            System.out.println("la lista esta vacia");
            return;
        }
        if (cabeza.getDato() == dato) {
            cabeza = cabeza.getSiguiente();
            cantidad --;
            return;
        }

        Nodo actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato() == dato) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                cantidad --;
                return;
            }
            actual = actual.getSiguiente();
        }
        System.out.println("no encontro el dato");
    }
    //mostrar todos los elementos
    public void mostrar (){
        if(vacio()){
            System.out.println("esta vacia la lista");
            return;
        }

        Nodo actual = cabeza;
        while(actual!=null){
            System.out.print(actual.getDato()+" - ");
            actual=actual.siguiente;
        }
        System.out.println();
    }
}
