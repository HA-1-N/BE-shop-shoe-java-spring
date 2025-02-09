package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    @Query(value = "SELECT * FROM user_address WHERE user_id = :userId", nativeQuery = true)
    List<UserAddress> findAllByUserId(@Param("userId") Long userId);
}
