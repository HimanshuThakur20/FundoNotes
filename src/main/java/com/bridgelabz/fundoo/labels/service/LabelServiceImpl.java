package com.bridgelabz.fundoo.labels.service;

import com.bridgelabz.fundoo.labels.modal.Labels;
import com.bridgelabz.fundoo.labels.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepository labelRepository;


    @Override
    public Mono<Labels> createLabel(Labels label) {
        return labelRepository.save(label);
    }

    @Override
    public Flux<Labels> getAllLabels() {
        return labelRepository.findAll();
    }

    @Override
    public Mono<Labels> getLabelById(Long id) {
        return labelRepository.findById(id);
    }

    @Override
    public Mono<Labels> updateLabel(Long id, Labels label) {
        return labelRepository.findById(id)
                .flatMap(existingLabel -> {
                    existingLabel.setName(label.getName());
                    existingLabel.setUserId(label.getUserId());
                    return labelRepository.save(existingLabel);
                });
    }

    @Override
    public Mono<Void> deleteLabel(Long id) {
        return labelRepository.deleteById(id);
    }
}
