package restfullwebservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import restfullwebservice.exception.RessourceNotFoundException;
import restfullwebservice.model.User;
import restfullwebservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(UUID id) {

		return userRepository.findById(id);
	}

	@Transactional
	public User addUser(User newUser) {
		return userRepository.save(newUser);
	}

	public void removeUser(UUID userId) {
		if (!userRepository.findById(userId).isPresent()) {
			throw new RessourceNotFoundException("User", "userId", userId);
		}
		userRepository.deleteById(userId);
	}

	public int editUser(User user, UUID id) {
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String email = user.getEmail();
		String username = user.getUsername();
		return userRepository.editUserById(firstName, lastName, email, username, id);
	}
}
