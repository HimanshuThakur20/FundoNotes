package com.bridgelabz.fundoo.userandnotes.repository;

import com.bridgelabz.fundoo.userandnotes.modal.Notes;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface NoteRepository extends R2dbcRepository<Notes, Long> {

//    @Query("SELECT * FROM notes WHERE user_id = :userId AND archived = false")
    Flux<Notes> findByUserIdAndArchivedFalse(Long userId);

    Flux<Notes> findByUserIdAndArchivedTrue(Long userId);
}
