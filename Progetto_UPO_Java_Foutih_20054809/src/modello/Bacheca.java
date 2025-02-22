package modello;

import java.io.*;
import java.time.*;
import java.util.*;
import modello.exception.*;

public class Bacheca implements Iterable<Annuncio>{
    private List<Annuncio> annunci;
    private List<Integer> poolId;
    
    /**
     * Costruttore del oggetto bacheca, inizializza un arraylist per gli annunci, e un arraylist di interi per salvare gli id presenti in bacheca
     * @param autore La stringa contenente l'autore.
     * @return Un oggetto Utente
     */
    public Bacheca() {
        this.annunci = new ArrayList<Annuncio>();
        this.poolId = new ArrayList<Integer>();
    }
    
    @Override
    public Iterator<Annuncio> iterator() {
        return new Iterator<Annuncio>() {
            private final Iterator<Annuncio> iteratore = annunci.iterator();

            @Override
            public boolean hasNext() {
                return iteratore.hasNext();
            }

            @Override
            public Annuncio next() {
                return iteratore.next();
            }
        };
    }
    
    
    /**
     * Aggiunge un nuovo annuncio alla bacheca, se non è già presente.
     * Se l'annuncio è di tipo "acquisto", cerca altri articoli in vendita con parole chiave in comune.
     * @param annuncio L'annuncio da aggiungere.
     * @return Una lista di annunci trovati in base alle parole chiave, se l'annuncio è di tipo acquisto.
     * @throws BachecaException Se l'annuncio è già presente nella bacheca.
     */
    public ArrayList<Annuncio> aggiungiAnnuncio(Annuncio annuncio) throws BachecaException {
        if (annunci.contains(annuncio)) {
            throw new BachecaException("Annuncio già presente");
        }
        
        controlloIdPresente(annuncio.getId());
        annunci.add(annuncio);
        
              
        // Se l'annuncio è di acquisto, cerchiamo articoli in vendita con parole chiave in comune
        if (annuncio.getTipologia().equals("acquisto")) {
            return cercaPerParolaChiave(annuncio.getParoleChiave());
        }

        // Se non è un annuncio di acquisto, restituisce una lista vuota
        return new ArrayList<>();
    }
    
    /**
     * Rimuove un annuncio dalla bacheca, se l'utente è l'autore dell'annuncio.
     * @param id L'ID dell'annuncio da rimuovere.
     * @param utente L'utente che tenta di rimuovere l'annuncio.
     * @throws AutoreNonAutorizzatoException Se l'utente non è l'autore dell'annuncio.
     * @throws BachecaException Se l'annuncio non è stato trovato.
     */
    public boolean rimuoviAnnuncio(int id, Utente utente) throws AutoreNonAutorizzatoException, BachecaException {
        List<Annuncio> annuncioDaRimuovere = new ArrayList<>();  // Lista temporanea per l'annuncio da rimuovere
        List<Integer> idDaRimuovere = new ArrayList<>();  // Lista temporanea per id da rimuovere
        
        Iterator<Annuncio> iteratore = annunci.iterator();  
        while (iteratore.hasNext()) {
            Annuncio annuncio = iteratore.next();
            if (annuncio.getId() == id) {						 // Controllo dell'id
                if (!annuncio.getAutore().equals(utente)) {		 // Controllo dell'autore
                    throw new AutoreNonAutorizzatoException("Non sei autorizzato a rimuovere questo annuncio.");
                }
                annuncioDaRimuovere.add(annuncio);
                idDaRimuovere.add(annuncio.getId());			
                break;  
            }
        }

        if (!annuncioDaRimuovere.isEmpty()) {
            annunci.removeAll(annuncioDaRimuovere);
            poolId.removeAll(idDaRimuovere);
            return true;
        } else {
            throw new BachecaException("Annuncio non trovato.");
        }
    }

    /**
     * Cerca gli annunci che contengono almeno una delle parole chiave specificate.
     */
    public ArrayList<Annuncio> cercaPerParolaChiave(String paroleChiave) {
        ArrayList<Annuncio> risultati = new ArrayList<>();
        
        String[] parole = paroleChiave.split(",");			// Separiamo la stringa in base a  ", "

        Iterator<Annuncio> iteratore = annunci.iterator();  // Uso l'iteratore per scorrere gli annunci
        while (iteratore.hasNext()) {
            Annuncio annuncio = iteratore.next();
            
            for (String parola : parole) {
                if (annuncio.getParoleChiave().contains(parola.trim())) {		//uso trim per rimuovere eventuali spazi
                    risultati.add(annuncio);
                    break; 
                }
            }
        }
        return risultati;
    }

    /**
     * Pulisce la bacheca rimuovendo gli annunci scaduti.
     */
    public boolean pulisciBacheca() {
        ArrayList<Annuncio> annunciDaRimuovere = new ArrayList<>();
	List<Integer> idDaRimuovere = new ArrayList<>();
	    
        Iterator<Annuncio> iteratore = annunci.iterator();  
        while (iteratore.hasNext()) {
            Annuncio annuncio = iteratore.next();
            if (annuncio.isScaduto()) {
                annunciDaRimuovere.add(annuncio);
		idDaRimuovere.add(annuncio.getId());
            }
        }

        return annunci.removeAll(annunciDaRimuovere) && poolId.removeAll(idDaRimuovere);
    }
    
    /**
     * Aggiunge una nuova parola chiave a un annuncio esistente, se l'utente è l'autore.
     * @param id L'ID dell'annuncio.
     * @param utente L'utente che sta aggiungendo la parola chiave.
     * @param nuovaParola La parola chiave da aggiungere.
     * @throws AutoreNonAutorizzatoException Se l'utente non è l'autore dell'annuncio.
     * @throws AnnuncioException Se l'annuncio non viene trovato.
     */
    public boolean aggiungiNuovaParolaChiave(int id, Utente utente, String nuovaParola) throws AutoreNonAutorizzatoException, AnnuncioException {
    	Iterator<Annuncio> iteratore = annunci.iterator();  //iteratore per scorrere gli annunci
        while (iteratore.hasNext()) {
            Annuncio annuncio = iteratore.next();
            if (annuncio.getId() == id) {
                if (!annuncio.getAutore().equals(utente)) {
                    throw new AutoreNonAutorizzatoException("Non sei autorizzato a modificare questo annuncio.");
                }
                
				annuncio.aggiungiParola(nuovaParola);;  // Aggiungiamo all'annuncio la nuova parola chiave
				return true; 
            }
        }
        
        throw new AnnuncioException("Annuncio non trovato");
    }

    public ArrayList<Annuncio> getAnnunci() {
        return new ArrayList<>(annunci);
    }
    
    public List<Integer> getPoolId() {
		return poolId;
	}

	/**
     * Salva gli annunci presenti nella bacheca in un file.
     */
    public void salvaAnnunciSuFile(String fileName) throws IOException {
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileWriter(fileName));
            Iterator<Annuncio> iteratore = annunci.iterator();
            while (iteratore.hasNext()) {
                Annuncio annuncio = iteratore.next();
                output.printf("%d; %s; %s; %.2f; %s; %s; %s\n",
                        annuncio.getId(),
                        annuncio.getAutore(),
                        annuncio.getArticolo(),
                        annuncio.getPrezzo(),
                        annuncio.getTipologia(),
                        annuncio.getParoleChiave(),
                        annuncio.getDataScadenza() != null ? annuncio.getDataScadenza().toString() : "null"); // se annuncio non ha data scadenza mette "null"
            }
        } catch (Exception e) {
        	throw new IOException("Errore nel salvataggio della bacheca su file: " + e.getMessage());
        } finally {
            output.close();  // Mi assicuro di chiudere sempre output
        }
    }

    /**
     * Carica gli annunci da un file alla bacheca
     */
    public void caricaAnnunciDaFile(String fileName) throws IOException, AnnuncioException, UtenteException {
    	BufferedReader input = null;
    	try {
        	input = new BufferedReader(new FileReader(fileName));
            String linea;
            annunci.clear();											// Svuota la bacheca
            
            while ((linea = input.readLine()) != null) {
                String[] dati = linea.split(";");
                if (dati.length == 7) {
                    int id = Integer.parseInt(dati[0].strip());			// Uso strip per rimuovere tutti gli spazi
                    controlloIdPresente(id);							// Controllo se id presente
                    
                    String autore = dati[1].strip();
                    Utente utente = generaAutore(autore);  				// Genero l'autore dalla stringa
                    
                    String articolo = dati[2].strip();
                    
                    // Sostituisci la virgola con il punto nel prezzo perchè parsefloat vuole il punto
                    float prezzo = Float.parseFloat(dati[3].strip().replace(",", "."));
                    
                    String tipologia = dati[4].strip();
                    String paroleChiave = dati[5].strip();
                    
                    // Controllo se dati[6] == "null" allora l'annuncio non ha scadenza, sennò crea un istanza LocalDate da dati[6]
                    LocalDate scadenza = "null".equals(dati[6].strip()) ? null : LocalDate.parse(dati[6].strip());

                    // Crea l'annuncio e lo aggiunge in bacheca
                    annunci.add(new Annuncio(id, utente, articolo, prezzo, tipologia, paroleChiave, scadenza != null ? scadenza.toString() : null));
                }
            }
        }catch (Exception e) {
			throw new IOException("Errore nel caricamento della bacheca: " + e.getMessage());
		}finally {
			input.close();				//mi assicuro di chiudere input sempre
		}
    }

    /**
     * Estrae l'email e il nome da una stringa dell'autore nel formato "[email=<email>,nome=<nome>]" e costruisce un oggetto Utente quando si carica un annuncio da file
     * @param autore La stringa contenente l'autore.
     * @return Un oggetto Utente
     */
    private Utente generaAutore(String autore) throws UtenteException {
        
        String senzaParentesi = autore.substring(8, autore.length() - 1); // Rimuove '[email=' e ']'
        
        // Divide la stringa in due parti separandola da ","
        String[] parti = senzaParentesi.split(",");
        
        // Sostituisce il prefisso "nome=" con ""
        String email = parti[0].strip();  
        String nome = parti[1].replace("nome=", "").strip();  		
        
        return new Utente(email, nome); // Crea Utente
    }
    
    /**
     * Controlla se l'id passato come argomento è presente nella pool degli id
     * @param id L'id da controllare.
     * @return true se non presente e aggiunge il nuovo id alla pool
     * @throws BachecaException se id gia presente
     */
    private boolean controlloIdPresente(int id) throws BachecaException {
    	if (poolId.contains(id)) {
			throw new BachecaException("ID già presente");
		}
        
		return poolId.add(id);
    }

    @Override
    public String toString() {
        StringBuilder listaBacheca = new StringBuilder();
        for (Annuncio annuncio : annunci) {
            listaBacheca.append(annuncio).append("\n");
        }
        return listaBacheca.toString();
    }

}
