package UT02.Mensajes;

import java.util.concurrent.BlockingQueue;

public class HiloEmisor extends Thread{

    private BlockingQueue<Mensaje> cola;
    private volatile boolean running = true;

    HiloEmisor(BlockingQueue<Mensaje> cola){
        this.cola = cola;
    }

    @Override
    public void run(){
        try{
            while(running){
                Mensaje mensaje = new Mensaje("prueba",msgTypes.information);
                cola.put(mensaje);

                long segundos = (int)(Math.random()*10000);
                Thread.sleep(segundos);
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public void finalizar(){
        this.running = false;
    }
}