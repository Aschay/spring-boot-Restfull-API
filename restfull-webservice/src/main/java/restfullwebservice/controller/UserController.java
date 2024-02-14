package restfullwebservice.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import restfullwebservice.exception.BadRequestException;
import restfullwebservice.exception.RessourceNotFoundException;
import restfullwebservice.model.User;
import restfullwebservice.service.UserService;
import restfullwebservice.utils.UserModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	UserModelAssembler assembler;

	@GetMapping("/users")
	public CollectionModel<EntityModel<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		List<EntityModel<User>> userModelList = new ArrayList<>();
		for (User user : users) {
			userModelList.add(assembler.toModel(user));
		}
		return CollectionModel.of(userModelList, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
	}

	@GetMapping("/users/{id}")
	public EntityModel<User> getOneUser(@Valid @PathVariable UUID id) {
		Optional<User> user = userService.getUserById(id);
		if (!user.isPresent())
			throw new RessourceNotFoundException("User", "userId", id);
		User u = user.get();
		return assembler.toModel(u);
	}

	@PostMapping("/users")
	public ResponseEntity<?> addNewUser(@Valid @RequestBody User newUser) {
		User savedUser = userService.addUser(newUser);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Userid}")
				.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).body(assembler.toModel(savedUser));
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteOneUser(@Valid @PathVariable UUID id) {
		Optional<User> user = userService.getUserById(id);
		if (!user.isPresent())
			throw new RessourceNotFoundException("User", "userId", id);
		userService.removeUser(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/users")
	public ResponseEntity<?> editUser(@Valid @RequestBody User editUser) {
		Optional<User> user = userService.getUserById(editUser.getId());
		if (!user.isPresent())
			throw new RessourceNotFoundException("User", "userId", editUser.getId());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Userid}")
				                                 .buildAndExpand(user.get().getId()).toUri();
		return ResponseEntity.ok().location(location).build();
		
	}

}
