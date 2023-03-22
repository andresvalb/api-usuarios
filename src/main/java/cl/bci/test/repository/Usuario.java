package cl.bci.test.repository;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * The Class Usuario.
 */
@Entity
@Table(name = "usuario")
public class Usuario {

	/** The id. */
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "VARCHAR(255)")
	private String id;

	/** The name. */
	private String name;

	/** The email. */
	private String email;

	/** The password. */
	private String password;

	/** The created. */
	private Date created;

	/** The last login. */
	private Date last_login;

	/** The token. */
	@Column(length = 300)
	private String token;

	/** The modified. */
	private Date modified;

	/** The phones. */
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Phones> phones = new HashSet<>();

	/**
	 * Gets the phones.
	 *
	 * @return the phones
	 */
	public Set<Phones> getPhones() {
		return phones;
	}

	/**
	 * Sets the phones.
	 *
	 * @param phones the new phones
	 */
	public void setPhones(Set<Phones> phones) {
		this.phones = phones;
		for (Phones phone : phones) {
			phone.setUsuario(this);
		}
	}

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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
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

}
