package com.siga.restapi.payload.requests;

import com.siga.restapi.entities.EState;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateProjectRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String description;
    private EState state;
    private int pays;
    @NotBlank
    private String prix;
    @NotBlank
    private String startDate;
    @NotBlank
    private String endDate;
}
