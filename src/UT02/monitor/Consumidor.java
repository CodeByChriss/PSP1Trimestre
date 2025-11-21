package UT02.monitor;

public class Consumidor extends Thread{
    private Monitor<Integer> monitor;
    private volatile boolean running = true;

    public Consumidor(Monitor<Integer> monitor){
        this.monitor = monitor;
    }

    @Override
    public void run(){
        try {
            while(running){
                int a = monitor.take();
                System.out.println("Recogido el elemento ["+a+"]");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Hilo consumidor parado de forma bruta");
        }
    }

    public void finalizar(){
        this.running = false;
    }
}
