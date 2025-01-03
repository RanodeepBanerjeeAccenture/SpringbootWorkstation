package com.accenture.day12_errorhandling_validation.repository;

import com.accenture.day12_errorhandling_validation.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
}
