package com.nicholas.app.Repositories; 
import org.springframework.data.jpa.repository.JpaRepository;

import com.nicholas.app.Entities.Notes;

import java.util.Optional;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Long>{
    List<Notes> findByUser_Id(Long id);
}