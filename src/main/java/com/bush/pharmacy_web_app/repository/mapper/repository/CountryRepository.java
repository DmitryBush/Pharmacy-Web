package com.bush.pharmacy_web_app.repository.mapper.repository;

import com.bush.pharmacy_web_app.repository.entity.manufacturer.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByCountry(String country);
}
