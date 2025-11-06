package UT02.TallerMecanico;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TallerMecanico {

    public static final String[] TIPOS_REPARACIONES = {"cambio de aceite","frenos","ITV","puerta rota"};
    public static final int[] TIEMPOS_ESPERA = {2,4,1,3}; // en segundos
    public static final double[] PRECIOS_REPARACIONES = {14.99,83.89,23.49,5.0}; // en segundos

    public static void main(String[] args) {
        BlockingQueue<Reparacion> cola = new LinkedBlockingQueue<Reparacion>();

        // Creamos todos los clientes y sus hilos
        int NUMERO_DE_CLIENTES = 2;
        HashMap<Cliente,Thread> clientes = new HashMap<Cliente,Thread>();
        for(int i = 0; i<NUMERO_DE_CLIENTES; i++){
            Cliente c = new Cliente(cola, "Cliente-"+(i+1));
            Thread hiloCliente = new Thread(c);
            hiloCliente.start();
            clientes.put(c,hiloCliente);
        }

        // Creamos todos los mecanicos y sus hilos y los arrancamos
        int NUMERO_DE_MECANICOS = 2;
        HashMap<Mecanico,Thread> mecanicos = new HashMap<Mecanico,Thread>();
        for(int i = 0; i<NUMERO_DE_MECANICOS; i++){
            Mecanico m = new Mecanico(cola, "Mecanico-"+(i+1));
            Thread hiloMecanico = new Thread(m);
            hiloMecanico.start();
            mecanicos.put(m,hiloMecanico);
        }

        // No detenemos la simulación hasta que todos los mecanicos hayan terminado su trabajo
        boolean finalizados = false;
        while(!finalizados){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.out.println("Ha ocurrido un error en la espera.");
            }
            // comprobamos si todos los mecanicos han terminado su trabajo
            if(cola.isEmpty()){
                int cntMecanicosEsperando = 0;
                for(Thread t : mecanicos.values()){
                    Thread.State state = t.getState();
                    if(state == Thread.State.WAITING){
                        cntMecanicosEsperando++;
                    }
                }
                if(cntMecanicosEsperando == mecanicos.size())
                    finalizados = true;
            }
        }

        // Detenemos todos los mecanicos
        for(Mecanico m : mecanicos.keySet()){
            Thread t = mecanicos.get(m);
            m.finalizar();
            t.interrupt();
        }

        System.out.println("RESUMEN DEL DÍA");
        int trabajosRealizados = 0;
        double facturacionTotal = 0.0;
        for(Mecanico m : mecanicos.keySet()){
            trabajosRealizados += m.getTotalDeTrabajos();
            facturacionTotal += m.getRecaudacionPersonal();
        }
        System.out.println("\t Trabajos realizados: "+trabajosRealizados);
        System.out.println("\t Facturación total: "+facturacionTotal+" €");
        System.out.println("Fin de la simulación.");
    }
}
