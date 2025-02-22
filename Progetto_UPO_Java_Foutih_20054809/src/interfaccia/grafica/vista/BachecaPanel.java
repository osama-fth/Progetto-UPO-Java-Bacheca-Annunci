package interfaccia.grafica.vista;

import java.awt.*;
import javax.swing.*;

import interfaccia.grafica.controllo.ControlloBacheca;
import modello.*;

@SuppressWarnings("serial")
public class BachecaPanel extends JPanel{
	
	/**
     * Inizializza il pannello dove ci saranno i componenti della finestra
     * @param model La bacheca caricata da file
     * @param utente L'utente che ha fatto l'accesso
     */
	public BachecaPanel(Bacheca model, Utente utente) {
		// settate il layout a BorderLayout
		ContentPanel contenutoBacheca =new ContentPanel (model);								// Pannello per la bacheca
		ControlloBacheca controllo = new ControlloBacheca ( contenutoBacheca , model, utente );	// Controller
		OpsPanel operazioniBacheca = new OpsPanel (controllo);									// Pannello per i pulsanti
		UtentePanel utenteBacheca = new UtentePanel(utente, controllo);							// Pannello per visualizzare l'utente loggato
		
		// Aggiungere le componenti al pannello nelle posizioni giuste		
		add(operazioniBacheca, BorderLayout.NORTH);
		add(contenutoBacheca, BorderLayout.CENTER);
		add(utenteBacheca, BorderLayout.SOUTH);
	}

}
