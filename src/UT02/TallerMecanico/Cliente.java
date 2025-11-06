package UT02.TallerMecanico;

import java.util.concurrent.BlockingQueue;

public class Cliente implements Runnable{

    private BlockingQueue<Reparacion> cola;
    private String id;

    public Cliente(BlockingQueue<Reparacion> cola,String id){
        this.cola = cola;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            int reparacionesGenerar = (int) ((Math.random() * (3 - 1 + 1)) + 1);
            System.out.println(id + " llega al taller con " + reparacionesGenerar + " reparaciones.");
            while (reparacionesGenerar > 0) {
                // generamos la reparación de forma aleatoria
                int reparacionIndex = (int) ((Math.random() * TallerMecanico.TIPOS_REPARACIONES.length));
                Reparacion reparacion = new Reparacion(TallerMecanico.TIPOS_REPARACIONES[reparacionIndex], TallerMecanico.TIEMPOS_ESPERA[reparacionIndex], TallerMecanico.PRECIOS_REPARACIONES[reparacionIndex]);
                System.out.println(id+" solicita: "+reparacion.getTipo()+" ("+reparacion.getTiempo()+"s, "+reparacion.getPrecio()+"€)");
                cola.put(reparacion);

                // esperamos un tiempo para volver a pedir una reparación
                int tiempo = (int) (Math.random() * 1000);
                Thread.sleep(tiempo);

                reparacionesGenerar--;
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}