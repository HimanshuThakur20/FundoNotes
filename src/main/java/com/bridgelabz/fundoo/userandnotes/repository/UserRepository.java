package com.bridgelabz.fundoo.userandnotes.repository;

import com.bridgelabz.fundoo.userandnotes.modal.Users;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<Users,Long> {
}
