package com.bridgelabz.fundoo.user.service;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.dto.UserLoginDTO;
import com.bridgelabz.fundoo.user.exception.LoginException;
import com.bridgelabz.fundoo.user.exception.RegistrationException;
import com.bridgelabz.fundoo.user.modal.Users;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.util.UserToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.core.env.Environment;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserServices {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    Environment environment;

    public Mono<Response> registerUser(UserLoginDTO user) {
        return userRepository.findByEmail(user.getEmail())
                .collectList()
                .flatMap(existingUsers -> {
                    if (!existingUsers.isEmpty()) {
                        // User with the given email already exists
                        return Mono.just(new Response(500,"User already exsists"));
//                        return Mono.error(new RegistrationException(environment.getProperty("status.register.userAlreadyExist")));
                    } else {
                        // No user with the given email exists
                        ModelMapper modelMapper = new ModelMapper();
                        Users newUser = modelMapper.map(user, Users.class);
                        String message = "status.signup.success";
                        int successCode = Integer.parseInt("33");
                        return userRepository.save(newUser)
                                .map(savedUser -> new Response(successCode,message,newUser));
                    }
                });
    }

    public Mono<Response> loginService(UserLoginDTO userLoginDTO) {
        return userRepository.findAll()
                .filter(user -> user.getEmail().equals(userLoginDTO.getEmail()))
                .next() // This effectively replaces findFirst, as it returns the first item in the stream or empty if the stream is empty.
                .flatMap(userDAO -> {
                    if (userDAO.getPassword().equals(userLoginDTO.getPassword())) {
                        String token = UserToken.generateToken((int)userDAO.getId());
                        String message = "status.login.success";
                        int successCode = Integer.parseInt("3");
                        return Mono.just(new Response(successCode,message, token));
                    } else {
                        return Mono.error(new LoginException("status.login.passwordIncorrect"));
                    }
                })
                .switchIfEmpty(Mono.error(new LoginException("status.login.userNotFound")));
    }


    public Mono<Response> verifyUser(String token) {
        long id = UserToken.verifyToken(token);
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(user -> {
                    user.setIsVerified(true);
                    return userRepository.save(user)
                            .thenReturn(new Response(200, "User verified successfully", id));
                });
    }


    //    @Override
//    public Mono<Users> createUser(Users user) {
//        return userRepository.save(user);
//    }
//
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
