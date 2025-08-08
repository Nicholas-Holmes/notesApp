package com.nicholas.app.exception;

public class DatabaseSaveException extends RuntimeException{
    public DatabaseSaveException(String message){
        super(message);
    }
}