package it.urania.software;


public class NastroTrasportatore extends Orario {
    Utilita utilita = new Utilita();
    private int pesoPacco;


// con mod. random generiamo il tempo, il peso, il codice

    public NastroTrasportatore() {
        this.pesoPacco = utilita.pesoGenerato();
    }

    public NastroTrasportatore(String tempo, int pesoPacco) {
        super(tempo);
        this.pesoPacco = pesoPacco;
    }

    @Override
    public String toString() {
        return super.toString() + "\t\tPeso: " + Integer.toString(pesoPacco) + " kg";
    }
}
