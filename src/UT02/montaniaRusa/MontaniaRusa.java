package UT02.montaniaRusa;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class MontaniaRusa extends Thread{

    private BlockingQueue<GrupoPersonas> cola;
    private volatile boolean running = true;

    MontaniaRusa(BlockingQueue<GrupoPersonas> cola){
        this.cola = cola;
    }

    @Override
    public void run(){
        try{
            ArrayList<GrupoPersonas> personasEnAtraccion = new ArrayList<GrupoPersonas>();
            while(running){
                System.out.println("LLENANDO VAGONES");

                ArrayList<GrupoPersonas> candidatos = new ArrayList<GrupoPersonas>();
                for(int i = 0; i<cola.size(); i++){
                    GrupoPersonas gp = cola.take();
                    candidatos.add(gp);
                }
                ArrayList<GrupoPersonas> mejorLista = new ArrayList<GrupoPersonas>();
                int mejorSuma = 0;

                for (int mask = 0; mask < (1 << cola.size()); mask++) {
                    int sumaActual = 0;
                    ArrayList<GrupoPersonas> actual = new ArrayList<GrupoPersonas>();

                    for (int i = 0; i < cola.size(); i++) {
                        if ((mask & (1 << i)) != 0) {
                            sumaActual += candidatos.get(i).getCantidadPersonas();
                            actual.add(candidatos.get(i));
                        }
                    }

                    if (sumaActual <= Main.ASIENTOS_TOTALES && sumaActual > mejorSuma) {
                        mejorSuma = sumaActual;
                        System.out.println("-----ESTOY AQUIIIII");
                        mejorLista = new ArrayList<>(actual);
                    }
                }

                System.out.println("VAGONES ["+mejorSuma+"/"+Main.ASIENTOS_TOTALES+"]. CON DISCAPACIDAD ["+0+"/"+Main.ASIENTOS_DISCAPACIDAD+"]");

                System.out.println("ATRACCION INICIADA");
                Thread.sleep(Main.TIEMPO_ATRACCION*1000);
                System.out.println("ATRACCION FINALIZADA");

                personasEnAtraccion.clear();
                System.out.println("VAGONES VACIOS");
            }
        }catch(InterruptedException e){
            System.out.println("Error, hilo montaniaRusa interrumpido");
        }
    }

    public void finalizar(){
        this.running = false;
    }
}