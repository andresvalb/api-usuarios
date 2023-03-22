package cl.bci.test.security.filter;

import cl.bci.test.security.service.MyUserDetailsService;
import cl.bci.test.security.utils.JwtUtil;
import cl.bci.test.service.model.RespuestaError;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * Clase que interceptara las peticiones para asegurarse de validar el token del
 * usuario hacia el servidor
 */
@Component
public class AuthFiltroToken extends OncePerRequestFilter {

	private static final String APPLICATION_JSON = "application/json";

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * Do filter internal.
	 *
	 * @param request     the request
	 * @param response    the response
	 * @param filterChain the filter chain
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {

			// Extraemos el header Authorization: es donde se encuentra el token enviado por
			// el usuario
			// Podemos poner esta parte en la clase de utilidad del token
			final String headerAuth = request.getHeader("Authorization");

			String token = null;
			String username = null;
			// Extraemos el token de la cabecera
			if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
				// Lo extraemos quitando el "Bearer " para solo tener el token
				token = headerAuth.substring(7);
				// Buscamos el username del usuario en el token
				username = jwtUtil.extraerUsername(token);
			}

			// Validamos los valores extraidos del token y el contexto de seguridad
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				// obtenemos el nombre del usuario de nuestra BD y poblamos el UserDetails
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				// Validamos el token si aun esta vigente y si concuerda con el usuario de la BD
				if (jwtUtil.validarToken(token, userDetails)) {

					UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					// Generamos los detalles de la autenticacion por token
					userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					// y establecemos el tipo de seguridad
					SecurityContextHolder.getContext().setAuthentication(userPassAuthToken);
				} else {

					response.resetBuffer();
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					response.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
					RespuestaError msgError = new RespuestaError();
					msgError.setMensaje("Token no valido.");
					response.getOutputStream().print(new ObjectMapper().writeValueAsString(msgError));
					response.flushBuffer();
					return;

				}
			}

			filterChain.doFilter(request, response);

		} catch (ExpiredJwtException e) {

			response.resetBuffer();
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
			RespuestaError msgError = new RespuestaError();
			msgError.setMensaje("Token Invalido.");
			response.getOutputStream().print(new ObjectMapper().writeValueAsString(msgError));
			response.flushBuffer();

		} catch (Exception e) {

			e.printStackTrace();

			response.resetBuffer();
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
			RespuestaError msgError = new RespuestaError();
			msgError.setMensaje("No permitido");
			if (e.getMessage().contains("Bad credentials")) {
				msgError.setMensaje("Username o password incorrecto.");
			}
			response.getOutputStream().print(new ObjectMapper().writeValueAsString(msgError));
			response.flushBuffer();

		}

	}

}
