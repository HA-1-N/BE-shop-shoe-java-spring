package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.dto.role.RoleDTO;
import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.enumerations.GenderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllByNameContainsAndEmailContains(@Param("name") String name, @Param("email") String email , Pageable pageable);

    @Query(value = "SELECT u.*, r.id, r.code, r.text FROM user u inner join user_role ur on ur.user_id = u.id left join role r on r.id = ur.role_id WHERE u.email LIKE %:email%", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT r.id, r.code, r.text FROM role r INNER JOIN user_role ur ON r.id = ur.role_id WHERE ur.user_id = :id", nativeQuery = true)
    List<RoleDTO> getRoleByUserId(@Param("id") Long id);
}
