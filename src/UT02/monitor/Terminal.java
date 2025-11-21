package UT02.monitor;

public class Terminal extends Thread{
    private Monitor<Integer> monitor;
    private volatile boolean running = true;

    public Terminal(Monitor<Integer> monitor){
        this.monitor = monitor;
    }

    @Override
    public void run(){
        try{
            while(running){
                System.out.println("El tamaño actual del monitor es de: "+monitor.size());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Hilo terminal parado de forma bruta");
        }
    }

    public void finalizar(){
        this.running = false;
    }
}
