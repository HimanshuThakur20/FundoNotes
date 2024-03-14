package com.bridgelabz.fundoo.user.repository;

import com.bridgelabz.fundoo.user.modal.Users;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends R2dbcRepository<Users,Long> {
    Flux<Object> findByEmail(String email);
}
