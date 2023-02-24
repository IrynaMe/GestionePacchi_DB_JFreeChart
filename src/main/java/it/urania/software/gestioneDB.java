package it.urania.software;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class gestioneDB {

    NastroTrasportatore nastroTrasportatore;
    Connection conn;
    Properties properties;
    PreparedStatement preparedStatement;

    public Connection conn(String user, String password) {
        properties = null;
        conn = null;
        try {
            FileReader reader = new FileReader("config.properties");
            properties = new Properties();
            properties.load(reader);
            String url = properties.getProperty("url") + "://" +
                    properties.getProperty("host") + ":" +
                    properties.getProperty("porta") + "/" + properties.getProperty("database");

            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connesso a db con successo");
            } else {
                System.out.println("NON connesso a db");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problema in lettura file: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Non posso fare la connessione alla db: " + e.getMessage());
        }
        return conn;
    }

    public void inserimento(int quantitaPacchi) {
        String[] tempoStringhe = new String[quantitaPacchi];
        Utilita utilita = new Utilita();
        for (int i = 0; i < tempoStringhe.length; i++) {
            tempoStringhe[i] = utilita.generaTempo();
        }
        Arrays.sort(tempoStringhe);
        // Collections.sort(tempoStringhe);
        for (int i = 0; i < quantitaPacchi; i++) {

            //  System.out.println("TEST tempostringe: "+tempoStringhe);
            String tempo = tempoStringhe[i];
            int peso = utilita.pesoGenerato();
            //  Orario orario=new Orario(tempo);
            NastroTrasportatore pacco = new NastroTrasportatore(tempo, peso);
            if (peso < 10) {
                String codice = codiceControllato();
                preparedStatement = null;
                String sql = "INSERT INTO gest_pacchi (tempo,peso,codice) values (?,?,?)";
                // System.out.println("test print " + sql);
                try {
                    //eseguo inrtuzione sql con prepared statement e metto i dati nella tabella
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, tempo);
                    preparedStatement.setInt(2, peso);
                    preparedStatement.setString(3, codice);
                    System.out.println("I dati sono inseriti nella tabella: " + tempo + ", " + peso + ", " + codice);
                    //eseguo prep statement
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Non posso collegare a db " + e.getMessage());
                } finally {
                    // chiudo prep statement
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        System.out.println("non posso chiudere preparedStatement: " + e.getMessage());
                    }
                }
            }
            if (peso > 10)
                try {
                    throw new pesoEccezione("Segnalato un pacco con peso eccessivo: "
                            + peso + ", NON è stato inserito nella tabella");
                } catch (pesoEccezione e) {
                    pesoEccezione.pacchiSegnalati(pacco);
                }
        }
    }

    public ArrayList<String> estraiPacchiPerOra() {
        ArrayList<Orario> orarioMio = new ArrayList<>();
        ArrayList<String> pacchiPerOra = new ArrayList<>();
        int quantita = 0;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sql = "SELECT * FROM gest_pacchi";

        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String tempo = resultSet.getString("tempo");
                String[] tempoArr = tempo.split(":");
                if (tempoArr[0].charAt(0) == '0') {
                    tempoArr[0] = tempoArr[0].substring(1); //cancello 0 da ore se la stringa ha formatto 08:11:22
                }
                int ore = Integer.parseInt(tempoArr[0]);
                int minuti = Integer.parseInt(tempoArr[1]);
                int secondi = Integer.parseInt(tempoArr[2]);
                Orario orario = new Orario(ore, minuti, secondi);
                orarioMio.add(orario);
            }

            for (int i = 8; i < 24; i++) {
                int oreAttuale = i;
                quantita = 0;
                for (int j = 0; j < orarioMio.size(); j++) {
                    if (orarioMio.get(j).getOre() == (oreAttuale)) {
                        quantita++;
                    }
                }
                if (quantita != 0) {
                    String risposta = "Dalle " + i + " alle " + (i + 1) + " -> " + quantita;
                    pacchiPerOra.add(risposta);
                }
            }
            // Collections.sort(pacchiPerOra);
            for (String risposta : pacchiPerOra) {
                System.out.println(risposta);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Non posso leggere i dati: " + e.getMessage());
        }
        return pacchiPerOra;
    }


    public int contaPachiPesanti() {
        int quantita = 0;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sql = "SELECT Count(*) FROM gest_pacchi where peso > 5";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Quantità dei pacchi > 5 kg: " + resultSet.getInt(1));
                quantita = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Non posso leggere i dati: " + e.getMessage());
        }
        return quantita;
    }

    public int contaTutti() {
        int quantitaTot = 0;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sql = "SELECT Count(*) FROM gest_pacchi";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Quantità dei pacchi nella tabella: " + resultSet.getInt(1));
                quantitaTot = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Non posso leggere i dati: " + e.getMessage());
        }
        return quantitaTot;
    }

    public String codiceControllato() {
        String codice = "";
        int quantita = 0;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Utilita utilita = new Utilita();
        do {
            codice = utilita.codiceGenerato();
            String sql = "SELECT Count(*) FROM gest_pacchi where codice = '" + codice + "'";
            try {
                preparedStatement = conn.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    quantita = resultSet.getInt(1);
                    if (quantita == 1) {
                        System.out.println("Il codice '" + codice + "' è gia presente nella DB, sarà rigenerato");
                    }
                }
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("Non posso leggere i dati: " + e.getMessage());
            }
        } while (quantita > 0);
        return codice;
    }


}//