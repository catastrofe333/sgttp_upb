package modelo;

public class ListaEnlazada<T> {
    //nodo
    private class Nodo {
        private T dato;
        private Nodo siguiente;

        public Nodo (T dato) {
            this.dato=dato;
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
        this.cabeza=null;
        this.cantidad=0;
    }

    //metodos

    //metodo agregrar al inicio
    public void agregar(T dato){
        Nodo nuevo = new Nodo(dato);
        if (cabeza==null){
            cabeza=nuevo;
        }
        else{
            nuevo.siguiente=cabeza;
            cabeza=nuevo;
        }
        cantidad ++;
    }

    public T buscar(T dato){
        if (cabeza == null){
            return null;
        }
        Nodo actual = cabeza;
        while (actual!=null){
            if (actual.getDato()==dato){
                return actual.getDato();
            }
            actual=actual.getSiguiente();
        }
        return null;
    }

    public int tamano (){
        return cantidad;
    }
    //verificar si esta vacia
    public boolean vacio (){
        if(cabeza == null){
            return true;
        }
        return false;
    }
    public void borrar (T dato){
        if (vacio()){
            System.out.println("la lista esta vacia");
            return;
        }
        if (cabeza==dato){
            cabeza=cabeza.getSiguiente();
            return;
        }

        Nodo actual = cabeza;
        while (actual.getSiguiente()!=null){
            if (actual.getSiguiente()==dato) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                return;
            }
            actual=actual.getSiguiente();
        }
        System.out.println("no encontro el dato");
    }
    //mostrar todos los elementos
}
