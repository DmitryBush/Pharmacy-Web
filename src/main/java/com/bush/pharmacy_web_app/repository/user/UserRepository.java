package com.bush.pharmacy_web_app.repository.user;

import com.bush.pharmacy_web_app.model.entity.user.User;
import com.bush.pharmacy_web_app.model.entity.user.role.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findAll(Pageable pageable);

    @Query("select u from User u " +
            "join u.role r " +
            "where u.mobilePhone like concat(:mobilePhone, '%') and r.type = :type")
    Page<User> findAllByMobilePhoneAndRole(@Param("mobilePhone") String mobilePhone,
                                           @Param("type") RoleType type,
                                           Pageable pageable);
}
