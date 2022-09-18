package com.example.workshopmongo.resource;

import com.example.workshopmongo.domain.Post;
import com.example.workshopmongo.domain.User;
import com.example.workshopmongo.dto.UserDto;
import com.example.workshopmongo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<List<UserDto>> findAll() {
    List<User> list = userService.findAll();
    List<UserDto> listDto = list.stream().map(x -> new UserDto(x)).collect(Collectors.toList());
    return ResponseEntity.ok().body(listDto);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<UserDto> findById(@PathVariable String id) {
    User user = userService.findById(id);
    return ResponseEntity.ok().body(new UserDto(user));
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Void> insert(@RequestBody UserDto userDto) {
    User user = userService.fromDto(userDto);
    user = userService.insert(user);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable String id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Void> update(@PathVariable String id, @RequestBody UserDto userDto) {
    User user = userService.fromDto(userDto);
    user.setId(id);
    user = userService.update(user);
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
  public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
    User user = userService.findById(id);
    return ResponseEntity.ok().body(user.getPosts());
  }
}
