package com.nicholas.app;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") 
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; 

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
