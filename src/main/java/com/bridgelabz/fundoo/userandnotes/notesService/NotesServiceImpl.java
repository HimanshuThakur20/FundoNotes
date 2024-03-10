package com.bridgelabz.fundoo.userandnotes.notesService;

import com.bridgelabz.fundoo.userandnotes.modal.Notes;
import com.bridgelabz.fundoo.userandnotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NotesServiceImpl implements NotesService{

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Mono<Notes> saveNote(Notes note) {
        return this.noteRepository.save(note);
    }

    @Override
    public Flux<Notes> findByUserIdAndArchivedFalse(Long userId) {
        return noteRepository.findByUserIdAndArchivedFalse(userId);
    }

    @Override
    public Flux<Notes> findByUserIdAndArchivedTrue(Long userId) {
        return noteRepository.findByUserIdAndArchivedTrue(userId);
    }


    @Override
    public Mono<Notes> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    public Mono<Notes> updateNoteById(Long id, Notes note) {
        return noteRepository.findById(id)
                .flatMap(existingNote -> {
                    existingNote.setTitle(note.getTitle());
                    existingNote.setContent(note.getContent());
                    existingNote.setArchived(note.isArchived());
                    return noteRepository.save(existingNote);
                });
    }

    @Override
    public Mono<Void> deleteNoteById(Long id) {
        return noteRepository.deleteById(id);
    }
}
