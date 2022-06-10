package com.siga.restapi.repositories;

import com.siga.restapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // return True if email already exist
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Integer id);
    // find user by email address
    User findByEmail(String email);
}
