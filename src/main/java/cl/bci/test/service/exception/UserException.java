package cl.bci.test.service.exception;

/**
 * The Class UserException.
 */
public class UserException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new mensaje exception.
	 *
	 * @param mensaje the mensaje
	 */
	public UserException(String mensaje) {
		super(mensaje);
	}

	/**
	 * Instantiates a new mensaje exception.
	 */
	public UserException() {
		super();
	}
}
