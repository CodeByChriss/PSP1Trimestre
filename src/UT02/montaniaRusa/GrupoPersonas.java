package UT02.montaniaRusa;

public class GrupoPersonas {

    private int cantidadPersonas;
    private boolean conDiscapacidad;

    GrupoPersonas(){
        // Hay un 20% de probabilidad de que el grupo sea con discapacidad
        this.conDiscapacidad = Math.random() <= 0.20 ? true : false;

        // un grupo con discapacidad no puede superar el número de asientos reservados
        // para ellos porque si no nunca se subirian.
        // En la vida real, si fueran por ejemplo 10 personas con discapacidad,
        // deberian dividirse en grupos.
        int limitePersonas = this.conDiscapacidad ? Main.ASIENTOS_DISCAPACIDAD : 10;

        this.cantidadPersonas = (int) (Math.random() * limitePersonas) + 1;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public boolean isConDiscapacidad() {
        return conDiscapacidad;
    }

    public void setConDiscapacidad(boolean conDiscapacidad) {
        this.conDiscapacidad = conDiscapacidad;
    }
}
