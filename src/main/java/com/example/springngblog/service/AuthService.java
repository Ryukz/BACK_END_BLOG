package com.example.springngblog.service;

import com.example.springngblog.dto.LoginRequest;
import com.example.springngblog.dto.RegisterRequest;
import com.example.springngblog.model.User;
import com.example.springngblog.repository.PostRepository;
import com.example.springngblog.repository.UserRepository;
import com.example.springngblog.security.JwtProvider;
import com.example.springngblog.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PostRepository postRepository;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));

        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
    public UserDto getAllUser(String username) {
        User user = userRepository.findByUserName(username);
        UserDto user1 = new UserDto();
        user1.setId(user.getId());
        user1.setEmail(user.getEmail());
        String password = user.getPassword();
        user1.setPassword(password);
        user1.setUsername(user.getUserName());
        return user1;
    }
    public User updateUser(String username,String email,String password, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("For id " + id));
        String notupdatedusername = user.getUserName();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
      userRepository.save(user);
//        Post post = new Post();
//        post.setId(id);
//        post.setUsername(username);
//        postRepository.save(post);

//        PostService postService = new PostService();
//     updatePostByUsername(username,notupdatedusername);
        return user;
    }
//    public void updatePostByUsername(String updatedusernmae, String username )
//    {
//        Post post = postRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("For username " + username));
//        System.out.print(post.getUsername());
//        System.out.println(post.getUsername());
//        post.setUsername(updatedusernmae);
//        System.out.println(post.getUsername());
//        postRepository.save(post);
//
//    }

}
