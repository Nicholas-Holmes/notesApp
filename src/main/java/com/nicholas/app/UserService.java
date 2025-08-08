package com.nicholas.app; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nicholas.app.exception.DatabaseSaveException;
import com.nicholas.app.exception.InvalidPasswordException;
import com.nicholas.app.exception.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

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
    public User login(String username, String rawPassword){ 
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        User user = userOpt.get();
        boolean authenticated = passwordEncoder.matches(rawPassword,user.getPassword());
        if (!authenticated){
            throw new InvalidPasswordException("Incorrect password");
        }
        return user;
    }
}