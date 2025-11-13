package UT02.montaniaRusa;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    private final static int ASIENTOS_DISCAPACIDAD = 4;
    private final static int ASIENTOS_TOTALES = 25;
    private final static int TIEMPO_EJECUCION = 20;

    public static void main(String[]args){
        BlockingQueue<GrupoPersonas> cola = new LinkedBlockingQueue<GrupoPersonas>();

        MontaniaRusa montaniaRusa = new MontaniaRusa(cola);
        GeneradorGrupos generadorGrupos = new GeneradorGrupos(cola);

        montaniaRusa.start();
        generadorGrupos.start();

        try{
            Thread.sleep(TIEMPO_EJECUCION*1000);
        }catch(InterruptedException e){
            System.out.println("Error al domir hilo principal");
        }

        generadorGrupos.finalizar();
        try{
            generadorGrupos.join();
        }catch(InterruptedException e){
            System.out.println("Error join generador grupos");
        }
        generadorGrupos.interrupt();
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

    public static int getAsientosDiscapacidad(){
        return ASIENTOS_DISCAPACIDAD;
    }
}
