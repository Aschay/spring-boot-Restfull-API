package restfullwebservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import restfullwebservice.exception.RessourceNotFoundException;
import restfullwebservice.model.Post;
import restfullwebservice.model.User;
import restfullwebservice.service.PostService;
import restfullwebservice.service.UserService;
import restfullwebservice.utils.PostModelAssembler;

@RestController
@RequestMapping(path = "${apiPrefix}")
@Tag(name = "post", description = "posts API")
public class PostController {

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	@Autowired
	PostModelAssembler postAssembler;

	@Operation(summary = "Find All posts by a user Id", tags = { "post" })
	@GetMapping("/users/{id}/posts")
	// surppress hateos details from showing up in swagger ui
	@ApiResponses(value = {
			@ApiResponse( content = @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)))) })
	public CollectionModel<EntityModel<Post>> getAllPost(@Valid @PathVariable UUID id) {
		Optional<User> user = userService.getUserById(id);
		if (!user.isPresent())
			throw new RessourceNotFoundException("User", "userId", id);
		List<Post> posts = postService.getAllPostsByUserId(user.get());
		List<EntityModel<Post>> postModelList = new ArrayList<>();
		for (Post post : posts) {
			postModelList.add(postAssembler.toModel(post));
		}
		return CollectionModel.of(postModelList, linkTo(methodOn(PostController.class).getAllPost(id)).withSelfRel());
	}

	@Operation(summary = "Find a post by its id and  its user Id", tags = { "post" })
	@GetMapping("/users/{userId}/posts/{postId}")
	@ApiResponses(value = {
			@ApiResponse( content = @Content(schema = @Schema(implementation = Post.class))) })
	public EntityModel<Post> getOnePost(@Valid @PathVariable UUID userId, @Valid @PathVariable int postId) {
		Optional<User> user = userService.getUserById(userId);
		if (!user.isPresent())
			throw new RessourceNotFoundException("User", "userId", userId);
		Optional<Post> post = postService.findByIdAndUser(postId, user.get());
		if (!post.isPresent())
			throw new RessourceNotFoundException("Post", "postId", postId);
		return postAssembler.toModel(post.get());
	}

	@Operation(summary = "Create a new post for a user", tags = { "post" })
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<?> addPost(@Valid @PathVariable UUID id, @Valid @RequestBody Post newpost) {
		Optional<User> user = userService.getUserById(id);
		if (!user.isPresent())
			throw new RessourceNotFoundException("User", "userId", id);
		newpost.setUser(user.get());
		Post savedPost = postService.addPost(newpost);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Postid}")
				.buildAndExpand(savedPost.getId()).toUri();
		return ResponseEntity.created(location).body(postAssembler.toModel(savedPost));
	}

	@Operation(summary = "Delete post for a user", tags = { "post" })
	@DeleteMapping("/users/{userId}/posts/{postId}")
	public ResponseEntity<?> deleteOnePost(@Valid @PathVariable UUID userId, @Valid @PathVariable int postId) {
		Optional<User> user = userService.getUserById(userId);
		if (!user.isPresent())
			throw new RessourceNotFoundException("User", "userId", userId);
		Optional<Post> post = postService.findByIdAndUser(postId, user.get());
		if (!post.isPresent())
			throw new RessourceNotFoundException("Post", "postId", postId);
		postService.removePost(postId);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Update a post for a user", tags = { "post" })
	@PatchMapping("/users/{userId}/posts/{postId}")
	public ResponseEntity<?> editOnePost(@Valid @PathVariable("userId") UUID userId,
			@Valid @PathVariable("postId") int postId, @RequestBody Post editedPost) {
		Optional<User> user = userService.getUserById(userId);
		if (!user.isPresent())
			throw new RessourceNotFoundException("User", "userId", userId);
		Optional<Post> post = postService.findByIdAndUser(postId, user.get());
		if (!post.isPresent())
			throw new RessourceNotFoundException("Post", "postId", postId);
		editedPost.setUser(user.get());
		Post postSaved = postService.editPostFully(editedPost, postId);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{postId}")
				.buildAndExpand(user.get().getId()).toUri();
		return ResponseEntity.ok().location(location).body(postAssembler.toModel(postSaved));
	}

}
