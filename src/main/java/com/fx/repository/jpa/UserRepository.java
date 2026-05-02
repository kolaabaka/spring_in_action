package com.fx.repository.jpa;

import com.fx.dto.jpa.UserCredential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserCredential, Long>{

    Optional<UserCredential> findById(Long id);
    UserCredential findByUsername(String username);

}
