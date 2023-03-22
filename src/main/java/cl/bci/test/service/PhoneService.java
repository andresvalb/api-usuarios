package cl.bci.test.service;

import cl.bci.test.repository.Phones;
import cl.bci.test.repository.Usuario;
import cl.bci.test.repository.dao.PhoneRepository;
import cl.bci.test.repository.dao.UsuarioRepository;
import cl.bci.test.security.utils.JwtUtil;
import cl.bci.test.service.exception.NotFoundException;
import cl.bci.test.service.exception.UserException;
import cl.bci.test.service.model.PhonesVO;
import cl.bci.test.service.utils.Utils;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class ApiService.
 */
@RestController
@RequestMapping("/api/v1")
public class PhoneService {

	/** The Constant ERROR_INTERNO. */
	private static final String ERROR_INTERNO = "Error interno.";

	/** The Constant BEARER. */
	private static final String BEARER = "Bearer ";

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(PhoneService.class);

	/** The valida pass. */
	@Value("${valida.password}")
	private String validaPass;

	/** The user repo. */
	@Autowired
	private UsuarioRepository userRepo;

	@Autowired
	private PhoneRepository phoneRepo;

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
	 * Gets the telefonos por usuarios.
	 *
	 * @param email the email
	 * @return the telefonos por usuarios
	 */
	@GetMapping("/users/{email}/phones")
	public ResponseEntity<?> getTelefonosPorUsuarios(@PathVariable String email) {

		logger.info("Entrada al metodo getUser.");

		try {

			Usuario res = userRepo.findByEmail(email);

			// Valido si existe emil.
			util.validoNoExistenciaEmail(email);

			// Si no tiene telefonos lanzo error.
			if (null == res.getPhones() || res.getPhones().isEmpty()) {
				throw new NotFoundException("El usuario no tiene telefonos asociados.");
			}

			return new ResponseEntity<>(res.getPhones(), HttpStatus.valueOf(200));

		} catch (NotFoundException e) {

			return new ResponseEntity<>(util.getMensajeError(e.getMessage()), HttpStatus.valueOf(404));

		} catch (Exception e) {

			return new ResponseEntity<>(util.getMensajeError(ERROR_INTERNO), HttpStatus.valueOf(500));

		}

	}

	@GetMapping("/users/{email}/phones/{id}")
	public ResponseEntity<?> getTelefonoPorUsuarios(@PathVariable String email, @PathVariable Long id) {

		logger.info("Entrada al metodo getUser.");

		try {

			Phones res = userRepo.findPhoneForUser(id, email);

			// Si no tiene telefonos lanzo error.
			if (null == res) {
				throw new NotFoundException("El usuario no tiene telefonos asociados.");
			}

			return new ResponseEntity<>(res, HttpStatus.valueOf(200));

		} catch (NotFoundException e) {

			return new ResponseEntity<>(util.getMensajeError(e.getMessage()), HttpStatus.valueOf(404));

		} catch (Exception e) {

			return new ResponseEntity<>(util.getMensajeError(ERROR_INTERNO), HttpStatus.valueOf(500));

		}

	}

	@PostMapping("/users/{email}/phones")
	public ResponseEntity<?> addTelefonosPorUsuario(@RequestBody PhonesVO datos, @PathVariable String email) {

		logger.info("Entrada al metodo addUser.");

		try {
			// Valido Email.
			util.validaEmail(email);

			Usuario user = userRepo.findByEmail(email);

			if (null == user || user.getPhones().isEmpty()) {
				throw new NotFoundException("El usuario no tiene telefonos asociados.");
			}

			Phones telefono = new Phones();

			telefono.setNumber(datos.getNumber());
			telefono.setCitycode(datos.getCitycode());
			telefono.setContrycode(datos.getContrycode());
			telefono.setUsuario(user);

			user.getPhones().add(telefono);

			Usuario usuario = userRepo.save(user);

			return new ResponseEntity<>(usuario.getPhones(), HttpStatus.valueOf(201));

		} catch (UserException e) {

			return new ResponseEntity<>(util.getMensajeError(e.getMessage()), HttpStatus.valueOf(400));

		} catch (NotFoundException e) {

			return new ResponseEntity<>(util.getMensajeError(e.getMessage()), HttpStatus.valueOf(404));

		} catch (Exception e) {

			return new ResponseEntity<>(util.getMensajeError(ERROR_INTERNO), HttpStatus.valueOf(500));

		}
	}

	@PutMapping("/users/{email}/phones/{id}")
	public ResponseEntity<?> updateTelefonosPorUsuario(@PathVariable Long id, @PathVariable String email,
			@RequestBody PhonesVO datos) {

		logger.info("Entrada al metodo addUser.");

		try {
			// Valido Email.
			util.validaEmail(email);

			Phones res = userRepo.findPhoneForUser(id, email);

			if (null == res) {
				throw new NotFoundException("El usuario no tiene telefonos asociados.");
			}

			res.setNumber(datos.getNumber());
			res.setCitycode(datos.getCitycode());
			res.setContrycode(datos.getContrycode());

			Phones phone = phoneRepo.save(res);

			return new ResponseEntity<>(phone, HttpStatus.valueOf(201));

		} catch (UserException e) {

			return new ResponseEntity<>(util.getMensajeError(e.getMessage()), HttpStatus.valueOf(400));

		} catch (NotFoundException e) {

			return new ResponseEntity<>(util.getMensajeError(e.getMessage()), HttpStatus.valueOf(404));

		} catch (Exception e) {

			return new ResponseEntity<>(util.getMensajeError(ERROR_INTERNO), HttpStatus.valueOf(500));

		}
	}

}
