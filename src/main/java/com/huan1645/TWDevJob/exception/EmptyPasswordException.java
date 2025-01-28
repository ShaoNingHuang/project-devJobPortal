package com.huan1645.TWDevJob.exception;

public class EmptyPasswordException extends RuntimeException{
    public EmptyPasswordException(String message){
        super(message);
    }
}
