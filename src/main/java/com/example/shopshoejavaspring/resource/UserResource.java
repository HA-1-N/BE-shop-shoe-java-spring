package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.role.RoleDTO;
import com.example.shopshoejavaspring.dto.user.FilterUserDTO;
import com.example.shopshoejavaspring.dto.user.ListUserDTO;
import com.example.shopshoejavaspring.dto.user.UserDTO;
import com.example.shopshoejavaspring.dto.user.UserDetailUpdateDTO;
import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.service.UserService;
import javafx.scene.control.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
public class UserResource {

    @Autowired
    private final UserService userService;

    @PostMapping("/filter")
    public ResponseEntity<List<ListUserDTO>> filterUser(@RequestBody FilterUserDTO filterUserDTO, Pageable pageable){
        log.debug("BEGIN - /api/user/filter");
        Page<ListUserDTO> listUser = userService.filterUser(filterUserDTO, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(listUser.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(listUser.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(listUser.getNumber()));
        headers.add("X-Page-Size", String.valueOf(listUser.getSize()));
        log.debug("END - /api/user/filter");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(listUser.getContent());
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ListUserDTO>> getAllUser() {
        log.debug("BEGIN - /api/user/get-all");
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ListUserDTO> getUserById(@PathVariable("id") Long id) {
        log.debug("BEGIN - /api/user/get-by-id/{}", id);
        ListUserDTO userDTO = userService.getUserById(id);
        log.debug("END - /api/user/get-by-id/{}", id);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

//    @GetMapping("/current-user")
//    public ResponseEntity<UserDTO> getCurrentUser() {
//        log.debug("BEGIN - /api/user/current-user");
//        Optional<UserDTO> userDTO = userService.getUserWithAuthorities();
//        log.debug("END - /api/user/current-user");
//        return ResponseEntity.status(HttpStatus.OK).body(userDTO.get());
//    }

    @PostMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestParam(value = "file", required = false)MultipartFile file, @Valid @RequestPart("data") UserDTO userDTO) throws IOException {
        log.debug("BEGIN - /api/user/update/{}", id);
        User user = userService.updateUser(id, userDTO, file);
        userDTO.setId(user.getId());
        userDTO.setRoles(user.getRoles().stream().map(role -> new RoleDTO(role.getId(), role.getCode(), role.getText())).collect(Collectors.toList()));
        log.debug("END - /api/user/update/{}", id);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PostMapping("/update/user-detail/{id}")
    public ResponseEntity<UserDetailUpdateDTO> updateUserDetail(@PathVariable("id") Long id , @RequestParam(value = "file", required = false)MultipartFile file, @Valid @RequestPart("data") UserDetailUpdateDTO userDetailUpdateDTO) throws IOException {
        log.debug("BEGIN - /api/user/update/user-detail/{}", id);
        User user = userService.updateUserDetail(id, userDetailUpdateDTO, file);
        userDetailUpdateDTO.setId(user.getId());
        log.debug("END - /api/user/update/user-detail/{}", id);
        return ResponseEntity.status(HttpStatus.OK).body(userDetailUpdateDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.debug("BEGIN - /api/user/delete/{}", id);
        userService.deleteUser(id);
        log.debug("END - /api/user/delete/{}", id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/total-user")
    public ResponseEntity<Long> getTotalUser() {
        log.debug("BEGIN - /api/user/total-user");
        Long totalUser = userService.getTotalUser();
        log.debug("END - /api/user/total-user");
        return ResponseEntity.status(HttpStatus.OK).body(totalUser);
    }




}
