package com.siga.restapi.controllers;

import com.siga.restapi.entities.Project;
import com.siga.restapi.payload.responses.ApiResponse;
import com.siga.restapi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping
    public ApiResponse<List<Project>> listProject(){
        return new ApiResponse<>(HttpStatus.OK.value(),
                "La liste des pays a été récupérée avec succès.",
                this.countryService.findAll());
    }
}
