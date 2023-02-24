package it.urania.software;

import java.util.Scanner;

public class Main {
    static private gestioneDB gestDb = new gestioneDB();
    static private Utilita utilita;
    ReportGrafico report = new ReportGrafico();

    public static gestioneDB getGestDb() {
        return gestDb;
    }

    public static Utilita getUtilita() {
        return utilita;
    }

    public static void main(String[] args) {

        gestDb.conn("root", "root");
        Main app = new Main();
        app.menu();

    }

    private void menu() {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        do {
            System.out.println("Gestione pacchi");
            System.out.println("1 -> Inserisci nuovo pacco");
            System.out.println("2 -> Mostra quantità dei pacchi per ora");
            System.out.println("3 -> Mostra pacchi con peso > 5kg");
            System.out.println("4 -> Mostra pacchi con peso eccessivo");
            System.out.println("5 -> Stampa grafico torta di peso dei pacchi");
            System.out.println("6 -> Stampa grafico barre delle ore di inserimento");
            System.out.println("0 -> Esci");
            System.out.println("Inserisci la scelta:");
            String scelta = scanner.next();
            switch (scelta) {
                case "1":
                    System.out.println("Quanti pacchi vuoi inserire?");
                    int pacchiQta = scanner.nextInt();
                    gestDb.inserimento(pacchiQta);
                    break;
                case "2":
                    gestDb.estraiPacchiPerOra();
                    break;
                case "3":
                    gestDb.contaPachiPesanti();
                    break;
                case "4":
                    pesoEccezione.stampaEsclusi();
                    break;
                case "5":
                    report.stampaTortaPeso(gestDb);
                    break;
                case "6":
                    report.stampaBarOrario(gestDb);
                    break;
                case "0":
                    System.out.println("Il programma in chiusura");
                    flag = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("La scelta errata");
            }
        }
        while (flag);
    }
}