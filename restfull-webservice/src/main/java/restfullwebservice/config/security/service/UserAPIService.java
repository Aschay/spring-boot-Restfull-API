package restfullwebservice.config.security.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restfullwebservice.config.security.model.UserAPI;
import restfullwebservice.config.security.repository.UserAPIRepository;


@Service
public class UserAPIService {

	@Autowired
	private UserAPIRepository userRepository;

	public Optional<UserAPI> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}


	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

}
