package com.example.shopshoejavaspring.service;

import com.cloudinary.Cloudinary;
import com.example.shopshoejavaspring.dto.role.RoleDTO;
import com.example.shopshoejavaspring.dto.user.FilterUserDTO;
import com.example.shopshoejavaspring.dto.user.ListUserDTO;
import com.example.shopshoejavaspring.dto.user.UserDTO;
import com.example.shopshoejavaspring.entity.Role;
import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.mapper.UserMapper;
import com.example.shopshoejavaspring.repository.RoleRepository;
import com.example.shopshoejavaspring.repository.UserRepository;
import com.example.shopshoejavaspring.utils.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final FileStorageService fileStorageService;

    public List<ListUserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
//        List<ListUserDTO> listUserDTO = new ArrayList<>();
//        for (User user : users) {
//            ListUserDTO userDTO = new ListUserDTO();
//            userDTO.setId(user.getId());
//            userDTO.setName(user.getName());
//            userDTO.setEmail(user.getEmail());
//            userDTO.setPhone(user.getPhone());
//            userDTO.setGender(user.getGender());
//            userDTO.setAge(user.getAge());
//            userDTO.setDateOfBirth(user.getDateOfBirth());
//            userDTO.setImage(user.getImage());
//
//            List<RoleDTO> listRoleDTO = new ArrayList<>();
//            for (Role role : user.getRoles()) {
//                RoleDTO roleDTO = new RoleDTO();
//                roleDTO.setId(role.getId());
//                roleDTO.setCode(role.getCode());
//                roleDTO.setText(role.getText());
//                listRoleDTO.add(roleDTO);
//            }
//            userDTO.setRoles(listRoleDTO);
//            listUserDTO.add(userDTO);
//        }
        return userMapper.toDtoUser(users);
    }

    public Page<ListUserDTO> filterUser(FilterUserDTO filterUserDTO, Pageable pageable) {
        Page<User> users = userRepository.findAllByNameContainsAndEmailContains(filterUserDTO.getName(), filterUserDTO.getEmail(), pageable);
        List<ListUserDTO> listUserDTO = userMapper.toDtoUser(users.getContent());
        return new PageImpl<>(listUserDTO, pageable, users.getTotalElements());

        //        List<ListUserDTO> listUserDTO = new ArrayList<>();
//        for (User user : users) {
//            ListUserDTO userDTO = new ListUserDTO();
//            userDTO.setId(user.getId());
//            userDTO.setName(user.getName());
//            userDTO.setEmail(user.getEmail());
//            userDTO.setPhone(user.getPhone());
//            userDTO.setGender(user.getGender());
//            userDTO.setAge(user.getAge());
//            userDTO.setDateOfBirth(user.getDateOfBirth());
//            userDTO.setImage(user.getImage());
//
//            List<RoleDTO> listRoleDTO = new ArrayList<>();
//            for (Role role : user.getRoles()) {
//                RoleDTO roleDTO = new RoleDTO();
//                roleDTO.setId(role.getId());
//                roleDTO.setCode(role.getCode());
//                roleDTO.setText(role.getText());
//                listRoleDTO.add(roleDTO);
//            }
//            userDTO.setRoles(listRoleDTO);
//            listUserDTO.add(userDTO);
//        }
//
//        return new PageImpl<>(listUserDTO, pageable, users.getTotalElements());
    }

    public ListUserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return userMapper.toDtoOptionalUser(user.get());
    }

    public User updateUser(Long id, UserDTO userDTO, MultipartFile file) throws IOException {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            user.get().setName(userDTO.getName());
            user.get().setEmail(userDTO.getEmail());
            user.get().setPassword(userDTO.getPassword());
            user.get().setPhone(userDTO.getPhone());
            user.get().setGender(userDTO.getGender());
            user.get().setAge(userDTO.getAge());
            user.get().setDateOfBirth(userDTO.getDateOfBirth());

            if (file != null && !file.isEmpty()) {
                String imageUrl = fileStorageService.uploadImage(file);
                user.get().setImage(imageUrl);
            }

            if (userDTO.getRoleIds() != null) {
                Set<Role> roles = userDTO.getRoleIds().stream() //get roleIds from userDTO
                        .map(roleId -> roleRepository.findById(roleId)) //get role by roleId
                        .filter(Optional::isPresent) // filter role is present
                        .map(Optional::get) // get role
                        .collect(Collectors.toSet()); // collect to set
                user.get().setRoles(roles);
            }

            userRepository.save(user.get());
            return user.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }

//    public Optional<UserDTO> getUserWithAuthorities() {
//        Optional<User> userOptional = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByEmailIgnoreCase);
//        return null;
//    }
}
