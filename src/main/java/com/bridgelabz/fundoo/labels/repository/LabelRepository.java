package com.bridgelabz.fundoo.labels.repository;

import com.bridgelabz.fundoo.labels.modal.Labels;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface LabelRepository extends ReactiveCrudRepository<Labels, Long> {
    Flux<Object> findAllByUserId(long userId);

    Flux<Object> findByNoteId(Long noteId);
}
