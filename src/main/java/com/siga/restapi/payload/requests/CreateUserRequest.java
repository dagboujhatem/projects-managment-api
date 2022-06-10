package com.siga.restapi.payload.requests;

import com.siga.restapi.entities.ERole;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CreateUserRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private ERole role;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
