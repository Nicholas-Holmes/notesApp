package com.nicholas.app.dtoObjects;

public class RegisterResponseDto extends ErrorHolder{
    private String successfulResponse;

    public RegisterResponseDto(String successfulResponse){
        this.successfulResponse = successfulResponse;
    }

    public RegisterResponseDto(){

    }

    public String getSuccessfulResponse(){
        return this.successfulResponse;
    }
}