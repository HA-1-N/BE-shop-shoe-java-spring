package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.dto.role.RoleDTO;
import com.example.shopshoejavaspring.dto.user.UserDTO;
import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.enumerations.GenderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from user u where " +
            "(:name is null or u.name like concat ('%', :name ,'%')) and " +
            "(:email is null or u.email like concat ('%', :email ,'%')) and " +
            "(:gender is null or u.gender = :gender) and " +
            "(:phone is null or u.phone like concat ('%', :phone ,'%')) and " +
            "(:dateOfBirth is null or u.date_of_birth = :dateOfBirth)",
            nativeQuery = true)
    Page<User> filterUser(
            @Param("name") String name,
            @Param("email") String email ,
            @Param("gender") String gender,
            @Param("phone") String phone,
            @Param("dateOfBirth") Date dateOfBirth,
            Pageable pageable);

    @Query(value = "SELECT u FROM User u join fetch u.roles WHERE u.email LIKE %:email%")
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT r.id, r.code, r.text FROM role r INNER JOIN user_role ur ON r.id = ur.role_id WHERE ur.user_id = :id", nativeQuery = true)
    List<RoleDTO> getRoleByUserId(@Param("id") Long id);

    Boolean existsByEmail(String email);

    User findUserByEmailContains(String email);
}
