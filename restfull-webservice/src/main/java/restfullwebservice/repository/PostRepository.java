package restfullwebservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import restfullwebservice.model.Post;
import restfullwebservice.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	Optional<Post> findByIdAndUser(int postId, User user);

	@Transactional
	@Modifying
	@Query("update Post p set p.title = ?1, p.content = ?2 where p.id = ?3")
	void editPostById(String title, String content, int postId);

}
