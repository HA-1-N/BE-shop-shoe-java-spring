package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.role.RoleDTO;
import com.example.shopshoejavaspring.dto.user.UserDTO;
import com.example.shopshoejavaspring.dto.user.UserLoginDTO;
import com.example.shopshoejavaspring.entity.Role;
import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.repository.RoleRepository;
import com.example.shopshoejavaspring.repository.UserRepository;
import com.example.shopshoejavaspring.utils.FileStorageService;
import com.example.shopshoejavaspring.utils.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final FileStorageService fileStorageService;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDTO login(UserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
        );
        User user = userRepository.findByEmail(userLoginDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        String jwtToken = jwtService.generateToken(user);
//        UserDTO userDTO = UserDTO.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .email(user.getEmail())
//                .gender(user.getGender())
//                .age(user.getAge())
//                .phone(user.getPhone())
//                .dateOfBirth(user.getDateOfBirth())
//                .image(user.getImage())
//                .token(jwtToken)
//                .roles(userRepository.getRoleByUserId(user.getId()).stream()
//                        .map(role -> RoleDTO.builder().id(role.getId()).code(role.getCode()).text(role.getText()).build())
//                        .collect(Collectors.toList())
//                )
//                .build();

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setGender(user.getGender());
        userDTO.setAge(user.getAge());
        userDTO.setPhone(user.getPhone());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setImage(user.getImage());
        userDTO.setToken(jwtToken);

        List<RoleDTO> listRoleDTO = new ArrayList<>();

        for (Role role : user.getRoles()) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(role.getId());
            roleDTO.setCode(role.getCode());
            roleDTO.setText(role.getText());
            listRoleDTO.add(roleDTO);
        }
        userDTO.setRoles(listRoleDTO);

        return userDTO;
    }

    public User register(UserDTO userDTO, MultipartFile file) throws IOException {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPhone(userDTO.getPhone());
        user.setGender(userDTO.getGender());
        user.setAge(userDTO.getAge());
        user.setDateOfBirth(userDTO.getDateOfBirth());

        String imageUrl = fileStorageService.uploadImage(file);
        user.setImage(imageUrl);

        Set<Role> roles = userDTO.getRoleIds().stream() //get roleIds from userDTO
                .map(roleId -> roleRepository.findById(roleId)) //get role by roleId
                .filter(Optional::isPresent) // filter role is present
                .map(Optional::get) // get role
                .collect(Collectors.toSet()); // collect to set

        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

}
