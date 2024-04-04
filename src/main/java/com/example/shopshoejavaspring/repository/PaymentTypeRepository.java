package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.PaymentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    Page<PaymentType> findPaymentTypeByTypeContaining(String type, Pageable pageable);

    PaymentType findPaymentTypeByTypeContaining(String type);
}
