package restfullwebservice.config.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS).permitAll()
						                       .requestMatchers("/api/**").authenticated()
						                       .anyRequest().permitAll());
		
		http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
		
		return http.build();
	}
	
	
//		http.csrf(AbstractHttpConfigurer::disable);
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.exceptionHandling(
//				exception -> exception.accessDeniedHandler(jwtAutho).authenticationEntryPoint(jwtAuthen));
//		http.authorizeHttpRequests(authorizeRequests -> 
//		authorizeRequests.requestMatchers(
//				"/auth/**",
//				"/swagger-ui-custom.html", "/swagger-ui.html", 
//				"/swagger-ui/**", "/v3/api-docs/**", "/webjars/**",
//				"/swagger-ui/index.html", "/api-docs/**")
//		       .permitAll());
//		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
//		HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(
//				new ClearSiteDataHeaderWriter(Directive.COOKIES));
//		http.logout(logout -> logout.logoutUrl("/auth/logout").logoutSuccessUrl("/").permitAll()
//				.addLogoutHandler(clearSiteData));
//		http.authenticationProvider(authenticationProvider());
//		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
//		return http.build();



}