package UT02.monitor;

public class PrincipalUsoMonitor {
    public static void main(String[] args) {
        Monitor<Integer> monitor = new Monitor<Integer>();

        Productor productor = new Productor(monitor);
        Consumidor consumidor = new Consumidor(monitor);

        Terminal terminal = new Terminal(monitor);

        productor.start();
        consumidor.start();
        terminal.start();

        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            System.out.println("Error en el hilo principal");
        }

        productor.finalizar();
        consumidor.finalizar();
        terminal.finalizar();

        productor.interrupt();
        consumidor.interrupt();
        terminal.interrupt();

        System.out.println("Simulación finalizada");
    }
}