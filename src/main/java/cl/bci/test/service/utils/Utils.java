package cl.bci.test.service.utils;

import cl.bci.test.repository.Usuario;
import cl.bci.test.repository.dao.UsuarioRepository;
import cl.bci.test.service.exception.DuplicateException;
import cl.bci.test.service.exception.NotFoundException;
import cl.bci.test.service.exception.UserException;
import cl.bci.test.service.model.RespuestaError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The Class Utils.
 */
@Component
public class Utils {

	/** The regx email. */
	private String regxEmail = "^[\\w-_.+]*[\\w-_.]@([\\w]+[.])+[\\w]+[\\w]$";

	/** The valida pass. */
	@Value("${valida.password}")
	private String validaPass;

	/** The user repo. */
	@Autowired
	private UsuarioRepository userRepo;

	/**
	 * Valida email.
	 *
	 * @param email the email
	 * @throws UserException the user exception
	 */
	public void validaEmail(String email) throws UserException {

		if (null == email) {
			throw new UserException("Formato de email incorrecto.");
		}

		Pattern pattern = Pattern.compile(regxEmail);

		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {

			throw new UserException("Formato de email incorrecto.");

		}

	}

	/**
	 * Valida formato password.
	 *
	 * @param password the password
	 * @throws UserException the user exception
	 */
	public void validaFormatoPassword(String password) throws UserException {

		if (null == password) {
			throw new UserException("Formato de password incorrecto.");
		}

		Pattern pattern = Pattern.compile(validaPass);

		Matcher matcher = pattern.matcher(password);

		if (!matcher.matches()) {

			throw new UserException("Formato de password incorrecto.");

		}

	}

	/**
	 * Valido existencia email.
	 *
	 * @param email the email
	 * @throws DuplicateException the duplicate exception
	 */
	public void validoExistenciaEmail(String email) throws DuplicateException {

		Usuario res = userRepo.findByEmail(email);

		if (res != null) {
			// como existe el recurso se finaliza la peticion con 409 Conflict.
			throw new DuplicateException("El correo ya registrado");
		}

	}

	/**
	 * Gets the mensaje error.
	 *
	 * @param mensaje the mensaje
	 * @return the mensaje error
	 */
	public RespuestaError getMensajeError(String mensaje) {

		RespuestaError error = new RespuestaError();
		error.setMensaje(mensaje);

		return error;

	}

	/**
	 * Valido existencia email.
	 *
	 * @param email the email
	 * @throws DuplicateException the duplicate exception
	 */
	public void validoNoExistenciaEmail(String email) throws NotFoundException {

		Usuario res = userRepo.findByEmail(email);

		if (res == null) {
			throw new NotFoundException("El usuario no existe");
		}

	}

}
