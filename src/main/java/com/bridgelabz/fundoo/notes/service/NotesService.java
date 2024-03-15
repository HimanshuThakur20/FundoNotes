package com.bridgelabz.fundoo.notes.service;

import com.bridgelabz.fundoo.notes.dto.NotesDTO;
import com.bridgelabz.fundoo.notes.modal.Notes;
import com.bridgelabz.fundoo.response.Response;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotesService {
    Mono<ResponseEntity<Response>> saveNote(NotesDTO noteDto, String token);
    Mono<ResponseEntity<Response>> findByUserIdAndArchivedFalse(String token);
    Mono<ResponseEntity<Response>> findByUserIdAndArchivedTrue(String token);
    Mono<ResponseEntity<Response>> pinNoteById(String token);
    Mono<ResponseEntity<Response>> unpinNoteById(String token);
    Mono<ResponseEntity<Response>>getNoteById(Long noteId);
    Mono<ResponseEntity<Response>> updateNoteById(Long id, NotesDTO note);
    Mono<ResponseEntity<Response>> deleteNoteById(String Token);
}
