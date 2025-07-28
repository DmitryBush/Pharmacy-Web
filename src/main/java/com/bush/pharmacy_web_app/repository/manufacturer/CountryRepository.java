package com.bush.pharmacy_web_app.repository.manufacturer;

import com.bush.pharmacy_web_app.model.entity.manufacturer.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByCountry(String country);

    List<Country> findByCountryContainingIgnoreCase(@PathVariable String country);
}
