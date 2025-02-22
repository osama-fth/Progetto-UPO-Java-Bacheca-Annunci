package modello;

import java.util.Objects;
import modello.exception.UtenteException;

public class Utente {
    private final String email;
    private final String nome;
    
    /**
     * Espressione regolare utilizzata per validare un indirizzo email.
     */
    private final String emailRegex = "[\\w\\.]+@[\\w\\.]+\\.[\\w]{2,}";
    
    /**
     * Espressione regolare utilizzata per validare un nome.
     * Nome composto solo da caratteri alfanumeri, non accetta caratteri speciali.
     */
    private final String nomeRegex = "[a-zA-Z0-9][a-zA-Z0-9]*";
    
    /**
     * Crea un oggetto Utente con email e nome.
     * @param email L'email dell'utente.
     * @param nome Il nome dell'utente.
     */
    public Utente(String email, String nome) throws UtenteException {
        
        if (!isEmailValida(email)) {
            throw new UtenteException("Formato email errato, deve essere del tipo 'username@dominio.it'");
        }
        this.email = email;
        
        if (!isNomeValido(nome)) {
            throw new UtenteException("Formato nome errato, accetta solo caratteri alfanumerici");
        }
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }
    
    private boolean isEmailValida(String email) {
        return email.matches(emailRegex);
    }
    
    private boolean isNomeValido(String nome) {
        return nome.matches(nomeRegex);
    }

    @Override
    public String toString() {
        return "[email= " + email + ", nome= " + nome + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Utente other = (Utente) obj;
        return Objects.equals(email, other.email) && Objects.equals(nome, other.nome);
    }
}
