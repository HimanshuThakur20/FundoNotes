package com.bridgelabz.fundoo.notes.service;

import com.bridgelabz.fundoo.notes.dto.NotesDTO;
import com.bridgelabz.fundoo.notes.modal.Notes;
import com.bridgelabz.fundoo.notes.repository.NoteRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.util.UserToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Mono<ResponseEntity<Response>> saveNote(NotesDTO noteDto, String token) {
        long userId = UserToken.verifyToken(token);
        ModelMapper modelMapper = new ModelMapper();
        Notes noteDAO = modelMapper.map(noteDto, Notes.class);
        noteDAO.setUserId(userId);
        return this.noteRepository.save(noteDAO).map(savedNote -> new ResponseEntity<>(new Response(200, "Note saved successfully"), HttpStatus.OK))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Override
    public Mono<ResponseEntity<Response>> findByUserIdAndArchivedFalse(String token) {
        long userId = UserToken.verifyToken(token);
        return noteRepository.findByUserIdAndArchivedFalse(userId).collectList()
                .map(noteList -> !noteList.isEmpty() ?
                        new ResponseEntity<>(new Response(200, "Active notes retrieved successfully", noteList), HttpStatus.OK) :
                        new ResponseEntity<>(new Response(404, "No active notes found"), HttpStatus.NOT_FOUND))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Override
    public Mono<ResponseEntity<Response>> findByUserIdAndArchivedTrue(String token) {
        long userId = UserToken.verifyToken(token);
        return noteRepository.findByUserIdAndArchivedTrue(userId)
                .collectList()
                .map(noteList -> !noteList.isEmpty() ?
                        new ResponseEntity<>(new Response(200, "Archived notes retrieved successfully", noteList), HttpStatus.OK) :
                        new ResponseEntity<>(new Response(404, "No archived notes found"), HttpStatus.NOT_FOUND))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));

    }


    @Override
    public Mono<ResponseEntity<Response>> getNoteById(Long id) {
        return noteRepository.findById(id)
                .map(note -> new ResponseEntity<>(new Response(200, "Note retrieved successfully", note), HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(new Response(404, "Note not found"), HttpStatus.NOT_FOUND)))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));

    }

    @Override
    public Mono<ResponseEntity<Response>> updateNoteById(Long id, NotesDTO noteDto) {

        ModelMapper modelMapper = new ModelMapper();
        Notes noteDAO = modelMapper.map(noteDto, Notes.class);
        return noteRepository.findById(id)
                .flatMap(existingNote -> {
                    existingNote.setTitle(noteDAO.getTitle());
                    existingNote.setContent(noteDAO.getContent());
                    existingNote.setArchived(noteDAO.isArchived());
                    existingNote.setTrash(noteDAO.getTrash());
                    return noteRepository.save(existingNote)
                            .map(updatedNote -> new ResponseEntity<>(new Response(200, "Note updated successfully",updatedNote), HttpStatus.OK))
                            .switchIfEmpty(Mono.just(new ResponseEntity<>(new Response(404, "Note not found"), HttpStatus.NOT_FOUND)))
                            .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));

                });
    }

    @Override
    public Mono<ResponseEntity<Response>> pinNoteById(String token){
        long userId = UserToken.verifyToken(token);
        return noteRepository.findByUserIdAndPinnedTrue(userId)
                .collectList()
                .map(notes -> new ResponseEntity<>(new Response(200, "Pinned notes retrieved successfully", notes),HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(new Response(404, "No pinned notes found"), HttpStatus.NOT_FOUND)))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Override
    public Mono<ResponseEntity<Response>> unpinNoteById(String token){
        long userId = UserToken.verifyToken(token);
        return noteRepository.findByUserIdAndPinnedFalse(userId)
                .collectList()
                .map(notes -> new ResponseEntity<>(new Response(200, "Unpinned notes retrieved successfully", notes), HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(new Response(404, "No unpinned notes found"), HttpStatus.NOT_FOUND)))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }


    @Override
    public Mono<ResponseEntity<Response>> deleteNoteById(String token) {
        long userId = UserToken.verifyToken(token);
        return noteRepository.findByUserIdAndTrashTrue(userId)
                .flatMap(note -> noteRepository.delete((Notes) note))
                .then(Mono.just(new ResponseEntity<>(new Response(204, "Notes deleted successfully"), HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(new Response(404, "Notes not found"), HttpStatus.NOT_FOUND))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }



}