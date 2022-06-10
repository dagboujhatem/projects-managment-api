package com.siga.restapi.payload.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateTaskRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String libelle;
    private int project;
}
