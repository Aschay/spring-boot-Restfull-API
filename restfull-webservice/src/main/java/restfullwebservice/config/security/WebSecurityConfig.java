package restfullwebservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;

import restfullwebservice.config.security.jwt.AuthFilter;
import restfullwebservice.config.security.jwt.AuthenEntryPoint;
import restfullwebservice.config.security.jwt.AuthoHandler;
import restfullwebservice.config.security.utils.UserDetailsServiceCustom;

import org.springframework.beans.factory.annotation.Autowired;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class WebSecurityConfig {

	@Autowired
	AuthFilter authFilter;

	@Autowired
	private AuthenEntryPoint jwtAuthen;
	@Autowired
	private AuthoHandler jwtAutho;

	@Autowired
	UserDetailsServiceCustom userDetailsService;



	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.exceptionHandling(
				exception -> exception.accessDeniedHandler(jwtAutho).authenticationEntryPoint(jwtAuthen));
		
		http.authorizeHttpRequests(authorizeRequests -> 
		authorizeRequests.requestMatchers(
				"/auth/**",
				"/swagger-ui-custom.html", "/swagger-ui.html", 
				"/swagger-ui/**", "/v3/api-docs/**", "/webjars/**",
				"/swagger-ui/index.html", "/api-docs/**")
		       .permitAll());

		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		
		HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(
				new ClearSiteDataHeaderWriter(Directive.COOKIES));
		http.logout(logout -> logout.logoutUrl("/auth/logout").logoutSuccessUrl("/").permitAll()
				.addLogoutHandler(clearSiteData));
		
		http.authenticationProvider(authenticationProvider());
		
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}