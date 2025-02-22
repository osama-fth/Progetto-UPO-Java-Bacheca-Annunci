package modello.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.*;
import modello.*;
import modello.exception.*;

class BachecaTest {

    private Bacheca bacheca;
    Utente utente;
    
    @BeforeEach
    void setUp() throws Exception {
        bacheca = new Bacheca();										// Costruisco un oggetto bacheca e un oggetto utente
        utente = new Utente("mario123@gmail.com", "mario123");
    }

    @AfterEach
    void tearDown() {
        bacheca.getAnnunci().clear();									// Svuoto la bacheca dagli annunci
        bacheca.getPoolId().clear();									// Svuoto la pool degli id presenti in bacheca 
    }
    
    @Test
    void testCostruttore() {
    	assertTrue(bacheca.getAnnunci().isEmpty());
    }

    @Test
    void testAggiungiAnnuncioVendita() throws AnnuncioException, BachecaException {
        Annuncio annuncio = new Annuncio(utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-01-20");

        // Aggiungi l'annuncio di vendita e verifica che venga aggiunto correttamente alla bacheca
        ArrayList<Annuncio> risultati = bacheca.aggiungiAnnuncio(annuncio);
        assertTrue(risultati.isEmpty()); 				// Non ci sono articoli di acquisto in vendita da restituire
        assertEquals(1, bacheca.getAnnunci().size()); 	// La bacheca dovrebbe contenere un annuncio
    }

    @Test
    void testAggiungiAnnuncioAcquisto() throws AnnuncioException, BachecaException {
        Annuncio annuncioAcquisto = new Annuncio(utente, "TV", 150, "acquisto", "elettronica, TV", null);

        // Aggiungi l'annuncio di acquisto
        ArrayList<Annuncio> risultati = bacheca.aggiungiAnnuncio(annuncioAcquisto);
        
        // Verifica che, nel caso dell'annuncio di acquisto, la lista restituita contenga articoli in vendita con parole chiave in comune
        assertEquals(1, risultati.size()); // Ci dovrebbe essere almeno un annuncio di tipo "vendita" con parole chiave in comune
        assertEquals("TV", risultati.get(0).getArticolo()); // Verifica che l'articolo trovato sia quello in vendita con parole chiave in comune
        assertEquals(1, bacheca.getAnnunci().size()); // La bacheca dovrebbe contenere l'annuncio di acquisto e quello di vendita
    }


    @Test
    void testAggiungiAnnuncioDuplicato() throws AnnuncioException, BachecaException {
        Annuncio annuncio = new Annuncio(utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-01-20");

        bacheca.aggiungiAnnuncio(annuncio);

        BachecaException e = assertThrows(BachecaException.class, () -> {
            bacheca.aggiungiAnnuncio(annuncio);
        });
        assertEquals("Annuncio già presente", e.getMessage());
    }
    
    @Test
    void testAggiungiAnnuncioStessoID() throws AnnuncioException, BachecaException {
        Annuncio annuncio = new Annuncio(1234, utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-01-20");
        bacheca.aggiungiAnnuncio(annuncio);
        
        BachecaException e = assertThrows(BachecaException.class, () -> {
        	Annuncio annuncio1 = new Annuncio(1234, utente, "Radio", 100, "acquisto", "elettronica", "2026-01-20");
            bacheca.aggiungiAnnuncio(annuncio1);
        });
        assertEquals("ID già presente", e.getMessage());
    }

    @Test
    void testRimuoviAnnuncio() throws AnnuncioException, BachecaException, AutoreNonAutorizzatoException {
        Annuncio annuncio = new Annuncio(utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-01-20");

        bacheca.aggiungiAnnuncio(annuncio);

        assertTrue(bacheca.rimuoviAnnuncio(annuncio.getId(), utente));
        assertTrue(!bacheca.getPoolId().contains(annuncio.getId()));
        assertEquals(0, bacheca.getAnnunci().size());
    }

    @Test
    void testRimuoviAnnuncioAutoreNonAutorizzato() throws AnnuncioException, BachecaException, UtenteException {
        Utente utente2 = new Utente("luigi456@gmail.com", "luigi456");
        Annuncio annuncio = new Annuncio(utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-01-20");

        bacheca.aggiungiAnnuncio(annuncio);

        AutoreNonAutorizzatoException e = assertThrows(AutoreNonAutorizzatoException.class, () -> {
            bacheca.rimuoviAnnuncio(annuncio.getId(), utente2);
        });
        assertEquals("Non sei autorizzato a rimuovere questo annuncio.", e.getMessage());
    }

    @Test
    void testRimuoviAnnuncioNonTrovato() throws AnnuncioException, BachecaException {
        Annuncio annuncio = new Annuncio(1234, utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-01-20");

        bacheca.aggiungiAnnuncio(annuncio);

        BachecaException e = assertThrows(BachecaException.class, () -> {
            bacheca.rimuoviAnnuncio(9999, utente);
        });
        assertEquals("Annuncio non trovato.", e.getMessage());  
    }

    @Test
    void testCercaPerParolaChiave() throws AnnuncioException, BachecaException {
        Annuncio annuncio1 = new Annuncio(utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-01-20");
        Annuncio annuncio2 = new Annuncio(utente, "Frigorifero", 200, "vendita", "elettronica, cucina", "2026-01-20");

        bacheca.aggiungiAnnuncio(annuncio1);
        bacheca.aggiungiAnnuncio(annuncio2);

        assertEquals(1, bacheca.cercaPerParolaChiave("TV").size());  
        assertEquals(2, bacheca.cercaPerParolaChiave("elettronica").size());  
        assertEquals(0, bacheca.cercaPerParolaChiave("gaming").size());  
    }

    @Test
    void testPulisciBacheca() throws AnnuncioException, BachecaException {
        Annuncio annuncio1 = new Annuncio(utente, "Televisore", 150, "vendita", "elettronica, TV", "2025-01-01");
        Annuncio annuncio2 = new Annuncio(utente, "Frigorifero", 200, "vendita", "elettronica, cucina", "2025-01-01");
        Annuncio annuncio3 = new Annuncio(utente, "Laptop", 200, "vendita", "elettronica, gaming", "2026-01-01");
        
        bacheca.aggiungiAnnuncio(annuncio1);
        bacheca.aggiungiAnnuncio(annuncio2);
        bacheca.aggiungiAnnuncio(annuncio3);
        
        assertEquals(3, bacheca.getAnnunci().size());

        bacheca.pulisciBacheca();
        
        assertEquals(1, bacheca.getAnnunci().size());
        assertTrue(bacheca.getAnnunci().contains(annuncio3));
        assertFalse(bacheca.getAnnunci().contains(annuncio1));
        assertFalse(bacheca.getAnnunci().contains(annuncio2));
    }
    
    @Test
    void testAggiungiNuovaParolaChiave() throws AnnuncioException, BachecaException, AutoreNonAutorizzatoException, UtenteException {
    	Annuncio annuncio = new Annuncio(1234, utente, "Televisore", 150, "vendita", "elettronica, TV", "2025-01-01");
    	bacheca.aggiungiAnnuncio(annuncio);
    	
    	assertEquals("elettronica, TV", annuncio.getParoleChiave());			//controllo parole chiavi prima della aggiunta
    	bacheca.aggiungiNuovaParolaChiave(1234, utente, "usato");
    	assertEquals("elettronica, TV, usato", annuncio.getParoleChiave());		//controllo dopo la aggiunta
    	
    	//Caso non valido: utente non autorizzato
    	Utente utente2 = new Utente("luigi456@gmail.com", "luigi456");
    	assertThrows(AutoreNonAutorizzatoException.class, ()->{
    		bacheca.aggiungiNuovaParolaChiave(1234, utente2, "Lg");
    	});
    } 
    
    @Test
    void testCaricaSalvaDaFile() throws AnnuncioException, BachecaException, IOException, UtenteException {
        
        Annuncio annuncio1 = new Annuncio(utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-01-20");
        Annuncio annuncio2 = new Annuncio(utente, "Frigorifero", 200, "vendita", "elettronica, cucina", "2026-01-20");

        bacheca.aggiungiAnnuncio(annuncio1);
        bacheca.aggiungiAnnuncio(annuncio2);

        bacheca.salvaAnnunciSuFile("src/modello/test/test.txt");		// Salvo su file
        bacheca.getAnnunci().clear();									// Svuoto la bacheca
        bacheca.getPoolId().clear();									// Svuoto la pool degli id
        bacheca.caricaAnnunciDaFile("src/modello/test/test.txt");  		// Carico da file

        assertEquals(2, bacheca.getAnnunci().size());  					// Verifico se ci sono due annunci
        assertTrue(bacheca.getAnnunci().contains(annuncio1));  			// Verifica che annuncio sia presente
        assertTrue(bacheca.getAnnunci().contains(annuncio2));
    } 
    
    @Test
    void testRimuoviAnnuncioConIteratore() throws AnnuncioException, BachecaException, IOException, UtenteException {
        
    	Exception e = assertThrows(UnsupportedOperationException.class, ()->{
    		Annuncio annuncio = new Annuncio(utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-01-20");
    		bacheca.aggiungiAnnuncio(annuncio);
    		Iterator<Annuncio> iteratore = bacheca.iterator();
    		iteratore.remove();						// iteratore.remove di default non è supportata
    	});											// perciò lancia una eccezione di tipo UnsopportedOpertionException
    	
    	assertEquals("remove", e.getMessage());
    	assertEquals(UnsupportedOperationException.class, e.getClass());
    }
        
}
