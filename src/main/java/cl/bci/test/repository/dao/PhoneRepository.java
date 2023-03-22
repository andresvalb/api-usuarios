package cl.bci.test.repository.dao;

import cl.bci.test.repository.Phones;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * The Interface UsuarioRepository.
 */
public interface PhoneRepository extends JpaRepository<Phones, Integer> {

	/**
	 * Find phone id.
	 *
	 * @param id the id
	 * @return the sets the
	 */
	Set<Phones> findById(Long id);

	/**
	 * Find phone for user.
	 *
	 * @param id    the id
	 * @param email the email
	 * @return the sets the
	 */
	@Query("SELECT p FROM Phones p WHERE p.id = :id and usuario.email = :email")
	Set<Phones> findPhoneForUser(Long id, String email);

}
