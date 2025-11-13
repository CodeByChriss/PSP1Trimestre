package UT02.BarberoDurmiente;

import java.util.concurrent.BlockingQueue;

public class EntradaCliente extends Thread{

    private BlockingQueue<Cliente> colaClientes;
    private volatile boolean running = true;

    EntradaCliente(BlockingQueue<Cliente> colaClientes){
        this.colaClientes = colaClientes;
    }

    EntradaCliente(BlockingQueue<Cliente> colaClientes, String nombre){
        super(nombre);
        this.colaClientes = colaClientes;
    }

    @Override
    public void run(){
        try {
            while (running) {
                Cliente cliente = new Cliente(Cliente.getRandomName(),Cliente.getRandomEdad());
                if(colaClientes.offer(cliente)){ // en caso de que no quede espacio en la cola no se queda bloqueado
                    System.out.println("["+cliente+"] HA ENTRADO POR LA ["+Thread.currentThread().getName()+"] PERSONAS EN LA SALA DE ESPERA: ["+colaClientes.size()+"]");
                }else{
                    System.out.println("["+cliente+"] SE HA IDO PORQUE LA SALA DE ESPERA ESTÁ LLENA");
                }

                int tiempoSleep = (int) (Math.random() * ((10 - 1) + 1));
                Thread.sleep(tiempoSleep*1000);
            }
        }catch(InterruptedException e){
            System.out.println("Error en el hilo del producto de clientes.");
        }
    }

    public void finalizar(){
        this.running = false;
    }
}
