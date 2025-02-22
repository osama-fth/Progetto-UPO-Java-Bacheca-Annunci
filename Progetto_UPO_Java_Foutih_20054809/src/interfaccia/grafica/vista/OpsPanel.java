package interfaccia.grafica.vista;

import java.awt.*;
import javax.swing.*;
import interfaccia.grafica.controllo.ControlloBacheca;

@SuppressWarnings("serial")
public class OpsPanel extends JPanel{
	/**
     * Inizializza il pannello operazioniBacheca inserendo i vari bottoni e li aggiunge al listener
     * @param controllo il controller delle operazioni
     */
	public OpsPanel(ControlloBacheca controllo) {
		setLayout (new FlowLayout ());
		
		JButton addAnnuncio = new JButton ( "Aggiungi Annuncio" );				// Crea pulsante
		add(addAnnuncio);														// Aggiunge pulsante al pannello
		addAnnuncio.addActionListener(controllo);								// Aggiunge pulsante al listener del controller
		
		JButton removeAnnuncio = new JButton ( "Rimuovi Annuncio" );
		add(removeAnnuncio);
		removeAnnuncio.addActionListener(controllo);
		
		JButton cercaAnnunci = new JButton ( "Cerca Annunci" );
		add(cercaAnnunci);
		cercaAnnunci.addActionListener(controllo);
		
		JButton PulisciBacheca = new JButton ( "Pulisci Bacheca" );
		add(PulisciBacheca);
		PulisciBacheca.addActionListener(controllo);
		
		JButton addParolaChiave = new JButton ( "Aggiungi Parola Chiave ad Annuncio" );
		add(addParolaChiave);
		addParolaChiave.addActionListener(controllo);
	}
}
