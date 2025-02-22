package main;

import java.util.Scanner;
import interfaccia.grafica.InterfacciaGrafica;
import interfaccia.rigaDiComando.InterfacciaRigaDiComando;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);			// Uso classe scanner per poter leggere da tastiera gli input

        while (true) {
            System.out.println("Benvenuto! Scegli l'interfaccia che vuoi usare:");
            System.out.println("1. Interfaccia Grafica");
            System.out.println("2. Interfaccia da Riga di Comando");
            System.out.println("3. Esci");

            String input = scanner.nextLine();
            try {
                int scelta = Integer.parseInt(input);

                switch (scelta) {
                    case 1:
                        System.out.println("Avvio dell'interfaccia grafica...");
                        new InterfacciaGrafica();
                        break;
                    case 2:
                        System.out.println("Avvio dell'interfaccia da riga di comando...");
                        new InterfacciaRigaDiComando(scanner);						// Passo come argomento scanner per 
                        break;														// poter continuare a leggere input da tastiera 
                    case 3:
                        System.out.println("Uscita dal programma...");
                        return; 													//esce dai main e termina il programma
                    default:
                        System.out.println("Scelta non valida. Scegli un'opzione tra 1 e 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Errore: formato scelta non valida. Riprova con un numero tra 1 e 3.");
            }
        }
    }
}
