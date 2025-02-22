package interfaccia.grafica;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import modello.Bacheca;
import modello.Utente;
import interfaccia.grafica.vista.BachecaPanel;

@SuppressWarnings("serial")
public class InterfacciaGrafica extends JFrame {

    private Bacheca model;
    private Utente utente;
    
    /**
     * Inizializza la bacheca e l'utente, carica gli annunci da un file e inizializza il JFrame.
     */
    public InterfacciaGrafica() {
    	this.model = new Bacheca();
    	
    	// Creazione dell'utente
        LogIn();
        
    	// Caricamento degli annunci da file 
        caricaBacheca();
    	
        // Configurazione del JFrame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				//quando chiudo la finestra torna al main
        setBounds(100, 100, 1300, 300);
        setTitle("Bacheca Annunci");
        
        // Crea e imposta il pannello della bacheca
        JPanel bachecaPanel = new BachecaPanel(model, utente);
        setContentPane(bachecaPanel);
         
        // Rendi la finestra visibile
        setVisible(true);
    }
    
    
    private void caricaBacheca() {
    	try {
            this.model.caricaAnnunciDaFile("src/annunci.txt");  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento degli annunci: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    private void LogIn() {
    	// Finestra di dialogo per ottenere i dettagli dell'utente
    	JTextField nomeField = new JTextField(20);
     	JTextField emailField = new JTextField(20);
     	 
     	// Creazione di un array con tutti i campi per la finestra di dialogo
        JComponent[] inputs = new JComponent[] {
        	new JLabel("Inserisci il tuo nome utente:"),
        	nomeField,
        	new JLabel("Inserisci la tua email:"),
        	emailField,
        };
        
        while (true) {
        	// Mostra il dialogo per l'inserimento
        	int result = JOptionPane.showConfirmDialog(null, inputs, "Log In", JOptionPane.OK_CANCEL_OPTION);
        	    
        	if (result == JOptionPane.OK_OPTION) {
        	    try {
        	    	this.utente = new Utente(emailField.getText(), nomeField.getText());
        	    	break;										//se creazione avviene con successo interrompe il ciclo
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "Errore nella creazione dell'utente: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
        	}else {
				System.exit(0);						//se schiccio cancel esce dal programma
			}
        }
    }
}
