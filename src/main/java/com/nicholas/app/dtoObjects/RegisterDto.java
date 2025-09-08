package com.nicholas.app.dtoObjects;

public class RegisterDto extends ErrorHolder{

    private String username;
    private String password; 

    public RegisterDto(String username, String password){
        this.username = username; 
        this.password = password; 
    }

}