package com.nicholas.app;

import com.nicholas.app.frontEnd.ErrorHolder;

public class TextResponseDto extends ErrorHolder{
    private String textResponse; 

    public TextResponseDto(String textResponse){
        this.textResponse = textResponse;
    }

    public String getTextResponse(){
        return this.textResponse;
    }

    public void setTextResponse(String textResponse){
        this.textResponse = textResponse; 
    }
}