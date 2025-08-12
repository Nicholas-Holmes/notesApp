package com.nicholas.app.frontEnd;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotesResponseDto extends ErrorHolder{
    private long id;

    private String text;

    private String title;
    @Override
    public String toString(){
        return this.title;
    }
}
