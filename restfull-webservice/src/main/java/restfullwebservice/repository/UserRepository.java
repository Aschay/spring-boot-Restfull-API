package restfullwebservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import restfullwebservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	

	@Transactional
	@Modifying
	@Query("update User u set u.firstName=?1, u.lastName=?2, u.username=?3, u.email =?4 where  u.id =?5 ")
	int editUserById(String firstName, String lastName, String username, String email, UUID id);
	
	

}
