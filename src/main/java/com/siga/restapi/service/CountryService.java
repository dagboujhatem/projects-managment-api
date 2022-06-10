package com.siga.restapi.service;

import com.siga.restapi.entities.Country;

import java.util.List;

public interface CountryService {
    List<Country> findAll();
}
