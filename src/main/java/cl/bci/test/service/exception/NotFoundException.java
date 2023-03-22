package cl.bci.test.service.exception;

/**
 * The Class NotFoundException.
 */
public class NotFoundException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new mensaje exception.
	 *
	 * @param mensaje the mensaje
	 */
	public NotFoundException(String mensaje) {
		super(mensaje);
	}

	/**
	 * Instantiates a new mensaje exception.
	 */
	public NotFoundException() {
		super();
	}
}
