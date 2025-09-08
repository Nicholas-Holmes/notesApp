package com.nicholas.app.Controllers; 
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.nicholas.app.Security.CustomUserDetails;
import com.nicholas.app.Services.NotesService;
import com.nicholas.app.dtoObjects.NotesDto;
import com.nicholas.app.dtoObjects.TextResponseDto;
import com.nicholas.app.dtoObjects.UpdateTextDto;
import com.nicholas.app.dtoObjects.UpdateTextResponseDto;

import java.util.List; 
@RestController
@RequestMapping("/api/notes")
public class NotesController {
    @Autowired
    NotesService notesService;

    @GetMapping("/getNotes")
    public ResponseEntity<?> getNotes(Authentication authentication){
        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        NotesDto notes = notesService.getNotes(userDetails.getId());
        return ResponseEntity.ok(notes);
    }

    @PostMapping("/createNote")
    public ResponseEntity<?> createNote(@RequestBody NotesDto note,Authentication authentication){
        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        notesService.createNote(userDetails.getId(),note.getTitle(),note.getText());
        TextResponseDto textResponse = new TextResponseDto("note saved");
        return ResponseEntity.ok(textResponse);
    }

    @PutMapping("/updateNote/{noteId}")
    public ResponseEntity<?> updateNote(@PathVariable long noteId, @RequestBody UpdateTextDto text, Authentication authentication){
        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        notesService.updateNote(text.getNewText(),noteId,userDetails.getId());
        UpdateTextResponseDto response = new UpdateTextResponseDto("Note updated");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteNote/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id,Authentication authentication){
        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        notesService.deleteNote(id,userDetails.getId());
        return ResponseEntity.noContent().build();
    }

}