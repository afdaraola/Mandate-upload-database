package com.demotek.mandate.exception;

public class CustomAuthenticationException extends  RuntimeException{

    public CustomAuthenticationException(String message){
        super(message);
    }
}
