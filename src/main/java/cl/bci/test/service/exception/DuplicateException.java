package cl.bci.test.service.exception;

/**
 * The Class DuplicateException.
 */
public class DuplicateException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new mensaje exception.
	 *
	 * @param mensaje the mensaje
	 */
	public DuplicateException(String mensaje) {
		super(mensaje);
	}

	/**
	 * Instantiates a new mensaje exception.
	 */
	public DuplicateException() {
		super();
	}
}
