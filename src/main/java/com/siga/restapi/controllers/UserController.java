package com.siga.restapi.controllers;

import com.siga.restapi.entities.User;
import com.siga.restapi.exceptions.EmailAlreadyUsedException;
import com.siga.restapi.payload.requests.CreateUserRequest;
import com.siga.restapi.payload.requests.UpdateUserRequest;
import com.siga.restapi.payload.responses.ApiResponse;
import com.siga.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService ;

    @PostMapping
    public ApiResponse<User> saveUser(@RequestBody CreateUserRequest createUserRequest) throws EmailAlreadyUsedException {
        return new ApiResponse<>(HttpStatus.OK.value(),
                "L'utilisateur a été créé avec succès.",
                userService.save(createUserRequest));
    }

    @GetMapping
    public ApiResponse<List<User>> listUser(){
        return new ApiResponse<>(HttpStatus.OK.value(),
                "La liste des utilisateurs a été récupérée avec succès.",
                userService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getOne(@PathVariable int id){
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Les détails de l'utilisateur ont été récupérés avec succès.",
                userService.findById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<User> update(@PathVariable int id, @RequestBody UpdateUserRequest updateUserRequest) throws EmailAlreadyUsedException {
        return new ApiResponse<>(HttpStatus.OK.value(),
                "L'utilisateur a été mis à jour avec succès.",
                userService.update(id, updateUserRequest));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable int id) {
        userService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(),
                "L'utilisateur a été supprimé avec succès.",
                null);
    }

    // Profile section
    @GetMapping("/profil")
    public ApiResponse<User> getAuthProfile(Authentication authentication){
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Les détails de l'utilisateur ont été récupérés avec succès.",
                userService.getAuthProfile(authentication));
    }

    @PutMapping("/profil")
    public ApiResponse<Void> update(Principal principal, @RequestBody UpdateUserRequest updateUserRequest) throws EmailAlreadyUsedException {
        userService.updateAuthProfile(principal, updateUserRequest);
        return new ApiResponse<Void>(HttpStatus.OK.value(),
                "Votre profil a été mis à jour avec succès.", null);
    }
}
