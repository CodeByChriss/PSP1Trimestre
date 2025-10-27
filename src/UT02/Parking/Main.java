package UT02.Parking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    private final static int LIMITE_PARKING = 100;
    private final static float TARIFA_MINUTO = (float)0.05;
    // variables global
    private static double recaudacionFinal;
    private static int cochesEntraron;
    private static int cochesSalieron;

    public static void main(String[] args) throws InterruptedException {
        // variable
        recaudacionFinal = 0.0;
        cochesEntraron = 0;
        cochesSalieron = 0;

        BlockingQueue<Coche> colaParking = new LinkedBlockingQueue<Coche>(LIMITE_PARKING);

        HiloEntrada entradaParking = new HiloEntrada(colaParking);
        HiloSalida salidaParking = new HiloSalida(colaParking);

        // inicio del programa
        entradaParking.start();
        salidaParking.start();

        Thread.sleep(20000); // 20 segundos

        entradaParking.finalizar();
        entradaParking.finalizar();

        System.out.println("\nDeteniendo simulación...");

        salidaParking.interrupt();
        salidaParking.interrupt();

        System.out.println("Resumen final:\n"
                + "\t Coches que entraron: " + cochesEntraron + "\n"
                + "\t Coched que salieron: " + cochesSalieron + "\n"
                + "\t Recaudación total: " + (Math.round(recaudacionFinal*100)/100) + " €\n" // redondeamos a dos decimales
                + "\t Coches restantes en parking: " + colaParking.size());

        System.out.println("Fin de la simulación.");
    }

    public static float calcularCosteTotal(int minutos) {
        return minutos * TARIFA_MINUTO;
    }

    public static void sumarRecaudacionFinal(float suma) {
        recaudacionFinal += suma;
    }

    public static void sumarCochesEntraron(int suma) {
        cochesEntraron += suma;
    }

    public static void sumarCochesSalieron(int suma) {
        cochesSalieron += suma;
    }
}