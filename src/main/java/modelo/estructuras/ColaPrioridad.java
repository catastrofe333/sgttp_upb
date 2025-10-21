package modelo.estructuras;

public class ColaPrioridad<T> {
    private Nodo cabeza;

    private class Nodo {
        private T dato;
        private int prioridad;
        private Nodo siguiente;

        public Nodo(T dato, int prioridad) {
            this.dato = dato;
            this.prioridad = prioridad;
            this.siguiente = null;
        }

        public T getDato() {
            return dato;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }

        public int getPrioridad() {
            return prioridad;
        }

        public void setPrioridad(int prioridad) {
            this.prioridad = prioridad;
        }

        public Nodo getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo siguiente) {
            this.siguiente = siguiente;
        }
    }

    public ColaPrioridad() {
        this.cabeza = null;
    }

    public void encolar(T dato, int prioridad) {
        Nodo nuevo = new Nodo(dato, prioridad);

        if (cabeza == null || prioridad > cabeza.getPrioridad()) {
            nuevo.setSiguiente(cabeza);
            cabeza = nuevo;
        }
        else {
            Nodo actual = cabeza;
            while (actual.getSiguiente() != null && actual.getSiguiente().getPrioridad() >= prioridad) {
                actual = actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
        }
    }

    public T desencolar() {
        if (cabeza == null) {
            return null;
        }
        T dato = cabeza.getDato();
        cabeza = cabeza.getSiguiente();
        return dato;
    }

    public boolean vacio() {
        return cabeza == null;
    }
}
