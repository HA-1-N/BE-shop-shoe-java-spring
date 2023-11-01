package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.enumerations.GenderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllByNameContainsAndEmailContains(@Param("name") String name, @Param("email") String email , Pageable pageable);

    Optional<User> findByEmail(String email);
}
