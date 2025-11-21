package UT02.monitor;

public class Productor extends Thread{
    private Monitor<Integer> monitor;
    private volatile boolean running = true;

    public Productor(Monitor<Integer> monitor){
        this.monitor = monitor;
    }

    @Override
    public void run(){
        try {
            while(running){
                int a = (int)(Math.random() * 10) +1;
                monitor.put(a);
                System.out.println("Introducido el elemento ["+a+"]");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Hilo productor parado de forma bruta");
        }
    }

    public void finalizar(){
        this.running = false;
    }
}
