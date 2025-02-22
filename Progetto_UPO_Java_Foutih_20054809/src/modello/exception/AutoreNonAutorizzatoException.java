package modello.exception;

@SuppressWarnings("serial")
public class AutoreNonAutorizzatoException extends UtenteException {
	
	public AutoreNonAutorizzatoException(String msg) {
		super(msg);
	}
}
