package cl.bci.test;

import cl.bci.test.security.filter.AuthFiltroToken;
import cl.bci.test.security.service.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The Class SecurityConfig.
 */
@EnableWebSecurity
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

	/** The user details service. */
	@Autowired
	private MyUserDetailsService userDetailsService;

	/** The auth filtro token. */
	@Autowired
	private AuthFiltroToken authFiltroToken;

	/**
	 * Pass encoder.
	 *
	 * @return the b crypt password encoder
	 */
	@Bean
	public BCryptPasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Authentication manager bean.
	 *
	 * @return the authentication manager
	 * @throws Exception the exception
	 */
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Configure.
	 *
	 * @param auth the auth
	 * @throws Exception the exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passEncoder());
	}

	/**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// libero el path del token y consola h2
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/token").permitAll()
				.antMatchers("/h2-console/**").permitAll().anyRequest().authenticated();

		// Para permitir consola h2.
		http.headers().frameOptions().sameOrigin();

		// Indicamos que usaremos un filtro
		http.addFilterBefore(authFiltroToken, UsernamePasswordAuthenticationFilter.class);
	}
}
