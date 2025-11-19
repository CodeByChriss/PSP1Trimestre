package UT02.montaniaRusa;

import java.util.concurrent.BlockingQueue;

public class GeneradorGrupos extends Thread {
    private BlockingQueue<GrupoPersonas> cola;
    private volatile boolean running = true;

    GeneradorGrupos(BlockingQueue<GrupoPersonas> cola){
        this.cola = cola;
    }

    @Override
    public void run(){
        try{
            while(running){
                // cantidad de grupos que se van a generar de una tanda
                // (simulamos que van a entrar X grupos al parque de atracciones)
                int cantidadGrupos = (int) (Math.random() * (12 - 5 + 1)) + 5;
                for(int i = 0; i<cantidadGrupos; i++){
                    GrupoPersonas grupoPersonas = new GrupoPersonas();
                    cola.put(grupoPersonas);
                    System.out.println("ENTRANDO GRUPO DE ["+grupoPersonas.getCantidadPersonas()+"] ["+(grupoPersonas.isConDiscapacidad() ? "CON" : "SIN")+"] DISCAPACIDAD.");
                }
                int tiempoSleep = (int) (Math.random() * ((20 - 5) + 5));
                Thread.sleep(tiempoSleep*1000);
            }
        }catch(InterruptedException e){
            System.out.println("Error, hilo generador grupos interrumpido");
        }
    }

    public void finalizar(){
        this.running = false;
    }
}