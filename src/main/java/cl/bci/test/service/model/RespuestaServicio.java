package cl.bci.test.service.model;

import java.util.Date;

/**
 * The Class RespuestaServicio.
 */
public class RespuestaServicio {

	/** The id. */
	private String id;

	/** The created. */
	private Date created;

	/** The modified. */
	private Date modified;

	/** The last login. */
	private Date last_login;

	/** The token. */
	private String token;

	/** The isactive. */
	private Boolean isactive;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the created.
	 *
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * Sets the created.
	 *
	 * @param created the new created
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * Gets the modified.
	 *
	 * @return the modified
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * Sets the modified.
	 *
	 * @param modified the new modified
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}

	/**
	 * Gets the last login.
	 *
	 * @return the last login
	 */
	public Date getLast_login() {
		return last_login;
	}

	/**
	 * Sets the last login.
	 *
	 * @param last_login the new last login
	 */
	public void setLast_login(Date last_login) {
		this.last_login = last_login;
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

	/**
	 * Gets the isactive.
	 *
	 * @return the isactive
	 */
	public Boolean getIsactive() {
		return isactive;
	}

	/**
	 * Sets the isactive.
	 *
	 * @param isactive the new isactive
	 */
	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

}
