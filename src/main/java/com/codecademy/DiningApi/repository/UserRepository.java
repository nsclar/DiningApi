package com.codecademy.DiningApi.repository;

import com.codecademy.DiningApi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUserName(String userName);
    Optional<User> findUserById(Long id);

}
