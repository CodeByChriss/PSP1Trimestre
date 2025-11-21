package UT02.monitor;

import java.util.ArrayList;
import java.util.List;

public class Monitor<T> {
    private List<T> lista = new ArrayList<T>();
    private static final int CAPACIDAD = 10;

    public synchronized void put(T elemento) throws InterruptedException{
        while(lista.size() == CAPACIDAD){
            // se bloquea si la lista está llena
            wait();
        }
        lista.add(elemento);
        notifyAll(); // Notifica a los consumidores que hay un nuevo elemento
    }

    public synchronized T take() throws InterruptedException{
        while(lista.isEmpty()){
            // se bloque si la lista está vacía
            wait();
        }
        T elemento = lista.remove(0);
        notifyAll();
        return elemento;
    }

    public synchronized int size() {
        return lista.size();
    }
}
