package restfullwebservice.utils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import restfullwebservice.controller.PostController;
import restfullwebservice.model.Post;

@Component
public class PostModelAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>> {

	@Override
	public EntityModel<Post> toModel(Post post) {
		Link linkPost = linkTo(methodOn(PostController.class).getOnePost(post.getUser().getId(), post.getId())).withSelfRel();
		Link linkAllPosts = linkTo(methodOn(PostController.class).getAllPost(post.getUser().getId())).withRel("Posts");
		return EntityModel.of(post, linkPost, linkAllPosts);
	}

}
