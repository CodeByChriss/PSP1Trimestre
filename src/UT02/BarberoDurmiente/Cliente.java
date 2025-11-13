package UT02.BarberoDurmiente;

public class Cliente {
    private String nombre;
    private int edad;

    Cliente(String nombre, int edad){
        this.nombre = nombre;
        this.edad = edad;
    }

    public static String getRandomName(){
        String[] nombres = {"Christian","Roberto Carlos","Cristiano Ronaldo","Maradona"};
        int idx = (int) (Math.random() * nombres.length);
        return nombres[idx];
    }

    public static int getRandomEdad(){
        return (int) (Math.random() * ((100 - 18) - 18));
    }

    @Override
    public String toString(){
        return this.nombre + ", " + this.edad;
    }
}
