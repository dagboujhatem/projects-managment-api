package com.siga.restapi.service.impl;

import com.siga.restapi.entities.Country;
import com.siga.restapi.repositories.CountryRepository;
import com.siga.restapi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return this.countryRepository.findAll();
    }
}
