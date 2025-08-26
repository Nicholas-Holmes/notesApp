package com.nicholas.app; 
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nicholas.app.frontEnd.LoginResponseDto;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        userService.registerUser(userDto.getUsername(), userDto.getPassword());
        return ResponseEntity.ok("User registered successfully."); 
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto){ 
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        LoginResponseDto loginResponse= userService.login(username,password);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refreshTokens")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String header){
        if (header == null || !header.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body("Missing or invalid authorization header");
        }
        String token = header.substring(7);
        Map<String,String> newTokens = userService.refreshTokens(token);
        TokenDto tokens = new TokenDto(newTokens.get("accessToken"),newTokens.get("refreshToken"));
        return ResponseEntity.ok(tokens);
    }
}
