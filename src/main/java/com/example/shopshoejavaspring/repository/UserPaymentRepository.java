package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment , Long> {

   List< UserPayment> findUserPaymentByPaymentTypeContaining(String paymentType);

}
