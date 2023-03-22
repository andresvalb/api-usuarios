package cl.bci.test.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The Class Phones.
 */
@Entity
@Table(name = "phones")
public class Phones {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The number. */
	private String number;

	/** The citycode. */
	private String citycode;

	/** The contrycode. */
	private String contrycode;

	/** The usuario. */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "usuario_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Usuario usuario;

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 *
	 * @param number the new number
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Gets the citycode.
	 *
	 * @return the citycode
	 */
	public String getCitycode() {
		return citycode;
	}

	/**
	 * Sets the citycode.
	 *
	 * @param citycode the new citycode
	 */
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	/**
	 * Gets the contrycode.
	 *
	 * @return the contrycode
	 */
	public String getContrycode() {
		return contrycode;
	}

	/**
	 * Sets the contrycode.
	 *
	 * @param contrycode the new contrycode
	 */
	public void setContrycode(String contrycode) {
		this.contrycode = contrycode;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the usuario.
	 *
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Sets the usuario.
	 *
	 * @param usuario the new usuario
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
