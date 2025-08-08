package com.nicholas.app; 
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Long>{
    List<Notes> findByUser_Id(Long id);
}