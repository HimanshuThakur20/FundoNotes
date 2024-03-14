package com.bridgelabz.fundoo.user.controller;

import com.bridgelabz.fundoo.user.dto.UserLoginDTO;
import com.bridgelabz.fundoo.user.modal.Users;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;


    @PostMapping("/signup")
    public Mono<ResponseEntity<Mono<Response>>> addUser(@RequestBody UserLoginDTO user) {
        Mono<Response> response = userServices.registerUser(user);
        return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @GetMapping("/login")
    public Mono<ResponseEntity<Mono<Response>>> loginByEmailAndPassword(@RequestBody UserLoginDTO user) {
        Mono<Response> response = userServices.loginService(user);
        return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @GetMapping("/user/{token}")
    public Mono<ResponseEntity<Mono<Response>>> verifyToken(@PathVariable String token) {
        Mono<Response> response = userServices.verifyUser(token);
        return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @PutMapping("/update/{id}")
    public Mono<Users> updateUser(@PathVariable int id, @RequestBody Users user) {
        return null;
    }




//    @PostMapping("/users")
//    public Mono<ResponseEntity<Response>> createUser(@RequestBody Users user) {
//        return this.userServices.createUser(user)
//                .map(savedUser -> new ResponseEntity<>(new Response(200, "User created successfully"), HttpStatus.OK))
//                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
//    }
//
    @GetMapping("/{id}")
    public Mono<Users> getUserById(@PathVariable String id) {
        Mono<Users> response = this.userServices.getUserById(Long.parseLong(id));
        return response;
    }
//
//
//
@GetMapping( "/all")
public Mono<ResponseEntity<Flux<Users>>> getAllUsers() {
    return this.userServices.getAllUsers()
            .collectList()
            .map(userList -> !userList.isEmpty() ?
                    new ResponseEntity<>(Flux.fromIterable(userList), HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND));
}


    @DeleteMapping("/delete/{id}")
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
