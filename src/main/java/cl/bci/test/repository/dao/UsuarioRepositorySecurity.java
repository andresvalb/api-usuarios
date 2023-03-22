package cl.bci.test.repository.dao;

import cl.bci.test.repository.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface UsuarioRepositorySecurity.
 */
public interface UsuarioRepositorySecurity extends JpaRepository<Usuario, Integer> {

	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the optional
	 */
	Optional<Usuario> findByEmail(String email);

}
