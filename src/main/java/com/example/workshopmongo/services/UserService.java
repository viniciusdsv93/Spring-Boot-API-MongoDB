package com.example.workshopmongo.services;

import com.example.workshopmongo.domain.User;
import com.example.workshopmongo.dto.UserDto;
import com.example.workshopmongo.repositories.UserRepository;
import com.example.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User findById(String id) {
    Optional<User> user = userRepository.findById(id);
    if (!user.isPresent()) {
      throw new ObjectNotFoundException("Not found object");
    }
    else {
      return user.get();
    }
  }

  public User insert(User user) {
    return userRepository.insert(user);
  }

  public void delete(String id) {
    findById(id);
    userRepository.deleteById(id);
  }

  public User update(User user) {
    User newUser = userRepository.findById(user.getId()).orElse(null);
    updateData(newUser, user);
    return userRepository.save(newUser);
  }

  private void updateData(User newUser, User user) {
    newUser.setName(user.getName());
    newUser.setEmail(user.getEmail());
  }

  public User fromDto(UserDto userDto) {
    return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
  }
}
