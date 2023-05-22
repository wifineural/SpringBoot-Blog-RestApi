package org.saini.blogrestapi.controller;

import jakarta.validation.Valid;
import org.saini.blogrestapi.payload.PostDto;
import org.saini.blogrestapi.payload.PostResponse;
import org.saini.blogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService=postService;
    }
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
      return new ResponseEntity<>(  postService.createPost(postDto), HttpStatus.CREATED);

    }

    @GetMapping
    public PostResponse findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0")int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2") int pageSize,
            @RequestParam(value = "sortBy" , defaultValue = "id") String sortBy,
            @RequestParam( value = "sortDir", defaultValue = "sortDir") String sortDir
    ){
        return postService.findAll(pageNumber,pageSize,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<PostDto> findPostById(@PathVariable(name="id") long id){
        return new  ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public  ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto,@PathVariable(name="id") long id){
        return new  ResponseEntity<>(postService.updatePostById(postDto,id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deletePostById(@PathVariable(name="id") long id){

        return new  ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }

}
