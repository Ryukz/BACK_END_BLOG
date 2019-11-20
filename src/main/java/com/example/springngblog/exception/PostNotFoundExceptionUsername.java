package com.example.springngblog.exception;

public class PostNotFoundExceptionUsername extends RuntimeException {
    public PostNotFoundExceptionUsername(String message) {
        super(message);
    }
}
