package com.bridgelabz.fundoo.labels.controller;

import com.bridgelabz.fundoo.labels.modal.Labels;
import com.bridgelabz.fundoo.labels.service.LabelService;
import com.bridgelabz.fundoo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @PostMapping
    public Mono<ResponseEntity<Response>> createLabel(@RequestBody Labels label) {
        return labelService.createLabel(label)
                .map(createdLabel -> new ResponseEntity<>(new Response(200, "Label created successfully", createdLabel), HttpStatus.OK))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Labels>>> getAllLabels() {
        return labelService.getAllLabels()
                .collectList()
                .map(labelList -> !labelList.isEmpty() ?
                        new ResponseEntity<>(Flux.fromIterable(labelList), HttpStatus.OK) :
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Labels>> getLabelById(@PathVariable Long id) {
        String message = "Label with ID " + id + " not found";
        return labelService.getLabelById(id)
                .flatMap(label -> Mono.just(new ResponseEntity<>(label, HttpStatus.OK)))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }



    @PutMapping("/{id}")
    public Mono<ResponseEntity<Response>> updateLabel(@PathVariable Long id, @RequestBody Labels label) {
        return labelService.updateLabel(id, label)
                .map(updatedLabel -> new ResponseEntity<>(new Response(200, "Label updated successfully", updatedLabel), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(new Response(404, "Label not found"), HttpStatus.NOT_FOUND))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response>> deleteLabel(@PathVariable Long id) {
        return labelService.deleteLabel(id)
                .then(Mono.just(new ResponseEntity<>(new Response(204, "Label deleted successfully"), HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(new Response(404, "Label not found"), HttpStatus.NOT_FOUND))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
