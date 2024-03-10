package com.bridgelabz.fundoo.userandnotes.controller;

import com.bridgelabz.fundoo.userandnotes.modal.Users;
import com.bridgelabz.fundoo.userandnotes.response.Response;
import com.bridgelabz.fundoo.userandnotes.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    private UserServices userServices;


    @PostMapping("/users")
    public Mono<ResponseEntity<Response>> createUser(@RequestBody Users user) {
        return this.userServices.createUser(user)
                .map(savedUser -> new ResponseEntity<>(new Response(200, "User created successfully"), HttpStatus.OK))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @GetMapping("/users/{id}")
    public Mono<ResponseEntity<?>> getUserById(@PathVariable String id) {
        String message = "user with ID " + id + " not found";
        return this.userServices.getUserById(Long.parseLong(id))
                .flatMap(userById->{
                    if (userById != null) {
                        return Mono.just(new ResponseEntity<>(userById, HttpStatus.OK));
                    } else {
                        return Mono.just(new ResponseEntity<>(message, HttpStatus.NOT_FOUND));
                    }
                })
                .switchIfEmpty(Mono.just(new ResponseEntity<>(message,HttpStatus.NOT_FOUND)));
    }



    @GetMapping( "/users")
    public Mono<ResponseEntity<Flux<Users>>> getAllUsers() {
        return this.userServices.getAllUsers()
                .collectList()
                .map(userList -> !userList.isEmpty() ?
                        new ResponseEntity<>(Flux.fromIterable(userList), HttpStatus.OK) :
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/users/{id}")
    public Mono<ResponseEntity<Response>> deleteUserById(@PathVariable String id) {
        return this.userServices.deleteUserById(Long.parseLong(id))
                .flatMap(deletionResult -> {
                    if (deletionResult) {
                        return Mono.just(new ResponseEntity<>(new Response(204, "User removed successfully"), HttpStatus.OK));
                    } else {
                        return Mono.just(new ResponseEntity<>(new Response(404, "User doesn't exist"), HttpStatus.NOT_FOUND));
                    }
                })
                .onErrorResume(error -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
