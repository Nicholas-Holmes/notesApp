package com.nicholas.app; 
import java.util.Map;
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
        User user = userService.login(username,password);
        return ResponseEntity.ok(new LoginResponseDto(user.getId(),user.getUsername()));
    }
}
