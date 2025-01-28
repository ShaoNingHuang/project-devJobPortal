package com.huan1645.TWDevJob.exception;

public class UserExistedException extends RuntimeException{
    public UserExistedException(String message) {
        super(message);
    }
}
