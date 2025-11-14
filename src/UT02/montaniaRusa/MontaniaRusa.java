package UT02.montaniaRusa;

import java.util.ArrayList;
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
            ArrayList<GrupoPersonas> personasEnAtraccion = new ArrayList<GrupoPersonas>();
            while(running){
                System.out.println("LLENANDO VAGONES");
                // agregar metodo para saber cual es la mejor forma de usar todos los asientos de los vagones
                System.out.println("VAGONES ["+0+"/"+Main.ASIENTOS_TOTALES+"]. CON DISCAPACIDAD ["+0+"/"+Main.ASIENTOS_DISCAPACIDAD+"]");

                System.out.println("ATRACCION INICIADA");
                Thread.sleep(Main.TIEMPO_ATRACCION*1000);
                System.out.println("ATRACCION FINALIZADA");

                personasEnAtraccion.clear();
                System.out.println("VAGONES VACIOS");
            }
        }catch(InterruptedException e){
            System.out.println("Error, hilo montaniaRusa interrumpido");
        }
    }

    public void finalizar(){
        this.running = false;
    }
}