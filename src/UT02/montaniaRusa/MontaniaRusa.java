package UT02.montaniaRusa;

import java.util.concurrent.BlockingQueue;

public class MontaniaRusa extends Thread{

    private BlockingQueue<GrupoPersonas> cola;
    private volatile boolean running = true;

    MontaniaRusa(BlockingQueue<GrupoPersonas> cola){
        this.cola = cola;
    }

    @Override
    public void run(){
        try{
            while(running){
                Thread.sleep(1);
            }
        }catch(InterruptedException e){
            System.out.println("Error, hilo montaniaRusa interrumpido");
        }
    }

    public void finalizar(){
        this.running = false;
    }
}