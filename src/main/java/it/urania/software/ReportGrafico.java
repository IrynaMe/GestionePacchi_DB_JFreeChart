package it.urania.software;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportGrafico {

    public void stampaTortaPeso(gestioneDB obj) {
        DefaultPieDataset graficoTorta = new DefaultPieDataset();
        int pacchiPesanti = obj.contaPachiPesanti();
        int pacchiSovraPeso = pesoEccezione.stampaEsclusi();
        int pacchiLeggeri = obj.contaTutti() - pacchiPesanti;

        // metto i valori nel dataset
        graficoTorta.setValue("Pacchi > 5kg", pacchiPesanti);
        graficoTorta.setValue("Pacchi esclusi (>10 kg)", pacchiSovraPeso);
        graficoTorta.setValue("Pacchi < 5 kg", pacchiLeggeri);

        // creo il grafico ---> JFreeChart
        // titolo, dataset, legenda, tooltips, url
        JFreeChart grafico = ChartFactory.createPieChart(
                "Peso dei pacchi",
                graficoTorta, // dataset di riferimento
                true, // mostro legenda
                true, // tooltips *-> evidenzia i dati della sezione
                false // link html
        );
        // rappresentare i dati in formato immagine (usa la classe ChartUtils)
        try {
            ChartUtils.saveChartAsJPEG(
                    new File("graficoPeso.jpg"),
                    grafico,
                    500,
                    500
            );
            System.out.println("File Grafico torta creato");
        } catch (
                IOException e) {
            System.out.println("Non posso generare il file" + e.getMessage());
        }
    }

    public void stampaBarOrario(gestioneDB obj) {
        DefaultCategoryDataset dsOre = new DefaultCategoryDataset();
        ArrayList<String> pacchiPerOra = obj.estraiPacchiPerOra();
        HashMap<String, Integer> pacchiOrari = new HashMap<>();
        for (String riga : pacchiPerOra) {
            String[] pezziUno = riga.split("->");// Dalle 20 alle 21   + 1
            int numeroPacchi = Integer.parseInt(pezziUno[1].trim());// numero pacchi
            String oreStr = pezziUno[0].trim();
            String[] pezziDue = oreStr.split(" ");//Dalle+ 20 + alle + 21
            String ore = pezziDue[1].trim(); //20
            //  pacchiOrari.put(ore, numeroPacchi);
            String orePrint = ore + "-" + (Integer.parseInt(ore) + 1);
            dsOre.setValue(numeroPacchi, "Quantità dei pacchi", orePrint);
        }


        // inseriamo i dati nello specifico dataset
        // dasetGoal.setValue(10,"goal","Giocatore A");
        // creo l'oggetto grafico
        JFreeChart graficoOre = ChartFactory.createBarChart(
                "Comparazione della quantità dei pacchi per ora",
                "Ore",
                "Pacchi inseriti",
                dsOre,
                PlotOrientation.VERTICAL,
                true,
                true,
                true
        );
        try {
            ChartUtils.saveChartAsJPEG(
                    new File("pacchiOrario.jpg"),
                    graficoOre,
                    1200,
                    600
            );
            System.out.println("File Grafico a barre creato");
        } catch (IOException e) {
            System.out.println("Non posso creare il file: " + e.getMessage());
        }
    }
}