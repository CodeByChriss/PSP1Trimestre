package UT02.Parking;

import java.util.concurrent.BlockingQueue;

public class HiloEntrada extends Thread {
    private BlockingQueue<Coche> colaParking;
    private volatile boolean running = true;

    public HiloEntrada(BlockingQueue<Coche> colaParking) {
        this.colaParking = colaParking;
    }

    @Override
    public void run() {
        try {
            while(running) {
                Coche coche = new Coche(Coche.generarMatricula());
                colaParking.put(coche);
                coche.registrarEntrada();
                Main.sumarCochesEntraron(1);
                System.out.println("-> Entrada: ["+coche.getMatricula()+"] | Plazas ocupadas: "+colaParking.size()+"/"+(colaParking.size()+colaParking.remainingCapacity()));

                // dar un tiempo entre coche y coche para poder ver las salidas por consola y evitar que haya 5 millones de coches
                //int segundos = (int)(Math.random()*10000+5000);
                //Thread.sleep(segundos);
            }
        }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void finalizar() {
        this.running = false;
    }
}