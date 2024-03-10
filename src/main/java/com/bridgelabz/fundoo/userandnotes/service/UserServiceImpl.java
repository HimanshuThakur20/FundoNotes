package com.bridgelabz.fundoo.userandnotes.service;

import com.bridgelabz.fundoo.userandnotes.modal.Users;
import com.bridgelabz.fundoo.userandnotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserServices {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Users> createUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<Users> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<Boolean> deleteUserById(long id) {
        return userRepository.findById(id)
                .flatMap(user -> {
                    return userRepository.delete(user).then(Mono.just(true));
                })
                .switchIfEmpty(Mono.just(false));
    }
}
