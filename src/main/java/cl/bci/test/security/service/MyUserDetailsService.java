package cl.bci.test.security.service;

import cl.bci.test.repository.Usuario;
import cl.bci.test.repository.dao.UsuarioRepositorySecurity;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The Class MiUserDetailsService.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	/** The usuario repository. */
	@Autowired
	private UsuarioRepositorySecurity usuarioRepository;

	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

		usuario.orElseThrow(() -> new UsernameNotFoundException("No se encontro el usuario " + username + " en la BD"));

		return usuario.map(MyUserDetails::new).get();

	}

}
