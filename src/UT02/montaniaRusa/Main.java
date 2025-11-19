package UT02.montaniaRusa;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public final static int ASIENTOS_DISCAPACIDAD = 4;
    public final static int ASIENTOS_TOTALES = 25;
    public final static int TIEMPO_ATRACCION = 7; // tiempo que van a estar subidos a la atracción
    private final static int TIEMPO_EJECUCION = 20;

    public static void main(String[]args){
        BlockingQueue<GrupoPersonas> cola = new LinkedBlockingQueue<GrupoPersonas>();

        GeneradorGrupos generadorGrupos = new GeneradorGrupos(cola);
        MontaniaRusa montaniaRusa = new MontaniaRusa(cola);

        generadorGrupos.start();
        montaniaRusa.start();

        // Dejamos un rato de simulación
        try{
            Thread.sleep(TIEMPO_EJECUCION*1000);
        }catch(InterruptedException e){
            System.out.println("Error al domir hilo principal");
        }

        // Terminamos de generar grupos de personas
        generadorGrupos.finalizar();
        try{
            generadorGrupos.join();
        }catch(InterruptedException e){
            System.out.println("Error join generador grupos");
        }
        generadorGrupos.interrupt();
        // Esperamos a que termine toda la cola de personas para que nadie se pierda la magnifica experiencia
        while(!cola.isEmpty()){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.out.println("Error al domir esperando a vaciar cola");
            }
        }
        montaniaRusa.finalizar();
        try{
            montaniaRusa.join();
        }catch(InterruptedException e){
            System.out.println("Error join montaña rusa");
        }
        montaniaRusa.interrupt();

        System.out.println("Simulación finalizada.");
    }
}
