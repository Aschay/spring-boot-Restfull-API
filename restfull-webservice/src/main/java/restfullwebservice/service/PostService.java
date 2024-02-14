package restfullwebservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import restfullwebservice.exception.RessourceNotFoundException;
import restfullwebservice.model.Post;
import restfullwebservice.model.User;
import restfullwebservice.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	PostRepository postRepository;
	

	public List<Post> getAllPostsByUserId(User user) {
		return postRepository.findByUser(user);
	}

	public Optional<Post> getPostById(int postId) {
		Optional<Post> post = postRepository.findById(postId);
		return post;
	}
	
	public Optional<Post>findByIdAndUser(int postId,User user){
		return postRepository.findByIdAndUser(postId, user);
	}

	@Transactional
	public Post addPost(Post newPost) {
		postRepository.save(newPost);
		return newPost;
	}

	public void removePost(int PostId) {
		if (!postRepository.findById(PostId).isPresent()) {
			throw new RessourceNotFoundException("Post", "PostId", PostId);
		}
		postRepository.deleteById(PostId);
	}

	@Transactional
	public void editPostPartial(Post Post, int postId) {
      postRepository.editPostById(Post.getTitle(),Post.getContent(), postId);		
	}
	@Transactional
	public Post editPostFully(Post Post, int postId) {
      Post.setId(postId);	
      Post.setDateCreated(LocalDateTime.now());
      return postRepository.save(Post);
      
	}
	

}
