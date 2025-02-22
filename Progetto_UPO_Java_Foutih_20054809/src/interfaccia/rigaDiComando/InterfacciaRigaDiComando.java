package interfaccia.rigaDiComando;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import modello.Bacheca;
import modello.Annuncio;
import modello.Utente;
import modello.exception.AnnuncioException;
import modello.exception.AutoreNonAutorizzatoException;
import modello.exception.BachecaException;
import modello.exception.UtenteException;

public class InterfacciaRigaDiComando {

    private Bacheca bacheca;
    private Utente utente;
    private Scanner scanner;
    
    /**
     * Inizializza la bacheca e l'utente, carica gli annunci da un file e chiedendo all'utente di fornire i propri dati.
     * @param scanner L'oggetto Scanner per la lettura dell'input dell'utente.
     */
    public InterfacciaRigaDiComando(Scanner scanner){
        this.scanner = scanner;
        this.bacheca = new Bacheca();
        
        // Creazione dell'utente
        logIn();
        
        // Caricamento da file
        caricaBacheca();
        
        // Avvio della applicazione
        run();
    }
    
    private void caricaBacheca() {
    	try {
            this.bacheca.caricaAnnunciDaFile("src/annunci.txt");  
        } catch (IOException | AnnuncioException | UtenteException e) {
            System.out.println("Errore nel caricamento degli annunci: " + e.getMessage());
            System.exit(0);
        }
	}
    
    private void logIn() {
        while (true) {
            System.out.println("Inserisci il tuo nome:");
            String nome = scanner.nextLine().trim();
            System.out.println("Inserisci la tua email:");
            String email = scanner.nextLine().trim();
            
            try {
                this.utente = new Utente(email, nome);
                break;											// Se utente creato correttamente interrompe il ciclo
            } catch (UtenteException e) {
                System.out.println("Errore nella creazione dell'utente: " + e.getMessage());
                System.out.println("Riprova a creare l'utente.");
            }
        }
    }
    
    /**
     * Metodo principale che gestisce il menù.
     */
    private void run() {
        while (true) {
            System.out.println("\nBenvenuto, " + utente);
            System.out.println("Scegli un'operazione:");
            System.out.println("1. Aggiungi annuncio");
            System.out.println("2. Rimuovi annuncio");
            System.out.println("3. Cerca annuncio");
            System.out.println("4. Pulisci bacheca (annunci scaduti)");
            System.out.println("5. Visualizza bacheca");
            System.out.println("6. Aggiungi Parola Chiave ad Annuncio");
            System.out.println("7. Esci");
            
            try {
                int scelta = Integer.parseInt(scanner.nextLine().trim());
                switch (scelta) {
                    case 1:
                    	aggiungiAnnuncio(); break;
                    case 2:
                    	rimuoviAnnuncio(); break;
                    case 3:
                    	cercaAnnuncio(); break;
                    case 4:
                    	pulisciBacheca(); break;
                    case 5:
                    	visualizzaBacheca(); break;
                    case 6:
                    	aggiungiNuovaParolaChiave();
                    	break;
                    case 7:
                    	return;								// Esce dall'applicazione
                    default:
                    	System.out.println("Scelta non valida, riprova.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Errore: inserire un numero valido.");
            }
        }
    }

    /**
    * Aggiunge un annuncio alla bacheca.
    */
    private void aggiungiAnnuncio() {
        while (true) {
            try {
                System.out.println("Inserisci il titolo dell'articolo:");
                String articolo = scanner.nextLine().trim();
                System.out.println("Inserisci il prezzo dell'articolo:");
                float prezzo = Float.parseFloat(scanner.nextLine().trim());
                System.out.println("Inserisci la tipologia (acquisto/vendita):");
                String tipologia = scanner.nextLine().trim();
                System.out.println("Inserisci le parole chiave:");
                String paroleChiave = scanner.nextLine().trim();
                System.out.println("Inserisci la data di scadenza (yyyy-MM-dd) o lasciare vuoto per nessuna scadenza:");
                String dataScadenza = scanner.nextLine().trim();
                
                Annuncio annuncio = new Annuncio(utente, articolo, prezzo, tipologia, paroleChiave, dataScadenza);
                ArrayList<Annuncio> risultato = bacheca.aggiungiAnnuncio(annuncio);
                bacheca.salvaAnnunciSuFile("src/annunci.txt");
                
                System.out.println("Annuncio aggiunto con successo!");
                
                if (tipologia.equals("acquisto")) {
					if (!risultato.isEmpty()) {
						System.out.println("\nAnnunci che potrebbero interessarti");
						for(Annuncio ann : risultato) {
							if ("vendita".equals(ann.getTipologia())) {
								System.out.println(ann);
							}
						}
					}
				}
                
                break;
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
                System.out.println("Riprovare...");
            }
        }
    }
    
    /**
     * Rimuove un annuncio alla bacheca.
     */
    private void rimuoviAnnuncio() {
        while (true) {
            try {
                System.out.println("Inserisci l'ID dell'annuncio da rimuovere o 0 per uscire:");
                int id = Integer.parseInt(scanner.nextLine().trim());
                if (id == 0) return;									//se id 0 esce
                
                if (bacheca.rimuoviAnnuncio(id, utente)) {
                    bacheca.salvaAnnunciSuFile("src/annunci.txt");
                    System.out.println("Annuncio rimosso con successo!");
                    break;
                }
            } catch (BachecaException | IOException | AutoreNonAutorizzatoException e) {
                System.out.println("Errore: " + e.getMessage());
                System.out.println("Riprovare...");
            } catch (NumberFormatException e) {
            	System.out.println("Errore: Formato ID non valido, riprova...");
			}
        }
    }
    
    /**
     * Cerca annunci che contengono determinate parole chiavi.
     */
    private void cercaAnnuncio() {
        System.out.println("Inserisci le parole chiave da cercare:");
        String paroleChiave = scanner.nextLine().trim();
        
        ArrayList<Annuncio> risultati = bacheca.cercaPerParolaChiave(paroleChiave);
        if (risultati.isEmpty()) {
            System.out.println("Nessun annuncio trovato.");
        } else {
            System.out.println("Annunci trovati:");
            for (Annuncio annuncio : risultati)
            	System.out.println(annuncio);      
        }
    }
    
    /**
     * Pulisce la bacheca dagli annunci scaduti 
     */
    private void pulisciBacheca() {
        if (bacheca.pulisciBacheca()) {
            System.out.println("Annunci scaduti rimossi.");
        } else {
            System.out.println("Nessun annuncio scaduto.");
        }
    }

    private void visualizzaBacheca() {
    	ArrayList<Annuncio> annunci = bacheca.getAnnunci();  
        
    	if (annunci.isEmpty()) {
        	System.out.println("La bacheca è vuota.");
        } else {
        	System.out.println("Annunci nella bacheca:");
            for (Annuncio annuncio : annunci) {				
            	System.out.println(annuncio);  
            }
        }
    }            
    
    /**
     * Aggiunge una o più parole chiavi ad un annuncio di cui sono autore.
     */
    private void aggiungiNuovaParolaChiave() {
        while (true) {
            try {
                System.out.println("Inserisci l'ID dell'annuncio da modificare o 0 per uscire:");
                int id = Integer.parseInt(scanner.nextLine().trim());
                
                if (id == 0) {  												// Se 0 esce
                    break;
                }
                
                System.out.println("Inserisci le nuove parole chiave (separate da virgola e spazio):");
                String nuovaParola = scanner.nextLine().trim();

                if (bacheca.aggiungiNuovaParolaChiave(id, utente, nuovaParola)) {
                    bacheca.salvaAnnunciSuFile("src/annunci.txt");
                    System.out.println("Parola chiave aggiunta con successo!");
                    break;
                }
            } catch (AutoreNonAutorizzatoException | IOException | AnnuncioException e) {
                System.out.println("Errore nella aggiunta della parola chiave: " + e.getMessage());
                System.out.println("Riprovare...");
            } catch (NumberFormatException e) {
                System.out.println("ID non valido, riprova...");
            }
        }
    }
}
