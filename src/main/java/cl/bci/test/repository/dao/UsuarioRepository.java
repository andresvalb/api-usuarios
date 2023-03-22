package cl.bci.test.repository.dao;

import cl.bci.test.repository.Phones;
import cl.bci.test.repository.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * The Interface UsuarioRepository.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the usuario
	 */
	Usuario findByEmail(String email);

	/**
	 * Find phone for user.
	 *
	 * @param id    the id
	 * @param email the email
	 * @return the sets the
	 */
	@Query("SELECT p FROM Phones p WHERE p.id = :id and usuario.email = :email")
	Phones findPhoneForUser(Long id, String email);

}
