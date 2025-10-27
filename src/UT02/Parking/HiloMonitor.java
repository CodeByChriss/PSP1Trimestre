package UT02.Parking;

public class HiloMonitor extends Thread{

    private int tiempoDormido;
    private volatile boolean running = true;

    public HiloMonitor(int segundos){
        this.tiempoDormido = segundos*1000;
    }

    @Override
    public void run(){
        try {
            while (running) {
                Thread.sleep(tiempoDormido);
                System.out.println("\nMonitoreo:"
                    +"\n\t Plazas ocupadas: "+Main.getPlazasOcupadas()
                    +"\n\t Coches que han entrado y coches que han salido: "+Main.getCochesEntraron()+" / "+Main.getCochesSalieron()
                    +"\n\t Recaudación acumulada: "+(Math.round(Main.getRecaudacionAcumulada()*100)/100.0)+"\n");
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public void finalizar(){
        this.running = false;
    }
}
