package com.nicholas.app; 
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicholas.app.exception.NoteNotFoundException;



import java.util.List; 
@Service 
public class NotesService {
    @Autowired 
    NotesRepository notesRepository; 

    @Transactional(readOnly = true)
    public List<Notes> getNotes(Long id){
        List<Notes> notes = notesRepository.findByUser_Id(id);
        return notes; 
    }
    @Transactional
    public void createNote(long id,String title, String text ){
        User user = new User();
        Notes note = new Notes();
        user.setId(id);
        note.setUser(user);
        note.setTitle(title);
        note.setNoteText(text);
        notesRepository.save(note); 
    }
    @Transactional
    public void updateNote(String text,long id){
        Optional<Notes> optNote = notesRepository.findById(id);
        if (!optNote.isEmpty()){
            Notes note = optNote.get(); 
            note.setNoteText(text);
        } else {
            throw new NoteNotFoundException("Note not found");
        }
    }
    @Transactional
    public void deleteNote(long id){
        Optional<Notes> optNote = notesRepository.findById(id);
        if (!optNote.isEmpty()){
            Notes note = optNote.get();
            notesRepository.delete(note);
        } else {
            throw new NoteNotFoundException("Note not found");
        }
    }
}