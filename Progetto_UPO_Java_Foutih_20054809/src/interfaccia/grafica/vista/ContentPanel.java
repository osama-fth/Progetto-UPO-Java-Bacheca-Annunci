package interfaccia.grafica.vista;

import java.awt.*;
import javax.swing.*;
import modello.*;

@SuppressWarnings("serial")
public class ContentPanel extends JPanel{
	private Bacheca model;
	private JTextArea bacheca;
	
	/**
     * Inizializza il pannello contenutoBacheca mettendo una Label e una TextArea 
     * @param model La bacheca caricata da file
     */
	public ContentPanel(Bacheca model) {
		this.model = model ;
		
		setLayout (new BorderLayout ());
		bacheca = new JTextArea (10 ,100);
		bacheca.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		JLabel label = new JLabel ("Bacheca");
		
		add (label, BorderLayout.NORTH );
		add (bacheca, BorderLayout.CENTER );
		
		updateView ();
	}

	public void updateView() {
		bacheca.setText(model.toString());
	}
	
}
