package com.example.springngblog.controller;

import com.example.springngblog.dto.CommentDto;
import com.example.springngblog.dto.PostDto;
import com.example.springngblog.security.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping("/update/{id}/{title}/{content}/{security}")
    public boolean updatePost(@PathVariable  Long id , @PathVariable String title, @PathVariable String content, @PathVariable Boolean security )
    {
        postService.updatePost(id,title,content,security);
        return true;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get/sort/{username}")
    public ResponseEntity<List<PostDto>> getPostByUsername(@PathVariable @RequestBody String username) {
        return new ResponseEntity<>(postService.getPostByUsername(username), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public  Boolean deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return true;
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }

   @PostMapping("/comment/")
    public Boolean addComment(@RequestBody CommentDto commentDto) {
        postService.addComment(commentDto);
        return true;
   }
   @GetMapping("/allcomment/{title}")
    public List<CommentDto> getAllCommentByUsername(@PathVariable String title) {
        return postService.getAllCommentByUsername(title);
   }



}
