package com.bridgelabz.fundoo.userandnotes.controller;

import com.bridgelabz.fundoo.userandnotes.modal.Notes;
import com.bridgelabz.fundoo.userandnotes.notesService.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;


    @PostMapping
    public Mono<Notes> saveNote(@RequestBody Notes note) {
        return notesService.saveNote(note);
    }

    @GetMapping("/active/{userId}")
    public Flux<Notes> getActiveNotesByUserId(@PathVariable Long userId) {
        return notesService.findByUserIdAndArchivedFalse(userId);
    }

    @GetMapping("/archived/{userId}")
    public Flux<Notes> getArchivedNotesByUserId(@PathVariable Long userId) {
        return notesService.findByUserIdAndArchivedTrue(userId);
    }

    @GetMapping("/{id}")
    public Mono<Notes> getNoteById(@PathVariable Long id) {
        return notesService.getNoteById(id);
    }

    @PutMapping("/{id}")
    public Mono<Notes> updateNoteById(@PathVariable Long id, @RequestBody Notes note) {
        return notesService.updateNoteById(id, note);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteNoteById(@PathVariable Long id) {
        return notesService.deleteNoteById(id);
    }
}
