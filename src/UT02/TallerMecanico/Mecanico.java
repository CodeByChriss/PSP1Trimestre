package UT02.TallerMecanico;

import java.util.concurrent.BlockingQueue;

public class Mecanico implements Runnable{

    private BlockingQueue<Reparacion> cola;
    private String id;
    private int totalDeTrabajos;
    private double recaudacionPersonal;
    private volatile boolean running = true;

    public Mecanico(BlockingQueue<Reparacion> cola, String id){
        this.cola = cola;
        this.id = id;
    }

    @Override
    public void run() {
        try{
            while(running) {
                Reparacion reparacion = cola.take();
                totalDeTrabajos += 1;
                recaudacionPersonal += reparacion.getPrecio();
                System.out.println(id+" repara: "+reparacion.getTipo()+" ("+reparacion.getTiempo()+"s, "+reparacion.getPrecio()+")");
                Thread.sleep((long) reparacion.getTiempo()*1000);
                System.out.println(id+" finalizó: "+reparacion.getTipo()+" ("+reparacion.getTiempo()+"s, "+reparacion.getPrecio()+")");
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public int getTotalDeTrabajos(){
        return totalDeTrabajos;
    }

    public double getRecaudacionPersonal(){
        return recaudacionPersonal;
    }

    public void finalizar(){
        this.running = false;
    }
}
