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
    private static BlockingQueue<Coche> colaParking;

    public static void main(String[] args) throws InterruptedException {
        // variable
        recaudacionFinal = 0.0;
        cochesEntraron = 0;
        cochesSalieron = 0;

        colaParking = new LinkedBlockingQueue<Coche>(LIMITE_PARKING);

        HiloMonitor hiloMonitor = new HiloMonitor(2);

        HiloEntrada entradaParking1 = new HiloEntrada("Entrada 1",colaParking);
        HiloEntrada entradaParking2 = new HiloEntrada("Entrada 2",colaParking);
        HiloSalida salidaParking1 = new HiloSalida("Salida 1",colaParking);
        HiloSalida salidaParking2 = new HiloSalida("Salida 2",colaParking);
        HiloSalida salidaParking3 = new HiloSalida("Salida 3",colaParking);

        // inicio del programa
        hiloMonitor.start();
        entradaParking1.start();
        entradaParking2.start();
        salidaParking1.start();
        salidaParking2.start();
        salidaParking3.start();

        Thread.sleep(20000); // 20 segundos

        hiloMonitor.finalizar();
        entradaParking1.finalizar();
        entradaParking2.finalizar();
        salidaParking1.finalizar();
        salidaParking2.finalizar();
        salidaParking3.finalizar();

        System.out.println("\nDeteniendo simulación...");

        hiloMonitor.interrupt();
        entradaParking1.interrupt();
        entradaParking2.interrupt();
        salidaParking1.interrupt();
        salidaParking2.interrupt();
        salidaParking3.interrupt();

        System.out.println("Resumen final:\n"
                + "\t Coches que entraron: " + cochesEntraron + "\n"
                + "\t Coches que salieron: " + cochesSalieron + "\n"
                + "\t Recaudación total: " + (Math.round(recaudacionFinal*100)/100) + " €\n" // redondeamos a dos decimales
                + "\t Coches restantes en parking: " + colaParking.size());

        System.out.println("Fin de la simulación.");
    }

    public static float calcularCosteTotal(int minutos) {
        return minutos * TARIFA_MINUTO;
    }

    public synchronized static void sumarRecaudacionFinal(float suma) {
        recaudacionFinal += suma;
    }

    public synchronized static void sumarCochesEntraron(int suma) {
        cochesEntraron += suma;
    }

    public synchronized static void sumarCochesSalieron(int suma) {
        cochesSalieron += suma;
    }

    public static int getPlazasOcupadas(){
        return colaParking.size();
    }

    public static Double getRecaudacionAcumulada(){
        return recaudacionFinal;
    }

    public static int getCochesEntraron(){
        return cochesEntraron;
    }

    public static int getCochesSalieron(){
        return cochesSalieron;
    }
}