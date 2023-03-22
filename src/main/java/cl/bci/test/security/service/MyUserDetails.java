package cl.bci.test.security.service;

import cl.bci.test.repository.Usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The Class MiUserDetails.
 */
public class MyUserDetails implements UserDetails {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The active. */
	private boolean active;

	/** The authorities. */
	private List<SimpleGrantedAuthority> authorities;

	/**
	 * Instantiates a new mi user details.
	 *
	 * @param usuario the usuario
	 */
	public MyUserDetails(Usuario usuario) {
		this.authorities = new ArrayList<SimpleGrantedAuthority>();
		this.username = usuario.getEmail();
		this.password = usuario.getPassword();
		this.active = true;

	}

	/**
	 * Gets the authorities.
	 *
	 * @return the authorities
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	@Override
	public String getPassword() {
		return this.password;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	@Override
	public String getUsername() {
		return this.username;
	}

	/**
	 * Checks if is account non expired.
	 *
	 * @return true, if is account non expired
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Checks if is account non locked.
	 *
	 * @return true, if is account non locked
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Checks if is credentials non expired.
	 *
	 * @return true, if is credentials non expired
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	@Override
	public boolean isEnabled() {
		return this.active;
	}
}
