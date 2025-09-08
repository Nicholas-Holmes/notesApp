package com.nicholas.app.dtoObjects;

public class ErrorHolder{
    private String errorMessage; 

    public ErrorHolder(){

    }

    public ErrorHolder(String errorMessage){
        this.errorMessage = errorMessage; 
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }
}