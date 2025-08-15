package com.nicholas.app; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nicholas.app.exception.DatabaseSaveException;
import com.nicholas.app.exception.InvalidPasswordException;
import com.nicholas.app.exception.UserNotFoundException;
import com.nicholas.app.frontEnd.LoginResponseDto;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    @Autowired 
    private JwtUtil jwtUtil;

    @Transactional
    public void registerUser(String username, String rawPassword){
        if (userRepository.existsByUsername(username)){
            throw new DatabaseSaveException("Username already exists");
        }
        String hashedPassword = passwordEncoder.encode(rawPassword);
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public LoginResponseDto login(String username, String rawPassword){ 
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        User user = userOpt.get();
        boolean authenticated = passwordEncoder.matches(rawPassword,user.getPassword());
        if (!authenticated){
            throw new InvalidPasswordException("Incorrect password");
        }
        var cud = (CustomUserDetails) loadUserByUsername(user.getUsername());
        String token = jwtUtil.generateToken(cud);
        return new LoginResponseDto(user.getId(),user.getUsername(),token);
    }

    @Override 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username).orElseThrow(() -> 
        new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}