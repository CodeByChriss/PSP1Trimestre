package UT02.Parking;

import java.util.concurrent.BlockingQueue;

public class HiloSalida extends Thread{
    private String nombre; // para diferenciar las salidas y poder mostrar en las salidas por terminal cual ha sido la salida por la que ha salido el coche
    private BlockingQueue<Coche> colaParking;
    private volatile boolean running = true;

    public HiloSalida(BlockingQueue<Coche> colaParking) {
        this.colaParking = colaParking;
    }

    public HiloSalida(String nombre,BlockingQueue<Coche> colaParking) {
        this.colaParking = colaParking;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            while(running) {
                // simular que cada coche se pasa entre 5 y 15 segundos reales
                int segundos = (int)(Math.random()*10000+5000);
                Thread.sleep(segundos);

                Coche coche = colaParking.take();

                String matricula = coche.getMatricula();
                int minutosEnParking = coche.calcularMinutosEnParking();
                double aPagar = Main.calcularCosteTotal(minutosEnParking);
                Main.agregarTarifaMatricula(matricula,aPagar);
                Main.sumarCochesSalieron(1);
                System.out.println("<- Salida ("+nombre+"): ["+matricula+"] | Estancia:  "+minutosEnParking+" min | Cote: "+aPagar+" €");
            }
        }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void finalizar() {
        this.running = false;
    }
}