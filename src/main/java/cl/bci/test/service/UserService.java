package cl.bci.test.service;

import cl.bci.test.repository.Phones;
import cl.bci.test.repository.Usuario;
import cl.bci.test.repository.dao.UsuarioRepository;
import cl.bci.test.security.utils.JwtUtil;
import cl.bci.test.service.exception.DuplicateException;
import cl.bci.test.service.exception.NotFoundException;
import cl.bci.test.service.exception.UserException;
import cl.bci.test.service.model.PhonesVO;
import cl.bci.test.service.model.RespuestaServicio;
import cl.bci.test.service.model.UsuarioVO;
import cl.bci.test.service.utils.Utils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class ApiService.
 */
@RestController
@RequestMapping("/api/v1")
public class UserService {

	private static final String ERROR_INTERNO = "Error interno.";

	/** The Constant BEARER. */
	private static final String BEARER = "Bearer ";

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(UserService.class);

	/** The valida pass. */
	@Value("${valida.password}")
	private String validaPass;

	/** The user repo. */
	@Autowired
	private UsuarioRepository userRepo;

	/** The jwt util. */
	@Autowired
	private JwtUtil jwtUtil;

	/** The util. */
	@Autowired
	private Utils util;

	/** The password encoder. */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * Adds the user.
	 *
	 * @param datos the datos
	 * @param token the token
	 * @return the response entity
	 */
	@PostMapping("/users")
	public ResponseEntity<?> addUser(@RequestBody UsuarioVO datos,
			@RequestHeader(value = "Authorization") String token) {

		logger.info("Entrada al metodo addUser.");

		try {
			// Valido Email.
			util.validaEmail(datos.getEmail());

			// Valido formato de las password.
			util.validaFormatoPassword(datos.getPassword());

			// Valido si el email ya esta registrado en bd.
			util.validoExistenciaEmail(datos.getEmail());

			Usuario usuIn = new Usuario();

			Date fechaActual = new Date();

			usuIn.setName(datos.getName());
			usuIn.setEmail(datos.getEmail());
			usuIn.setPassword(passwordEncoder.encode(datos.getPassword()));
			usuIn.setCreated(fechaActual);
			usuIn.setToken(token.replace(BEARER, ""));
			usuIn.setLast_login(fechaActual);

			Set<Phones> listPhone = new HashSet<>();

			if (datos.getPhones() != null) {

				for (PhonesVO phon : datos.getPhones()) {

					Phones p = new Phones();

					p.setNumber(phon.getNumber());
					p.setCitycode(phon.getCitycode());
					p.setContrycode(phon.getContrycode());

					listPhone.add(p);

				}
			}

			usuIn.setPhones(listPhone);

			Usuario usuOut = userRepo.save(usuIn);

			// Salida de la operacion.
			RespuestaServicio salida = new RespuestaServicio();

			salida.setId(usuOut.getId());
			salida.setCreated(usuOut.getCreated());
			salida.setModified(usuOut.getModified());
			salida.setLast_login(usuOut.getLast_login());
			salida.setToken(usuOut.getToken());
			salida.setIsactive(!jwtUtil.isTokenExpiration(token.replace(BEARER, "")));

			return new ResponseEntity<>(salida, HttpStatus.valueOf(201));

		} catch (UserException e) {

			return new ResponseEntity<>(util.getMensajeError(e.getMessage()), HttpStatus.valueOf(400));

		} catch (DuplicateException e) {

			return new ResponseEntity<>(util.getMensajeError(e.getMessage()), HttpStatus.valueOf(409));

		} catch (Exception e) {

			return new ResponseEntity<>(util.getMensajeError(ERROR_INTERNO), HttpStatus.valueOf(500));

		}
	}

	/**
	 * Gets the user.
	 *
	 * @param email the email
	 * @return the user
	 */
	@GetMapping("/users/{email}")
	public ResponseEntity<?> getUser(@PathVariable String email) {

		logger.info("Entrada al metodo getUser.");

		try {

			Usuario res = userRepo.findByEmail(email);

			// Valido si existe emil.
			util.validoNoExistenciaEmail(email);

			return new ResponseEntity<>(res, HttpStatus.valueOf(200));

		} catch (NotFoundException e) {

			return new ResponseEntity<>(util.getMensajeError(e.getMessage()), HttpStatus.valueOf(404));

		} catch (Exception e) {

			return new ResponseEntity<>(util.getMensajeError(ERROR_INTERNO), HttpStatus.valueOf(500));

		}

	}

	/**
	 * Update user.
	 *
	 * @param datos the datos
	 * @param token the token
	 * @param email the email
	 * @return the response entity
	 */
	@PutMapping("/users/{email}")
	public ResponseEntity<?> updateUser(@RequestBody UsuarioVO datos,
			@RequestHeader(value = "Authorization") String token, @PathVariable String email) {

		try {

			logger.info("Entrada al metodo updateUser.");

			// Valido Email.
			util.validaEmail(email);

			// Valido formato de las password.
			util.validaFormatoPassword(datos.getPassword());

			// Valido si el email no esta registrado en bd.
			Usuario res = userRepo.findByEmail(email);

			if (res == null) {
				throw new NotFoundException("El usuario no existe");
			}

			Date fechaActual = new Date();

			res.setName(datos.getName());
			res.setPassword(passwordEncoder.encode(datos.getPassword()));
			res.setModified(fechaActual);

			Usuario usuOut = userRepo.save(res);

			// Salida de la operacion.
			RespuestaServicio salida = new RespuestaServicio();

			salida.setId(usuOut.getId());
			salida.setCreated(usuOut.getCreated());
			salida.setModified(usuOut.getModified());
			salida.setLast_login(usuOut.getLast_login());
			salida.setToken(usuOut.getToken());
			salida.setIsactive(!jwtUtil.isTokenExpiration(token.replace(BEARER, "")));

			return new ResponseEntity<>(salida, HttpStatus.valueOf(200));

		} catch (UserException e) {

			return new ResponseEntity<>(util.getMensajeError(e.getMessage()), HttpStatus.valueOf(400));

		} catch (NotFoundException e) {

			return new ResponseEntity<>(util.getMensajeError(e.getMessage()), HttpStatus.valueOf(404));

		} catch (Exception e) {

			return new ResponseEntity<>(util.getMensajeError(ERROR_INTERNO), HttpStatus.valueOf(500));

		}
	}

}
