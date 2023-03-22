package cl.bci.test.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Clase que permite analizar el token de la solicitud.
 */
@Component
public class JwtUtil {

	/** The secreto. */
	@Value("${token.secret}")
	private String secreto;

	/** The expiracion. */
	@Value("${token.expiracion}")
	private String expiracion;

	/**
	 * Este metodo solo extrae una parte del token [header.payload.signature] siendo
	 * los claims registrados los claims (iss, sub, aude, exp)
	 * 
	 * @param token requiere el token para extraer la parte del claim
	 * @return El conjunto de Claims
	 */
	public Claims extraerContenidoClaims(String token) {

		// valido dentro del header
		return Jwts.parser().setSigningKey(secreto).parseClaimsJws(token).getBody();
	}

	/**
	 * Extrae del claim el nombre que contiene el subject.
	 *
	 * @param token token al que se le extraera el claim subject.
	 * @return el username del token
	 */
	public String extraerUsername(String token) {

		return extraerContenidoClaims(token).getSubject();
	}

	/**
	 * Extrae del claim el contenido de "exp" para identificar el tiempo de
	 * vencimiento del token.
	 *
	 * @param token token a extraer el tiempo de vencimiento
	 * @return La fecha de vencimiento.
	 */
	public Date extraerTiempoVencimiento(String token) {
		return extraerContenidoClaims(token).getExpiration();
	}

	/**
	 * Determina si se acepta procesar o no el token de acuerdo a la fecha y hora
	 * actual.
	 *
	 * @param token Del que se extrae la fecha de vencimiento
	 * @return si ya vencio o no el token
	 */
	public boolean isTokenExpiration(String token) {

		Date fechaExp = extraerTiempoVencimiento(token);

		return fechaExp.before(new Date());
	}

	/**
	 * Metodo que preparar la estructura del payload.
	 * 
	 * @param payload Sera el payload que sera usado para construir los claims sobre
	 *                el.
	 * @param subject Establece el username del token
	 * @return el String del payload del token
	 */
	public String prepararEstructuraToken(Map<String, Object> payload, String subject) {
		return Jwts.builder().setClaims(payload).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(expiracion)))
				.signWith(SignatureAlgorithm.HS512, secreto).compact();
	} // fin del metodo

	/**
	 * Es el encargado de crear el token por medio del <code>UserDetails</code> dado
	 * como parametro invoca al metodo que construye la estructura (payload)-.
	 *
	 * @param userDetails para obtener el username del usuario autenticado.
	 * @return el token [header.payload.signature]
	 */
	public String creatToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return prepararEstructuraToken(claims, userDetails.getUsername());
	}

	/**
	 * Validar token.
	 *
	 * @param token       the token
	 * @param userDetails the user details
	 * @return true, if successful
	 */
	public boolean validarToken(String token, UserDetails userDetails) {
		final String username = extraerUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpiration(token));
	}

} // fin de la clase utilidad
