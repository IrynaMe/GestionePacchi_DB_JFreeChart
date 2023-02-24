package it.urania.software;

import java.util.Random;

public class Utilita {

    public int pesoGenerato() {
        //genero peso tra 1grammo e --> kg
        int rangeMin = 1;
        int rangeMax = 20000;
        //genera i numero tra 1 e 50000
        int pesoRandom = (int) ((Math.random() * rangeMax) + rangeMin);

        //se generato < 1 kg trasformo in kg
        if (pesoRandom < 1000) {
            pesoRandom = 1000;
        }
        //se generato >10kg ->eccezione e segnala anomalia e non la inserisce nella tabella
        return pesoRandom / 1000;//->in kg
    }

    //generatore di orario simulato: dalle 8 alle 24->hh:mm:ss
    public String generaTempo() {
        String tempo = "";
        int hh = 0;
        int mm = 0;
        int ss = 0;
        //genero hh
        hh = (int) (Math.random() * (17) + 8);//da 8 a 24
        //genero mm
        mm = (int) (Math.random() * 61);//da 0 a 60
        //genero ss
        ss = (int) (Math.random() * 61);//da 0 a 60
        int oreMinSec[] = {hh, mm, ss};

        tempo = String.format("%02d:%02d:%02d", hh, mm, ss);
        //tempoStringhe.add(tempo);
        return tempo;
    }

    //method per provare se funzione metodo codiceControllato per rigenerare il codice se è gia presente in db
  /*  public String codiceGeneratoCorto() {
        Random random = new Random();
        String alfabeto = "abcdefghijklmnopqrstuvwxyz";
        //   int numero = (int) (Math.random() * 90000) + 10000; //da 10000 a 99999
        String uno = String.valueOf(alfabeto.charAt((int) (Math.random() * 25))).toUpperCase();
        int numero = random.nextInt(1, 10); //1-9

        String codice = uno + "-" + numero;
        //  System.out.println("TEST CODICE GENERATRO: " + codice);
        return codice;
    }*/

    public String codiceGenerato() {
        Random random = new Random();
        String alfabeto = "abcdefghijklmnopqrstuvwxyz";
        //   int numero = (int) (Math.random() * 90000) + 10000; //da 10000 a 99999
        String uno = String.valueOf(alfabeto.charAt((int) (Math.random() * 25))).toUpperCase();
        String due = String.valueOf(alfabeto.charAt((int) (Math.random() * 25))).toUpperCase();
        int numero = random.nextInt(1, 100000); //

        String codice = uno + due + "-" + String.format("%05d", numero);

        return codice;
    }

}//