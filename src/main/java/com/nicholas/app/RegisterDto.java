package com.nicholas.app;
import com.nicholas.app.frontEnd.ErrorHolder;

public class RegisterDto extends ErrorHolder{

    private String username;
    private String password; 

    public RegisterDto(String username, String password){
        this.username = username; 
        this.password = password; 
    }

}