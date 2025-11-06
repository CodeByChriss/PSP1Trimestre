package UT02.Mensajes;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Mensaje> cola = new LinkedBlockingQueue<Mensaje>();

        HiloEmisor hiloEmisor = new HiloEmisor(cola);
        HiloReceptor hiloReceptor = new HiloReceptor(cola);

        hiloEmisor.start();
        hiloReceptor.start();

        try {
            Thread.sleep(20000); // dormimos un rato para dejar tiempo a la simulación
        }catch(InterruptedException e){
            System.out.println("Error al dormir, simulación fallida.");
        }

        hiloEmisor.finalizar();
        hiloReceptor.finalizar();

        hiloEmisor.interrupt();
        hiloReceptor.interrupt();

        System.out.println("Simulación finalizada.");
    }
}