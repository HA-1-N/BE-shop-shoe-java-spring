package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.role.RoleDTO;
import com.example.shopshoejavaspring.dto.user.ChangePasswordDTO;
import com.example.shopshoejavaspring.dto.user.ResetPasswordDTO;
import com.example.shopshoejavaspring.dto.user.UserDTO;
import com.example.shopshoejavaspring.dto.user.UserLoginDTO;
import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestParam(value = "file", required = false) MultipartFile file, @Valid @RequestPart("data") UserDTO userDTO) throws IOException {
        log.debug("BEGIN - /api/user/register");
        User user = authenticationService.register(userDTO, file);
        userDTO.setId(user.getId());
        userDTO.setRoles(user.getRoles().stream().map(role -> new RoleDTO(role.getId(), role.getCode(), role.getText())).collect(Collectors.toList()));
        log.debug("END - /api/user/register");
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        log.debug("BEGIN - /api/auth/login");
        UserDTO user = authenticationService.login(userLoginDTO);
        log.debug("END - /api/auth/login");
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Boolean> verifyEmail(@RequestParam("email") String email) {
        log.debug("BEGIN - /api/auth/verify-email");
        Boolean isExist = authenticationService.verifyEmail(email);
        log.debug("END - /api/auth/verify-email");
        return ResponseEntity.status(HttpStatus.OK).body(isExist);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        log.debug("BEGIN - /api/auth/change-password");
        authenticationService.changePassword(changePasswordDTO);
        log.debug("END - /api/auth/change-password");
        return ResponseEntity.status(HttpStatus.OK).body("Change password success");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        log.debug("BEGIN - /api/auth/reset-password");
        authenticationService.resetPassword(resetPasswordDTO);
        log.debug("END - /api/auth/reset-password");
        return ResponseEntity.status(HttpStatus.OK).body("Reset password success");
    }
}
