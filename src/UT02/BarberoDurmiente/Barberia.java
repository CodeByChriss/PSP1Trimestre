package UT02.BarberoDurmiente;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// MAIN
public class Barberia {

    private final static int TOTAL_SALA_ESPERA = 5;
    private final static int TOTAL_ENTRADAS_PARA_CLIENTES = 5;
    private final static int HORARIO_BARBERIA = 20; // en segundos
    private final static int BARBEROS = 1;

    public static void main(String[] args) {
        BlockingQueue<Cliente> colaClientes = new LinkedBlockingQueue<>(TOTAL_SALA_ESPERA);

        // CREAMOS ENTRADAS Y BARBEROS
        ArrayList<EntradaCliente> entradasClientes = new ArrayList<EntradaCliente>();
        for(int i = 0; i<TOTAL_ENTRADAS_PARA_CLIENTES; i++){
            EntradaCliente entradaCliente = new EntradaCliente(colaClientes,"Entrada-"+(i+1));
            entradaCliente.start();
            entradasClientes.add(entradaCliente);
        }
        ArrayList<Barbero> barberos = new ArrayList<Barbero>();
        for(int i = 0; i<BARBEROS; i++){
            Barbero barbero = new Barbero(colaClientes,"Barbero-"+(i+1));
            barbero.start();
            barberos.add(barbero);
        }

        // SIMULAMOS EL TIEMPO QUE LA BARBERIA ESTÁ ABIERTA
        try{
            Thread.sleep(HORARIO_BARBERIA*1000);
        }catch(InterruptedException e){
            System.out.println("Error al dormir el hilo principal.");
        }

        // FINALIZAMOS LAS ENTRADAS Y LOS BARBEROS
        for(EntradaCliente ec : entradasClientes){
            ec.finalizar();
            try{
                ec.join();
            }catch(InterruptedException e){
                System.out.println("Error en el join al barbero");
            }
            ec.interrupt();
        }
        while(!colaClientes.isEmpty()){ // comprobamos si hay clientes esperando y los atendemos antes de cerrar
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.out.println("Error esperando a que la sala de espera se vacie");
            }
        }
        for(Barbero b : barberos){
            b.finalizar();
            try{
                b.join();
            }catch(InterruptedException e){
                System.out.println("Error en el join al barbero");
            }
            b.interrupt();
        }

        System.out.println("Simulación finalizada.");
    }
}