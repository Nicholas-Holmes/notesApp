package com.nicholas.app.dtoObjects;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotesResponseDto extends ErrorHolder{
    private long id;

    private String text;

    private String title;

    private List<NotesResponseDto> notesList;
    
    @Override
    public String toString(){
        return this.title;
    }
}
