package com.bridgelabz.fundoo.labels.service;

import com.bridgelabz.fundoo.labels.dto.LabelDTO;
import com.bridgelabz.fundoo.labels.modal.Labels;
import com.bridgelabz.fundoo.response.Response;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface LabelService {
    Mono<ResponseEntity<Response>> createLabel(LabelDTO labelDto, long noteId, String token);

    Mono<ResponseEntity<? extends Object>>  getAllLabels(String token);

    Mono <ResponseEntity<Object>> getLabelByNoteId(Long noteId);

    Mono<ResponseEntity<Response>> updateLabel(Long id, Labels label);

    Mono<ResponseEntity<Response>> deleteLabel(Long id);
}
