package com.nicholas.app;

import com.nicholas.app.frontEnd.ErrorHolder;

public class TokenDto extends ErrorHolder{
    private String accessToken;
    private String refreshToken;

    public TokenDto(String accessToken,String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken(){
        return this.accessToken;
    }

    public String getRefreshToken(){
        return this.refreshToken;
    }
}