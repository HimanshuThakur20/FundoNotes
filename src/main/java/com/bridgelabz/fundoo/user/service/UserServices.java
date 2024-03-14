package com.bridgelabz.fundoo.user.service;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.dto.UserLoginDTO;
import com.bridgelabz.fundoo.user.exception.RegistrationException;
import com.bridgelabz.fundoo.user.modal.Users;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserServices {

    public Mono<Response> registerUser(UserLoginDTO users) throws RegistrationException;
    public Mono<Response> loginService(UserLoginDTO userLoginDTO);
    public Mono<Response> verifyUser(String token);

//    Mono<Users> createUser(Users user);
//
    Mono<Users> getUserById(long id);

    Flux<Users>  getAllUsers();
//
    Mono<Boolean> deleteUserById(long id);
}
