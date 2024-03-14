package com.bridgelabz.fundoo.user.modal;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    private String password;
    @Column(name = "is_erified")
    private Boolean isVerified;

}
