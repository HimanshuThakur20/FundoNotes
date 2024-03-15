package com.bridgelabz.fundoo.labels.service;

import com.bridgelabz.fundoo.labels.dto.LabelDTO;
import com.bridgelabz.fundoo.labels.modal.Labels;
import com.bridgelabz.fundoo.labels.repository.LabelRepository;
import com.bridgelabz.fundoo.notes.modal.Notes;
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
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepository labelRepository;


    @Override
    public Mono<ResponseEntity<Response>> createLabel(LabelDTO labelDto, long noteId, String token) {

        long userId = UserToken.verifyToken(token);
        ModelMapper modelMapper = new ModelMapper();
        Labels labelDAO = modelMapper.map(labelDto, Labels.class);

        labelDAO.setUserId(userId);
        labelDAO.setNoteId(noteId);

        return labelRepository.save(labelDAO).map(createdLabel -> new ResponseEntity<>(new Response(200, "Label created successfully", createdLabel), HttpStatus.OK))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Override
    public Mono<ResponseEntity<? extends Object>> getAllLabels(String token) {
        long userId = UserToken.verifyToken(token);
        return labelRepository.findAllByUserId(userId)
                .collectList()
                .flatMap(labelList -> {
                    if (!labelList.isEmpty()) {
                        return Mono.just(new ResponseEntity<>(Flux.fromIterable(labelList), HttpStatus.OK));
                    } else {
                        return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                    }
                });
    }

    @Override
    public Mono<ResponseEntity<Object>> getLabelByNoteId(Long noteId) {
        return labelRepository.findByNoteId(noteId)
                .collectList()
                .flatMap(labelList -> {
                    if (!labelList.isEmpty()) {
                        return Mono.just(new ResponseEntity<>(labelList, HttpStatus.OK));
                    } else {
                        return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                    }
                });
    }

    @Override
    public Mono<ResponseEntity<Response>> updateLabel(Long id, Labels label) {
        return labelRepository.findById(id)
                .flatMap(existingLabel -> {
                    existingLabel.setName(label.getName());
                    existingLabel.setUserId(label.getUserId());
                    return labelRepository.save(existingLabel) .map(updatedLabel -> new ResponseEntity<>(new Response(200, "Label updated successfully", updatedLabel), HttpStatus.OK))
                            .defaultIfEmpty(new ResponseEntity<>(new Response(404, "Label not found"), HttpStatus.NOT_FOUND))
                            .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));

                });
    }

    @Override
    public Mono<ResponseEntity<Response>> deleteLabel(Long id) {
        return labelRepository.deleteById(id) .then(Mono.just(new ResponseEntity<>(new Response(204, "Label deleted successfully"), HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(new Response(404, "Label not found"), HttpStatus.NOT_FOUND))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));

    }
}
