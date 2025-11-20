package UT02.montaniaRusa;

import java.lang.reflect.Array;
import java.util.List;
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
            while(running){
                System.out.println("LLENANDO VAGONES");

                // convertimos la cola a lista SIN modificarla
                List<GrupoPersonas> todosLosGrupos = new ArrayList<GrupoPersonas>(cola);

                // separamos grupos con discapacidad de las que no
                List<GrupoPersonas> conDiscapacidad = new ArrayList<GrupoPersonas>();
                List<GrupoPersonas> sinDiscapacidad = new ArrayList<GrupoPersonas>();
                for (GrupoPersonas gp : todosLosGrupos) {
                    if (gp.isConDiscapacidad()) {
                        conDiscapacidad.add(gp);
                    } else {
                        sinDiscapacidad.add(gp);
                    }
                }

                int discapacidadLibres = Main.ASIENTOS_DISCAPACIDAD;
                int totalesLibres = Main.ASIENTOS_TOTALES;
                List<GrupoPersonas> seleccionados = new ArrayList<>();
                int discPersonasSubidas = 0;

                // los grupos con discapacidad tienen prioridad
                for (GrupoPersonas d : conDiscapacidad) {
                    int tam = d.getCantidadPersonas();
                    if (tam <= discapacidadLibres && tam <= totalesLibres) {
                        seleccionados.add(d);
                        discapacidadLibres -= tam;
                        totalesLibres -= tam;
                        discPersonasSubidas += tam;
                    }
                }

                // Ahora los grupos sin discapacidad
                ArrayList<GrupoPersonas> listaFinal = new ArrayList<GrupoPersonas>();
                int sinDiscSubidos = 0;

                for (int i = 0; i < sinDiscapacidad.size() && totalesLibres > 0; i++) {
                    GrupoPersonas grupoActual = sinDiscapacidad.get(i);
                    if(grupoActual.getCantidadPersonas() < totalesLibres){
                        sinDiscSubidos += grupoActual.getCantidadPersonas();
                        totalesLibres -= grupoActual.getCantidadPersonas();
                    }
                }

                seleccionados.addAll(listaFinal);
                int asientosOcupados = discPersonasSubidas + sinDiscSubidos;

                // Remover los seleccionados de la cola (simula que subieron)
                for (GrupoPersonas g : seleccionados) {
                    cola.remove(g);
                }

                System.out.println("VAGONES ["+asientosOcupados+"/"+Main.ASIENTOS_TOTALES+"]. CON DISCAPACIDAD ["+discPersonasSubidas+"/"+Main.ASIENTOS_DISCAPACIDAD+"]");

                System.out.println("ATRACCION INICIADA");
                Thread.sleep(Main.TIEMPO_ATRACCION*1000);
                System.out.println("ATRACCION FINALIZADA");

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