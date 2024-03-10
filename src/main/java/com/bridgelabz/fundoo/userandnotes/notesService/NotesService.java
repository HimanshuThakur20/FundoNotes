package com.bridgelabz.fundoo.userandnotes.notesService;

import com.bridgelabz.fundoo.userandnotes.modal.Notes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotesService {
    Mono<Notes> saveNote(Notes note);
//    Flux<Notes> getAllActiveNotesByUserId(Long userId);
    Flux<Notes> findByUserIdAndArchivedFalse(Long userId);
    Flux<Notes> findByUserIdAndArchivedTrue(Long userId);

    Mono<Notes> getNoteById(Long id);
    Mono<Notes> updateNoteById(Long id, Notes note);
    Mono<Boolean> deleteNoteById(Long id);
}
