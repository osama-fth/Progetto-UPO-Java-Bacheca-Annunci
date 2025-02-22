package interfaccia.grafica.vista;


import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import interfaccia.grafica.controllo.ControlloBacheca;
import modello.Utente;

@SuppressWarnings("serial")
public class UtentePanel extends JPanel{
	
	public UtentePanel(Utente utente, ControlloBacheca controllo) {
		
		setLayout (new FlowLayout());
		JLabel label = new JLabel ("Utente: "+ utente);
		add(label);
	}
}
