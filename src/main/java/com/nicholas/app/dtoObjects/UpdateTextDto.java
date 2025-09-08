package com.nicholas.app.dtoObjects;
public class UpdateTextDto extends ErrorHolder{
    private String newText; 
    
    public UpdateTextDto(String newText){
        this.newText = newText; 
    }

    public String getNewText(){
        return this.newText;
    }

}
