package com.siga.restapi.service;

import com.siga.restapi.entities.User;
import com.siga.restapi.exceptions.EmailAlreadyUsedException;
import com.siga.restapi.payload.requests.CreateUserRequest;
import com.siga.restapi.payload.requests.UpdateUserRequest;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

public interface UserService {
    User findUserByEmail(String username);
    List<User> findAll();
    User save(CreateUserRequest createUserRequest) throws EmailAlreadyUsedException;
    User findById(int id);
    User update(int id, UpdateUserRequest updateUserRequest) throws EmailAlreadyUsedException;
    void delete(int id);
    // Profile section
    User getAuthProfile(Authentication authentication);
    void updateAuthProfile(Principal principal, UpdateUserRequest updateUserRequest) throws EmailAlreadyUsedException;
}
