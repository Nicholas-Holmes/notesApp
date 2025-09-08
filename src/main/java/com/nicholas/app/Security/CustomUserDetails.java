package com.nicholas.app.Security;

import java.util.List;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nicholas.app.Entities.User;

public class CustomUserDetails implements UserDetails {
   private final User user;
   
   public CustomUserDetails(User user){
    this.user = user; 
   }

   public Long getId() {
    return user.getId();
   }

   @Override 
   public String getUsername() {
    return user.getUsername();
   }

   @Override 
   public String getPassword() {
    return user.getPassword();
   }

   @Override 
   public Collection<? extends GrantedAuthority> getAuthorities(){
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
   }

   @Override 
   public boolean isAccountNonExpired(){
    return true; 
   }

   @Override 
   public boolean isAccountNonLocked(){
    return true;
   }

   @Override public boolean isCredentialsNonExpired(){
    return true;
   }

   @Override 
   public boolean isEnabled(){
    return true;
   }

   public User getUser(){
    return user;
   }
}