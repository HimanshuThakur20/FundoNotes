package com.bridgelabz.fundoo.labels.controller;

import com.bridgelabz.fundoo.labels.dto.LabelDTO;
import com.bridgelabz.fundoo.labels.modal.Labels;
import com.bridgelabz.fundoo.labels.service.LabelService;
import com.bridgelabz.fundoo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @PostMapping("/{noteId}/{token}")
    public Mono<ResponseEntity<Response>> createLabel(@RequestBody LabelDTO label, @PathVariable long noteId, @PathVariable String token) {
        return labelService.createLabel(label,noteId,token);
                 }

    @GetMapping("/all/{token}")
    public Mono<ResponseEntity<? extends Object>>  getAllLabels(@PathVariable String token) {
        return labelService.getAllLabels(token);

    }

    @GetMapping("/{noteId}")
    public Mono <ResponseEntity<Object>> getLabelById(@PathVariable Long noteId) {
        return labelService.getLabelByNoteId(noteId);
    }



    @PutMapping("/{id}")
    public Mono<ResponseEntity<Response>> updateLabel(@PathVariable Long id, @RequestBody Labels label) {
        return labelService.updateLabel(id, label);
               }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response>> deleteLabel(@PathVariable Long id) {
        return labelService.deleteLabel(id);
               }
}
