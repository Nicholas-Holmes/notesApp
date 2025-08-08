package com.nicholas.app; 
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotesDto{
    private long id; 

    private String text; 
    
    private String title;
}