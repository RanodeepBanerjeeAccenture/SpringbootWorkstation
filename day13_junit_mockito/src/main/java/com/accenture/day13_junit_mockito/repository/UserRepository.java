package com.accenture.day13_junit_mockito.repository;

import com.accenture.day13_junit_mockito.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}