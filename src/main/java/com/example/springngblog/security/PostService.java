package com.example.springngblog.security;

import com.example.springngblog.exception.PostNotFoundException;
import com.example.springngblog.repository.PostRepository;
import com.example.springngblog.dto.CommentDto;
import com.example.springngblog.dto.PostDto;
import com.example.springngblog.model.Comment;
import com.example.springngblog.model.Post;
//import com.programming.techie.springngblog.model.User1;
import com.example.springngblog.repository.CommentRepository;
import com.example.springngblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }
    @Transactional
    public List<PostDto> getPostByUsername(String username) {
        List<Post> posts = postRepository.findByUsername(username);
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }
    @Transactional
    public void createPost(PostDto postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }
    @Transactional
    public void addComment(CommentDto commentDto)
    {
        Comment comment = move(commentDto);
        commentRepository.save(comment);


    }
    @Transactional
    public List<CommentDto> getAllCommentByUsername(String title) {
        List<Comment> comments = commentRepository.findByTitle(title);
        return comments.stream().map(this::move2).collect(toList());


    }

    private Comment move(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setUsername(commentDto.getUsername());
        comment.setPass(commentDto.getPass());
        String a= commentDto.getTitle();
        a=a.replaceAll(" ","");
        comment.setTitle(a);
        return comment;

    }

    @Transactional
    public void updatePost(Long id, String title, String content, Boolean security) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        System.out.println(post.getTitle());
        post.setContent(content);
        post.setTitle(title);
        post.setSecurity(security);
        postRepository.save(post);

    }

    @Transactional
    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }
    @Transactional
    public void deletePost(Long id) {

        postRepository.deleteById(id);
    }






    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        postDto.setDate(post.getCreatedOn());
        postDto.setSecurity(post.getSecurity());
        return postDto;
    }

    private CommentDto move2(Comment comment)
    {
        CommentDto commentDto = new CommentDto();
        commentDto.setUsername(comment.getUsername());
        commentDto.setPass(comment.getPass());
        commentDto.setCid(comment.getCid());
        commentDto.setTitle(comment.getTitle());
        return commentDto;
    }

    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(System.currentTimeMillis());
//        post.setDate(System.currentTimeMillis());
        post.setUsername(loggedInUser.getUsername());
        post.setUpdatedOn(Instant.now());
        post.setSecurity(postDto.getSecurity());
        return post;
    }




}
