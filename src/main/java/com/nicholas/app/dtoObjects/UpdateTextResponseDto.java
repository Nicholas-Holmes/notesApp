package com.nicholas.app.dtoObjects;

public class UpdateTextResponseDto extends ErrorHolder{
    private String responseText; 

    public UpdateTextResponseDto(String responseText){
        this.responseText = responseText;
    }

    public String getResponseText(){
        return this.responseText;
    }

}
