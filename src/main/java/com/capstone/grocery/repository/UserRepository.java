package com.capstone.grocery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.capstone.grocery.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserEmail(String userEmail);

    @Query(value = "{}", fields = "{userEmail : 1, _id : 0}")
    List<User> findAllUsername();

}
