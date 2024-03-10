package com.bridgelabz.fundoo.userandnotes.controller;

import com.bridgelabz.fundoo.userandnotes.modal.Notes;
import com.bridgelabz.fundoo.userandnotes.notesService.NotesService;
import com.bridgelabz.fundoo.userandnotes.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping
    public Mono<ResponseEntity<Response>> saveNote(@RequestBody Notes note) {
        return notesService.saveNote(note)
                .map(savedNote -> new ResponseEntity<>(new Response(200, "Note saved successfully"), HttpStatus.OK))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @GetMapping("/active/{userId}")
    public Mono<ResponseEntity<Response>> getActiveNotesByUserId(@PathVariable Long userId) {
        return notesService.findByUserIdAndArchivedFalse(userId)
                .collectList()
                .map(noteList -> !noteList.isEmpty() ?
                        new ResponseEntity<>(new Response(200, "Active notes retrieved successfully", noteList), HttpStatus.OK) :
                        new ResponseEntity<>(new Response(404, "No active notes found"), HttpStatus.NOT_FOUND))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @GetMapping("/archived/{userId}")
    public Mono<ResponseEntity<Response>> getArchivedNotesByUserId(@PathVariable Long userId) {
        return notesService.findByUserIdAndArchivedTrue(userId)
                .collectList()
                .map(noteList -> !noteList.isEmpty() ?
                        new ResponseEntity<>(new Response(200, "Archived notes retrieved successfully", noteList), HttpStatus.OK) :
                        new ResponseEntity<>(new Response(404, "No archived notes found"), HttpStatus.NOT_FOUND))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Response>> getNoteById(@PathVariable Long id) {
        return notesService.getNoteById(id)
                .map(note -> new ResponseEntity<>(new Response(200, "Note retrieved successfully", note), HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(new Response(404, "Note not found"), HttpStatus.NOT_FOUND)))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Response>> updateNoteById(@PathVariable Long id, @RequestBody Notes note) {
        return notesService.updateNoteById(id, note)
                .map(updatedNote -> new ResponseEntity<>(new Response(200, "Note updated successfully",updatedNote), HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(new Response(404, "Note not found"), HttpStatus.NOT_FOUND)))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response>> deleteNoteById(@PathVariable Long id) {
        return notesService.deleteNoteById(id)
                .map(deletionResult -> {
                    if (deletionResult) {
                        return new ResponseEntity<>(new Response(204, "Note deleted successfully"), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(new Response(404, "Note not found"), HttpStatus.NOT_FOUND);
                    }
                })
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
