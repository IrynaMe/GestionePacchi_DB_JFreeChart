package it.urania.software;

public class Orario {
    private int ore, minuti, secondi;
    private String tempo;


    public Orario(int ore, int minuti, int secondi) {
        this.ore = ore;
        this.minuti = minuti;
        this.secondi = secondi;
    }
    public Orario(String tempo) {
        this.tempo = tempo;
    }
    public Orario() {
    }
    public int getOre() {
        return ore;
    }

    @Override
    public String toString() {
        return "\tOre di registrazione: " + tempo;
    }
}