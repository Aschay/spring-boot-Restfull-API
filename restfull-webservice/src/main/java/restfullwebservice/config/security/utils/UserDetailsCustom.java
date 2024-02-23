package restfullwebservice.config.security.utils;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import restfullwebservice.config.security.model.UserAPI;

public class UserDetailsCustom implements UserDetails {
	private static final long serialVersionUID = -3092753139449108760L;
	
	private String name;
	private String password;
	private List<GrantedAuthority> authorities;

	public UserDetailsCustom (UserAPI UserAPI) {
		name = UserAPI.getUsername();
		password = UserAPI.getPassword();
		authorities = Arrays.stream(
				  UserAPI.getRoles().split(",")).map(SimpleGrantedAuthority::new)
				  .collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return name;
	}

}
