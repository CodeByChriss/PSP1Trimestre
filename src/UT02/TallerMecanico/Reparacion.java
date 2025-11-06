package UT02.TallerMecanico;

public class Reparacion {
    private String tipo;
    private int tiempo;
    private double precio;

    public Reparacion(String tipo, int tiempo, double precio){
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.precio = precio;
    }

    @Override
    public String toString(){
        return "Tipo: ["+tipo+"] Tiempo: ["+tiempo+"] Precio: ["+precio+"]";
    }

    public String getTipo(){
        return tipo;
    }

    public int getTiempo(){
        return tiempo;
    }

    public double getPrecio(){
        return precio;
    }
}