package com.nicholas.app.exception; 
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException e){
        return ResponseEntity.status(401).body(Map.of("error",e.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPassword(InvalidPasswordException e){
        return ResponseEntity.status(401).body(Map.of("error",e.getMessage()));
    }
    
    @ExceptionHandler(DatabaseSaveException.class)
    public ResponseEntity<?> handleSaveException(DatabaseSaveException e){
        return ResponseEntity.status(401).body(Map.of("error",e.getMessage()));
    }
    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<?> handleNoteNotFound(NoteNotFoundException e){
        return ResponseEntity.status(401).body(Map.of("error",e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String,String>> handleAccessDenied(AccessDeniedException e){
        return ResponseEntity.status(401).body(Map.of("error",e.getMessage()));
    }
    
}