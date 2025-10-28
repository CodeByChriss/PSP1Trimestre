package UT02.Parking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    private final static int LIMITE_PARKING = 100;
    private final static double TARIFA_MINUTO = 0.05;
    // variables global
    private static double recaudacionFinal;
    private static int cochesEntraron;
    private static int cochesSalieron;
    private static BlockingQueue<Coche> colaParking;
    private static ConcurrentHashMap<String,Double> tarifasPagadasPorMatricula;
    private final static String nombreFichero = "pagosPorMatricula.txt";

    public static void main(String[] args) throws InterruptedException {
        // variable
        recaudacionFinal = 0.0;
        cochesEntraron = 0;
        cochesSalieron = 0;

        colaParking = new LinkedBlockingQueue<Coche>(LIMITE_PARKING);
        tarifasPagadasPorMatricula = new ConcurrentHashMap<String,Double>();

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

        // mostrar por consola el contenido del HashMap
        // for(String matricula : tarifasPagadasPorMatricula.keySet()){
        //     System.out.println(matricula+" "+tarifasPagadasPorMatricula.get(matricula));
        // }

        // almacenamos el HashMap en un fichero txt
        try{
            File fichero = new File(nombreFichero);
            if(fichero.exists()) {// comprobamos si el fichero ya existe
                guardarEnFichero(fichero,false);
            }else{
                if(fichero.createNewFile()){ // si no existe lo intentamos crear
                    System.out.println("Fichero "+nombreFichero+" creado.");
                    guardarEnFichero(fichero,true);
                }else{
                    System.out.println("Error al crear el fichero. La información no ha sido guardada");
                }
            }
        }catch(IOException e){
            System.out.println("Error al guardar en fichero.");
        }
    }

    public static double calcularCosteTotal(int minutos) {
        return Math.round((minutos * TARIFA_MINUTO)*100)/100.0;
    }

    public synchronized static void sumarCochesEntraron(int suma) {
        cochesEntraron += suma;
    }

    public synchronized static void sumarCochesSalieron(int suma) {
        cochesSalieron += suma;
    }

    public synchronized static void agregarTarifaMatricula(String matricula, Double tarifa){
        tarifasPagadasPorMatricula.put(matricula, tarifa);
        recaudacionFinal += tarifa;
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

    public static void guardarEnFichero(File fichero,boolean ficheroNuevo){
        try {
            FileWriter fw = new FileWriter(fichero,true); // true para no sobreescribir
            if(ficheroNuevo) fw.write("matricula | pagado\n"); // si el fichero se acaba de crear agregamos una línea para saber el orden de la información que se guarda
            for (String matricula : tarifasPagadasPorMatricula.keySet()) {
                double pagado = tarifasPagadasPorMatricula.get(matricula);
                fw.write(matricula + " | " + String.valueOf(pagado) + "€\n");
            }
            fw.close();
            System.out.println("Información guardada en " + nombreFichero);
        }catch(IOException e){
            System.out.println("Error al guardar la información en el fichero.");
        }
    }
}