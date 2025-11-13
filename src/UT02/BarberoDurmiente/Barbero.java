package UT02.BarberoDurmiente;

import java.util.concurrent.BlockingQueue;

public class Barbero extends Thread{

    private BlockingQueue<Cliente> colaClientes;
    private volatile boolean running = true;

    Barbero(BlockingQueue<Cliente> colaClientes){
        this.colaClientes = colaClientes;
    }

    Barbero(BlockingQueue<Cliente> colaClientes, String hiloNombre){
        super(hiloNombre);
        this.colaClientes = colaClientes;
    }

    @Override
    public void run(){
        try {
            while (running) {
                Cliente cliente = colaClientes.poll(); // si no hay contenido no bloquea
                if (cliente == null) {
                    System.out.println("Zzz ["+Thread.currentThread().getName()+"] SE HA DORMIDO");
                    while (colaClientes.isEmpty() && running) {
                        Thread.sleep(1000);
                    }
                    if(running)
                        System.out.println("☀ ["+Thread.currentThread().getName()+"] SE HA DESPERTADO");
                }else{
                    int tiempoAtendiendo = (int) (Math.random() * ((5 - 1) + 1));
                    System.out.println("["+Thread.currentThread().getName()+"] ATENDIENDO CLIENTE ["+cliente+"] POR ["+tiempoAtendiendo+"s]");
                    Thread.sleep(tiempoAtendiendo*1000);
                }
            }
        }catch(InterruptedException e){
            System.out.println("Error en el hilo del barbero.");
        }
    }

    public void finalizar(){
        this.running = false;
    }
}
