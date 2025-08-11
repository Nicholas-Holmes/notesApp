package com.nicholas.app; 
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List; 
@RestController
@RequestMapping("/api/notes")
public class NotesController {
    @Autowired
    NotesService notesService;

    @GetMapping("/getNotes")
    public ResponseEntity<?> getNotes(Authentication authentication){

        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<Notes> notes = notesService.getNotes(userDetails.getId());
        List<NotesDto> responseList = notes.stream().map(note -> 
            new NotesDto(note.getId(),note.getNoteText(),note.getTitle())).collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    @PostMapping("/createNote")
    public ResponseEntity<?> createNote(@RequestBody NotesDto note,Authentication authentication){
        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        notesService.createNote(userDetails.getId(),note.getTitle(),note.getText());
        return ResponseEntity.ok("note Saved");
    }
    @PutMapping("/updateNote")
    public ResponseEntity<?> updateNote(@RequestBody NotesDto note){
        notesService.updateNote(note.getText(),note.getId());
        return ResponseEntity.ok("Note updated");
    }
    @DeleteMapping("/deleteNote/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id,Authentication authentication){
        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        notesService.deleteNote(id,userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}