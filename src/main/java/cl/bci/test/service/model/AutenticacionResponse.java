package cl.bci.test.service.model;

import java.io.Serializable;

/**
 * The Class AutenticacionResponse.
 */
public class AutenticacionResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The token. */
	private String token;

	/**
	 * Instantiates a new autenticacion response.
	 *
	 * @param token the token
	 */
	public AutenticacionResponse(String token) {
		this.token = token;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
