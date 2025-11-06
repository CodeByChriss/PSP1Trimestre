package UT02.Mensajes;

import java.util.concurrent.BlockingQueue;

public class HiloReceptor extends Thread {

    private BlockingQueue<Mensaje> cola;
    private volatile boolean running = true;

    HiloReceptor(BlockingQueue<Mensaje> cola){
        this.cola = cola;
    }

    @Override
    public void run(){
        try{
            while(running){
                long segundos = (int)(Math.random()*10000);
                Thread.sleep(segundos);

                Mensaje mensaje = cola.take();
                System.out.println(mensaje);
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public void finalizar(){
        this.running = false;
    }
}
