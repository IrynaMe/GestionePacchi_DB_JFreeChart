package it.urania.software;

import java.util.HashMap;
import java.util.Set;

public class pesoEccezione extends Throwable {

    private static HashMap<Integer, String> pacchiEsclusi = new HashMap<>();
    static int indice = 1;

    public pesoEccezione(String segnalatoPesoEccesivo) {
        System.out.println("Segnalato un pacco con peso eccessivo, NON è stato inserito nella tabella");
    }

    public static void pacchiSegnalati(NastroTrasportatore obj) {
        String pesoOre = obj.toString();
        pacchiEsclusi.put(indice, pesoOre);
        indice++;
    }

    public static int stampaEsclusi() {
        int quantitaEsclusi = 0;
        Set<Integer> keySet = pacchiEsclusi.keySet();
        if (pacchiEsclusi.size() > 0) {
            System.out.println("I pacchi esclusi sono: ");
            for (Integer indice : keySet) {
                System.out.println("Pacco " + indice + pacchiEsclusi.get(indice).toString());
                quantitaEsclusi++;
            }
        } else {
            System.out.println("Non è stato registrato nessun pacco escluso ");
        }
        return quantitaEsclusi;
    }

}
