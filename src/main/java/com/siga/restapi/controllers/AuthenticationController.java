package com.siga.restapi.controllers;

import com.siga.restapi.entities.*;
import com.siga.restapi.payload.requests.LoginRequest;
import com.siga.restapi.payload.responses.ApiResponse;
import com.siga.restapi.payload.responses.TokenResponse;
import com.siga.restapi.security.JwtTokenUtil;
import com.siga.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final User user = userService.findUserByEmail(loginRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "Bienvenue.", new TokenResponse(token, user.getEmail()));
    }

}
