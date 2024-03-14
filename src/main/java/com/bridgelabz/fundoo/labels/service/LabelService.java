package com.bridgelabz.fundoo.labels.service;

import com.bridgelabz.fundoo.labels.modal.Labels;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LabelService {
    Mono<Labels> createLabel(Labels label);

    Flux<Labels> getAllLabels();

    Mono<Labels> getLabelById(Long id);

    Mono<Labels> updateLabel(Long id, Labels label);

    Mono<Void> deleteLabel(Long id);
}
