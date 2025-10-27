package UT02.Parking;

import java.util.concurrent.BlockingQueue;

public class HiloSalida extends Thread{
    private BlockingQueue<Coche> colaParking;
    private volatile boolean running = true;

    public HiloSalida(BlockingQueue<Coche> colaParking) {
        this.colaParking = colaParking;
    }

    @Override
    public void run() {
        try {
            while(running) {
                Coche coche = colaParking.take();

                // simular que cada coche se pasa entre 5 y 15 segundos reales
                int segundos = (int)(Math.random()*10000+5000);
                Thread.sleep(segundos);

                int minutosEnParking = coche.calcularMinutosEnParking();
                float aPagar = Main.calcularCosteTotal(minutosEnParking);
                Main.sumarRecaudacionFinal(aPagar);
                Main.sumarCochesSalieron(1);
                System.out.println("<- Salida: ["+coche.getMatricula()+"] | Estancia:  "+minutosEnParking+" min | Cote: "+aPagar+" €");
            }
        }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void finalizar() {
        this.running = false;
    }
}