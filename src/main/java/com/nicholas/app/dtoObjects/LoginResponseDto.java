package com.nicholas.app.dtoObjects; 
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto extends ErrorHolder{
    private long id;

    private String username; 

    private String accessToken; 

    private String refreshToken;

    @Synchronized
    public void setAccessToken(String token){
        this.accessToken = token;
    }
    
    @Synchronized
    public String getAccessToken(){
        return this.accessToken;
    }
    
    @Synchronized
    public void setRefreshToken(String token){
        this.refreshToken = token;
    }

    @Synchronized
    public String getRefreshToken(){
        return this.refreshToken;
    }


}