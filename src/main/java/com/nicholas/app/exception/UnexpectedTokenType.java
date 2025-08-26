package com.nicholas.app.exception;

public class UnexpectedTokenType extends RuntimeException{
    public UnexpectedTokenType(String message){
        super(message);
    }
}