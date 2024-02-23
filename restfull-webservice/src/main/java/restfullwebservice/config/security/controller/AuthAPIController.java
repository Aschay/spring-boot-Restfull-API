package restfullwebservice.config.security.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import restfullwebservice.config.security.dto.AuthRequest;
import restfullwebservice.config.security.dto.AuthResponse;
import restfullwebservice.config.security.jwt.JwtProvider;

@RestController
@Tag(name = "Authentication", description = "The Authentication API. Contains operations like change password, forgot password, login, logout, etc.")
public class AuthAPIController {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private AuthenticationManager authenticationManager;


	@Operation(summary = "User Authentication", description = "Authenticate the user and return a JWT token if the user is valid.")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\n"
			+ "  \"username\": \"admin\",\n" + "  \"password\": \"admin\"\n"
			+ "}", summary = "User Authentication Example")))
	@PostMapping(value="auth/signin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			String username = authRequest.getUsername();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
			String token = jwtProvider.generateToken(username);
			return ResponseEntity.ok(new AuthResponse(token, username, authorities));
		} else {
			throw new BadCredentialsException("Username or password invalid");
		}
	}

	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

	@PostMapping("/auth/logout")
	public String performLogout(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) {
		this.logoutHandler.logout(request, response, authentication);
		return "redirect:/home";
	}

}
