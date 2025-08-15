package com.nicholas.app; 
import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotesDto{
    private long id; 

    private String text; 

    private String title;

    private List<NotesDto> notesList;
}