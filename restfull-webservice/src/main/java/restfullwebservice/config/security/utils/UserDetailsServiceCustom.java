package restfullwebservice.config.security.utils;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import restfullwebservice.config.security.model.UserAPI;
import restfullwebservice.config.security.repository.UserAPIRepository;


@Service
public class UserDetailsServiceCustom implements UserDetailsService {

	@Autowired
	public UserAPIRepository repository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserAPI> userDetail = repository.findByUsername(username);
		return userDetail.map(UserDetailsCustom::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}



	
}