package com.example.workshopmongo.resource;

import com.example.workshopmongo.domain.Post;
import com.example.workshopmongo.resource.util.URL;
import com.example.workshopmongo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

  @Autowired
  private PostService postService;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Post> findById(@PathVariable String id) {
    Post post = postService.findById(id);
    return ResponseEntity.ok().body(post);
  }

  @RequestMapping(value = "/titlesearch", method = RequestMethod.GET)
  public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
    text = URL.decodeParam(text);
    List<Post> list = postService.findByTitle(text);
    return ResponseEntity.ok().body(list);
  }

  @RequestMapping(value = "/fullsearch", method = RequestMethod.GET)
  public ResponseEntity<List<Post>> fullSearch(
          @RequestParam(value = "text", defaultValue = "") String text,
          @RequestParam(value = "minDate", defaultValue = "") String minDate,
          @RequestParam(value = "maxDate", defaultValue = "") String maxDate) {
    text = URL.decodeParam(text);
    Date min = URL.convertDate(minDate, new Date(0L));
    Date max = URL.convertDate(maxDate, new Date(0L));
    List<Post> list = postService.fullSearch(text, min, max);
    return ResponseEntity.ok().body(list);
  }
}
