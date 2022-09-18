package com.example.workshopmongo.dto;

import com.example.workshopmongo.domain.User;

import java.io.Serializable;

public class AuthorDto implements Serializable {

  public static final long serialVersionUID = 1L;

  private String id;
  private String name;

  public AuthorDto() {

  }

  public AuthorDto(User user) {
    id = user.getId();
    name = user.getName();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
