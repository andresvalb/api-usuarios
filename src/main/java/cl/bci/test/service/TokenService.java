package cl.bci.test.service;

import cl.bci.test.repository.Usuario;
import cl.bci.test.repository.dao.UsuarioRepository;
import cl.bci.test.security.service.MyUserDetailsService;
import cl.bci.test.security.utils.JwtUtil;
import cl.bci.test.service.model.AutenticacionResponse;
import cl.bci.test.service.model.CredencialesVO;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class TokenService.
 */
@RestController
@RequestMapping("/api/v1")
public class TokenService {

	/** The password encoder. */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/** The auth manager. */
	@Autowired
	private AuthenticationManager authManager;

	/** The mi user details service. */
	@Autowired
	private MyUserDetailsService miUserDetailsService;

	/** The jwt util. */
	@Autowired
	private JwtUtil jwtUtil;

	/** The user repo. */
	@Autowired
	private UsuarioRepository userRepo;

	/**
	 * Login.
	 *
	 * @param autLogin the aut login
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("token")
	public ResponseEntity<?> login(@RequestBody CredencialesVO autLogin) throws Exception {

		try {

			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(autLogin.getUsername(), autLogin.getPassword()));

		} catch (BadCredentialsException ex) {
			throw new Exception("Error en el username o contraseña " + ex.getMessage());
		}

		// Actualizo la fecha de la login.
		Usuario user = userRepo.findByEmail(autLogin.getUsername());

		user.setLast_login(new Date());

		userRepo.save(user);

		// Obtenemos los datos del usuario de la BD para construir el token
		final UserDetails userDetails = miUserDetailsService.loadUserByUsername(autLogin.getUsername());
		final String token = "Bearer " + jwtUtil.creatToken(userDetails);

		// Regresamos el token
		return ResponseEntity.ok(new AutenticacionResponse(token));

	}

}
