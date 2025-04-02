package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findAll(Pageable pageable);

}
