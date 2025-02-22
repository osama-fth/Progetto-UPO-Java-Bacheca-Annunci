package modello.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import modello.Annuncio;
import modello.Utente;
import modello.exception.AnnuncioException;

class AnnuncioTest {
	
    @Test
    void testCostruttoreConVendita() throws Exception {
        // Caso valido: tipo "vendita"
        LocalDate data = LocalDate.parse("2026-11-12");
    	Utente utente = new Utente("MarioRossi@gmail.com", "Marco123");
        Annuncio annuncio = new Annuncio(utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-11-12");
        
        assertEquals("Televisore", annuncio.getArticolo());
        assertEquals(150, annuncio.getPrezzo());
        assertEquals("vendita", annuncio.getTipologia());
        assertEquals("elettronica, TV", annuncio.getParoleChiave());
        assertEquals(data, annuncio.getDataScadenza());                    
        assertEquals(utente, annuncio.getAutore());  // Verifica che l'autore sia quello giusto
    }
    
    @Test
    void testCostruttoreConVenditaConId() throws Exception {
        // Caso valido: tipo "vendita" passando anche id 
        LocalDate data = LocalDate.parse("2026-11-12");
        Utente utente = new Utente("MarioRossi@gmail.com", "Marco123");
        Annuncio annuncio = new Annuncio(1234,utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-11-12");
        
        assertEquals(1234, annuncio.getId());
        assertEquals("Televisore", annuncio.getArticolo());
        assertEquals(150.0, annuncio.getPrezzo());
        assertEquals("vendita", annuncio.getTipologia());
        assertEquals("elettronica, TV", annuncio.getParoleChiave());
        assertEquals(data, annuncio.getDataScadenza());                    
        assertEquals(utente, annuncio.getAutore());  
    }

    @Test
    void testCostruttoreConAcquisto() throws Exception {
        // Caso valido: tipo "acquisto" senza data scadenza
    	Utente utente = new Utente("MarioRossi@gmail.com", "Marco123");
        Annuncio annuncio = new Annuncio(utente, "Computer", 500, "acquisto", "laptop, gaming", null);
        
        assertEquals("Computer", annuncio.getArticolo());
        assertEquals(500.0, annuncio.getPrezzo());
        assertEquals("acquisto", annuncio.getTipologia());
        assertEquals("laptop, gaming", annuncio.getParoleChiave());
        assertNull(annuncio.getDataScadenza());  // La scadenza dovrebbe essere null
        assertEquals(utente, annuncio.getAutore());  
    }

    @Test
    void testDataScadenzaNonValida() throws Exception {
        // Caso non valido: tipo "vendita" senza data scadenza
    	Utente utente = new Utente("MarioRossi@gmail.com", "Marco123");
    	
        AnnuncioException e = assertThrows(AnnuncioException.class, () -> {
            new Annuncio(utente, "Televisore", 150, "vendita", "elettronica, TV", null);
        });
        assertEquals("Data di scadenza non valida; usare il formato: yyyy-MM-dd o lasciare vuoto in caso di acquisto", e.getMessage());
        
     // Caso non valido: tipo "vendita" con data di scadenza non valida
        AnnuncioException e1 = assertThrows(AnnuncioException.class, () -> {
            new Annuncio(utente, "Televisore", 150, "vendita", "elettronica, TV", "2026-13-20");
        });
        assertEquals("Formato della data non valido. Usare il formato yyyy-MM-dd.", e1.getMessage());
    }

    @Test
    void testPrezzoNonValido() throws Exception {
        // Caso non valido: prezzo negativo
    	Utente utente = new Utente("MarioRossi@gmail.com", "Marco123");
        AnnuncioException e = assertThrows(AnnuncioException.class, () -> {
            new Annuncio(utente, "Televisore", -100, "vendita", "elettronica, TV", "2026-11-12");
        });
        assertEquals("Il prezzo deve essere maggiore di zero", e.getMessage());
    }

    @Test
    void testTipologiaNonValida() throws Exception {
        // Caso non valido: tipologia non riconosciuta
    	Utente utente = new Utente("MarioRossi@gmail.com", "Marco123");
        AnnuncioException e = assertThrows(AnnuncioException.class, () -> {
            new Annuncio(utente, "Televisore", 150, "noleggio", "elettronica, TV", "2026-11-12");
        });
        assertEquals("Tipologia non valida, deve essere 'acquisto' o 'vendita'", e.getMessage());
    }

    @Test
    void testParoleChiaveNonValide() throws Exception {
        
    	Utente utente = new Utente("MarioRossi@gmail.com", "Marco123");
    	// Caso valido: stringa vuota
    	assertDoesNotThrow(()->{
    		new Annuncio(utente, "Televisore", 150, "vendita", "", "2026-11-12");
    	});
    	
    	// Caso non valido: formato parole chiave errato
        AnnuncioException e = assertThrows(AnnuncioException.class, () -> {
            new Annuncio(utente, "Televisore", 150, "vendita", "elettronica;TV", "2026-11-12");
        });
        assertEquals("Formato parole chiave errato, usare parole separate da una virgola e uno spazio", e.getMessage());
    }
}
