package com.bridgelabz.fundoo.labels.repository;

import com.bridgelabz.fundoo.labels.modal.Labels;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends ReactiveCrudRepository<Labels, Long> {
}
