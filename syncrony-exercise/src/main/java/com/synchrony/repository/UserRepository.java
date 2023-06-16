package com.synchrony.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.synchrony.model.User;

public interface UserRepository extends JpaRepository<User, String> {
  User findByUserName(String userName);

}
