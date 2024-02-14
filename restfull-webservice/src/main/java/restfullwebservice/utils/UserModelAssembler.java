package restfullwebservice.utils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import restfullwebservice.controller.UserController;
import restfullwebservice.model.User;
@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

	@Override
	public EntityModel<User> toModel(User user) {
		Link linkUser = linkTo(methodOn(UserController.class).getOneUser(user.getId())).withSelfRel();
		Link linkAllUsers = linkTo(methodOn(UserController.class).getAllUsers()).withRel("users");
		return EntityModel.of(user, linkUser, linkAllUsers);
	}

	
}
