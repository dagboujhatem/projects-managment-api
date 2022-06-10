package com.siga.restapi.payload.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateTaskRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String libelle;
}
