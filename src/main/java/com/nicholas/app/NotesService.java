package com.nicholas.app; 
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicholas.app.exception.NoteNotFoundException;



import java.util.List; 
@Service 
public class NotesService {
    @Autowired 
    NotesRepository notesRepository; 

    @Transactional(readOnly = true)
    public NotesDto getNotes(Long id){
        List<Notes> notes = notesRepository.findByUser_Id(id);
        List<NotesDto> responseList = notes.stream().map(note -> 
            new NotesDto(note.getId(),note.getNoteText(),note.getTitle(),null)).collect(Collectors.toList());
        return new NotesDto(0,"","",responseList); 
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
    public void deleteNote(long NoteId, long userId){
        Optional<Notes> optNote = notesRepository.findById(NoteId);
        if (!optNote.isEmpty()){
            Notes note = optNote.get();
            if (note.getUser().getId() == userId){
                notesRepository.delete(note);
            } else {
                throw new AccessDeniedException("Note does not belong to you");
            }
        } else {
            throw new NoteNotFoundException("Note not found");
        }
    }
}