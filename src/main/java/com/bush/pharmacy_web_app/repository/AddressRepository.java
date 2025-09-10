package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
