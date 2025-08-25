package com.nicholas.app;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig { 
    @Bean 
    public JwtAuthenticationFilter jwtAuthenticationFilter(UserService userService,JwtUtil jwtUtil){
        return new JwtAuthenticationFilter(userService, jwtUtil);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/users/login","/api/users/register","/api/users/refreshTokens").permitAll().anyRequest().authenticated()
        ).addFilterBefore(jwtAuthenticationFilter(null,null), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    
    }

}