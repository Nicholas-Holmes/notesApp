package com.nicholas.app;

import com.nicholas.app.frontEnd.ErrorHolder;

public class TextResponseDto extends ErrorHolder{
    private String textResponse; 

    public TextResponseDto(String textResponse){
        this.textResponse = textResponse;
    }

    public String getText(){
        return this.textResponse;
    }
}