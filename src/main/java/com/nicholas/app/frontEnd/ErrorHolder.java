package com.nicholas.app.frontEnd; 
import java.util.Map;

public class ErrorHolder{
    private Map<String,String> errorMap;

    public ErrorHolder(){

    }

    public ErrorHolder(Map<String,String> errorMap){
        this.errorMap = errorMap; 
    }

    public Map<String,String> getErrorMap(){
        return this.errorMap;
    }
}