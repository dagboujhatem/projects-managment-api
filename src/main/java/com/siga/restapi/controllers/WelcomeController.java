package com.siga.restapi.controllers;

import com.siga.restapi.payload.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class WelcomeController {

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ApiResponse<Object> welcome(){;
        return new ApiResponse<>(200, "Bienvenue." ,null);

    }
}
