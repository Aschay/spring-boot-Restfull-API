package restfullwebservice.config.security.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restfullwebservice.config.security.model.UserAPI;


@Repository
public interface UserAPIRepository extends JpaRepository<UserAPI, Integer> {

	Optional<UserAPI> findByUsername(String username);
	Boolean existsByUsername(String username);

}
