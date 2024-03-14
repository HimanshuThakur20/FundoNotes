package com.bridgelabz.fundoo.notes.controller;

import com.bridgelabz.fundoo.notes.dto.NotesDTO;
import com.bridgelabz.fundoo.notes.service.NotesService;
import com.bridgelabz.fundoo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/{token}")
    public Mono<ResponseEntity<Response>> saveNote(@RequestBody NotesDTO note, @PathVariable String token) {
        return notesService.saveNote(note,token);
    }

    @GetMapping("/active/{token}")
    public Mono<ResponseEntity<Response>> getActiveNotesByUserId(@PathVariable String token) {
        return notesService.findByUserIdAndArchivedFalse(token);
    }

    @GetMapping("/archived/{token}")
    public Mono<ResponseEntity<Response>> getArchivedNotesByUserId(@PathVariable String token) {
        return notesService.findByUserIdAndArchivedTrue(token);
    }

    @GetMapping("/pin/{token}")
    Mono<ResponseEntity<Response>> pinNoteById(@PathVariable String token) {
        return notesService.pinNoteById(token);
    }

    @GetMapping("/unpin/{token}")
    public Mono<ResponseEntity<Response>> unpinNoteById(@PathVariable String token) {
        return notesService.unpinNoteById(token);
    }

    @GetMapping("/{noteId}")
    public Mono<ResponseEntity<Response>> getNoteById(@PathVariable Long noteId) {
         return notesService.getNoteById(noteId);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Response>> updateNoteById(@PathVariable Long id, @RequestBody NotesDTO note) {
        return notesService.updateNoteById(id, note);
    }

    @DeleteMapping("/{noteId}/{token}")
    public Mono<ResponseEntity<Response>> deleteNoteById(@PathVariable Long noteId, @PathVariable String token) {
        return notesService.deleteNoteById(noteId,token);
    }
}