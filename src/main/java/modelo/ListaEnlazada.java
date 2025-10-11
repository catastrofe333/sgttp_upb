package modelo;

public class ListaEnlazada<T> {
    //nodo
    private class Nodo {
        private T dato;
        private Nodo siguiente;
        private String id;

        //constructor sin id
        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
            this.id = null;
        }

        //constructor con id
        public Nodo(T dato, String id) {
            this.dato = dato;
            this.siguiente = null;
            this.id = id;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

    //metodo agregrar al inicio sin id
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

    //metodo agregrar al inicio con id
    public void agregar(T dato, String id) {
        Nodo nuevo = new Nodo(dato, id);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            nuevo.setSiguiente(cabeza);
            cabeza = nuevo;
        }
        cantidad++;
    }

    //metodo buscar sin id
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

    //metodo buscar con id
    public T buscar(String id) {
        if (cabeza == null) {
            return null;
        }
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getId().equals(id)) {
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

    //borrar por dato
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

    //borrar con id
    public void borrar(String id) {
        if (vacio()) {
            System.out.println("la lista esta vacia");
            return;
        }
        if (cabeza.getId().equals(id)) {
            cabeza = cabeza.getSiguiente();
            cantidad --;
            return;
        }

        Nodo actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getId().equals(id)) {
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
