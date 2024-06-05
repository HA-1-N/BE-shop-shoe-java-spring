package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.ForgotPassword;
import com.example.shopshoejavaspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

  @Query("SELECT f FROM ForgotPassword f WHERE f.otp = ?1 AND f.user = ?2")
  Optional<ForgotPassword> findByOtpAndUser(Long otp, User user);

  @Query("SELECT f FROM ForgotPassword f WHERE f.user = ?1")
  List<ForgotPassword> findByUser(User user);
}
