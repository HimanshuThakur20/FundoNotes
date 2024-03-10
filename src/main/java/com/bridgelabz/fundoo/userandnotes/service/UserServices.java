package com.bridgelabz.fundoo.userandnotes.service;

import com.bridgelabz.fundoo.userandnotes.modal.Users;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserServices {
    Mono<Users> createUser(Users user);

    Mono<Users> getUserById(long id);

    Flux<Users> getAllUsers();

    Mono<Boolean> deleteUserById(long id);
}
