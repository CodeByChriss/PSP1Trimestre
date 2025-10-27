package UT02.Parking;

public class Coche {
    private String matricula;
    private long horaEntrada;

    public Coche(String matricula) {
        this.matricula = matricula;
    }

    public static String generarMatricula() {
        String matricula = "";

        // los 4 números de las matrículas
        for(int i = 0; i<=3; i++){
            int num = (int)(Math.random()*10);
            matricula += String.valueOf(num);
        }
        matricula += "-";
        // obtenemos las 3 letras de las matrículas
        for(int i = 0; i<=2; i++){
            int num = (int)(Math.random()*26);
            char caracter = (char)(num+65); // el número 65 es el primer caracter en el CP 850
            matricula+=caracter;
        }

        return matricula;
    }

    public void registrarEntrada() {
        this.horaEntrada = System.currentTimeMillis();
    }

    public int calcularMinutosEnParking() { // simulamos que 1 segundo es 1 minutos
        long actual = System.currentTimeMillis() / 1000;
        int minutos = (int)(actual-(horaEntrada/1000));
        return minutos;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public long getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(long horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
}