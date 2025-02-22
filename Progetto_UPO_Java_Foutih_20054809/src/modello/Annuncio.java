package modello;

import java.time.LocalDate;
import java.util.*;
import modello.exception.AnnuncioException;

/**
 * Rappresenta un annuncio pubblicato sulla bacheca.
 */
public class Annuncio {
	/**
	 * Espressione regolare per validare le parole chiave.
	 * Le parole chiave sono una lista di parole alfanumeriche separate da virgola e spazio
	 */
	private static final String paroleChiaveRegex = "([a-zA-Z0-9]+(,\\s*[a-zA-Z0-9]+)*)?";

	/**
	 * Espressione regolare per validare una data nel formato 'yyyy-MM-dd'.
	 */
	private static final String dataScadenzaRegex = "^\\d{4}-\\d{2}-\\d{2}$";


    private int id;
    private Utente autore;
    private String articolo;
    private float prezzo;
    private String tipologia;
    private String paroleChiave;
    private LocalDate dataScadenza;

    /**
     * Crea un annuncio con i dati caricati da file.
     * 
     * @param id L'identificativo univoco dell'annuncio
     * @param autore L'utente creatore dell'annuncio
     * @param articolo Il nome dell'articolo
     * @param prezzo Il prezzo dell'articolo
     * @param tipologia Il tipo di annuncio ("acquisto" o "vendita")
     * @param paroleChiave Le parole chiave associate all'annuncio
     * @param dataScadenza Data di scadenza dell'annuncio nel formato yyyy-MM-dd
     * @throws AnnuncioException Se i dati forniti non sono validi
     */
    public Annuncio(int id, Utente autore, String articolo, float prezzo, String tipologia, String paroleChiave, String dataScadenza)
            throws AnnuncioException {
        this.id = id;
        this.autore = autore;
        this.articolo = validaArticolo(articolo);
        this.prezzo = validaPrezzo(prezzo);
        this.tipologia = validaTipologia(tipologia);
        this.paroleChiave = validaParoleChiave(paroleChiave);
        this.dataScadenza = validaDataScadenza(tipologia, dataScadenza);
    }

    /**
     * Crea un nuovo annuncio generando automaticamente un ID.
     */
    public Annuncio(Utente autore, String articolo, float prezzo, String tipologia, String paroleChiave, String dataScadenza)
            throws AnnuncioException {
        this(generaId(), autore, articolo, prezzo, tipologia, paroleChiave, dataScadenza);
    }

    /**
     * Genera un ID casuale per l'annuncio.
     */
    private static int generaId() {
        return new Random().nextInt(9000) + 1000;
    }
    
    /**
     * Verifica che articolo non sia vuoto.
     */
    private String validaArticolo(String articolo) throws AnnuncioException {
        if (articolo.equals("")) {
            throw new AnnuncioException("Nome articolo non può essere vuoto");
        }
        return articolo;
    }
    
    /**
     * Verifica che il prezzo sia strettamente positivo.
     */
    private float validaPrezzo(float prezzo) throws AnnuncioException {
        if (prezzo <= 0) {
            throw new AnnuncioException("Il prezzo deve essere maggiore di zero");
        }
        return prezzo;
    }

    /**
     * Verifica che la tipologia sia o acquisto o vendita.
     */
    private String validaTipologia(String tipologia) throws AnnuncioException {
        if (!tipologia.equals("acquisto") && !tipologia.equals("vendita")) {
            throw new AnnuncioException("Tipologia non valida, deve essere 'acquisto' o 'vendita'");
        }
        return tipologia;
    }

    /**
     * Verifica che le parole chiave matchino la espressione regolare.
     */
    private String validaParoleChiave(String paroleChiave) throws AnnuncioException {
        if (!paroleChiave.matches(paroleChiaveRegex)) {
            throw new AnnuncioException("Formato parole chiave errato, usare parole separate da una virgola e uno spazio");
        }
        return paroleChiave;
    }
    
    /**
     * Verifica che la data rispetti la espressione regolare.
     */
    private LocalDate validaDataScadenza(String tipologia, String dataScadenza) throws AnnuncioException {
        if ("vendita".equals(tipologia)) {
            if (dataScadenza == null || !dataScadenza.matches(dataScadenzaRegex)) {
                throw new AnnuncioException("Data di scadenza non valida; usare il formato: yyyy-MM-dd o lasciare vuoto in caso di acquisto");
            }
            try {
                return LocalDate.parse(dataScadenza);
            } catch (Exception e) {
                throw new AnnuncioException("Formato della data non valido. Usare il formato yyyy-MM-dd.");
            }
        } else {
            return null;
        }
    }

    public String getArticolo() {
        return articolo;
    }

    public int getId() {
        return id;
    }

    public Utente getAutore() {
        return autore;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public String getTipologia() {
        return tipologia;
    }

    public String getParoleChiave() {
        return paroleChiave;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    
    /**
     * Verifica se l'annuncio è scaduto.
     */
    public boolean isScaduto() {
        return dataScadenza != null && dataScadenza.isBefore(LocalDate.now());
    }
    
    /**
     * Aggiunge una parola chiave all'annuncio.
     */
    public void aggiungiParola(String nuovaParola) throws AnnuncioException {
        if (!nuovaParola.matches(paroleChiaveRegex)) {
            throw new AnnuncioException("Formato parola chiave errato, deve essere alfanumerica senza spazi");
        }

        this.paroleChiave += (", " + nuovaParola);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Annuncio other = (Annuncio) obj;
        return Objects.equals(articolo, other.articolo) && Objects.equals(autore, other.autore)
                && Objects.equals(dataScadenza, other.dataScadenza) && id == other.id
                && Objects.equals(paroleChiave, other.paroleChiave)
                && Float.floatToIntBits(prezzo) == Float.floatToIntBits(other.prezzo)
                && Objects.equals(tipologia, other.tipologia);
    }

    @Override
    public String toString() {
        return "id= " + id + ";  autore= " + autore + ";  articolo= " + articolo + ";  prezzo= €" + prezzo +
               ";  tipologia= " + tipologia + ";  paroleChiave= [" + paroleChiave + "]" + 
               ";  dataScadenza= " + (dataScadenza != null ? dataScadenza : "null");
    }
}
