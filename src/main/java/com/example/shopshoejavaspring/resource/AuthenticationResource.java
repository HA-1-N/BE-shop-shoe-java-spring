package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.email.EmailDTO;
import com.example.shopshoejavaspring.dto.email.VerifyOtpEmailDTO;
import com.example.shopshoejavaspring.dto.refreshToken.RefreshTokenDTO;
import com.example.shopshoejavaspring.dto.refreshToken.RequestRefreshTokenDTO;
import com.example.shopshoejavaspring.dto.role.RoleDTO;
import com.example.shopshoejavaspring.dto.user.*;
import com.example.shopshoejavaspring.entity.RefreshToken;
import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.service.AuthenticationService;
import com.example.shopshoejavaspring.service.RefreshTokenService;
import com.example.shopshoejavaspring.utils.config.JwtService;
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

    private final RefreshTokenService refreshTokenService;

    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestParam(value = "file", required = false) MultipartFile file, @Valid @RequestPart("data") UserDTO userDTO) throws IOException {
        log.debug("BEGIN - /api/user/register");
        User user = authenticationService.register(userDTO, file);
        userDTO.setId(user.getId());
        userDTO.setRoles(user.getRoles().stream().map(role -> new RoleDTO(role.getId(), role.getCode(), role.getText())).collect(Collectors.toList()));
        log.debug("END - /api/user/register");
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("/mobile/register")
    public ResponseEntity<UserDTO> mobileRegister(@RequestParam(value = "file", required = false) MultipartFile file, @RequestPart("data") UserDTO userDTO) throws IOException {
        log.debug("BEGIN - /api/user/register");
        User user = authenticationService.mobileRegister(userDTO, file);
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
    public ResponseEntity<String> verifyEmail(@RequestParam("email") String email) {
        log.debug("BEGIN - /api/auth/verify-email");
        authenticationService.verifyEmail(email);
        log.debug("END - /api/auth/verify-email");
        return ResponseEntity.status(HttpStatus.OK).body("Verify email success");
    }

    @PostMapping("/verify-otp-email")
    public ResponseEntity<String> verifyEmail(@RequestBody VerifyOtpEmailDTO verifyOtpEmailDTO) {
        log.debug("BEGIN - /api/auth/verify-email");
        authenticationService.verifyOtpEmail(verifyOtpEmailDTO);
        log.debug("END - /api/auth/verify-email");
        return ResponseEntity.status(HttpStatus.OK).body("Verify email success");
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

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RequestRefreshTokenDTO requestRefreshTokenDTO) {
        log.debug("BEGIN - /api/auth/refresh-token");
         return refreshTokenService.refreshToken(requestRefreshTokenDTO)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateToken(user);
                    return ResponseEntity.status(HttpStatus.OK).body(new RequestRefreshTokenDTO(token));
                })
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
//        log.debug("END - /api/auth/refresh-token");

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam("refreshToken") String refreshToken) {
        log.debug("BEGIN - /api/auth/logout");
        refreshTokenService.deleteByToken(refreshToken);
        log.debug("END - /api/auth/logout");
        return ResponseEntity.status(HttpStatus.OK).body("Logout success");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        log.debug("BEGIN - /api/auth/forgot-password");
        authenticationService.forgotPassword(email);
        log.debug("END - /api/auth/forgot-password");
        return ResponseEntity.status(HttpStatus.OK).body("Forgot password success");
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO email) {
        log.debug("BEGIN - /api/auth/send-email");
        authenticationService.sendEmail(email);
        log.debug("END - /api/auth/send-email");
        return ResponseEntity.status(HttpStatus.OK).body("Send email success");
    }

    @PostMapping("/update-role/{id}")
    public ResponseEntity<UserDTO> updateRole(@PathVariable("id") Long id, @RequestBody UserUpdateRoleDTO userUpdateRoleDTOS) {
        log.debug("BEGIN - /api/auth/update-role/{}", id);
        User user = authenticationService.updateRole(id, userUpdateRoleDTOS);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setRoles(user.getRoles().stream().map(role -> new RoleDTO(role.getId(), role.getCode(), role.getText())).collect(Collectors.toList()));
        log.debug("END - /api/auth/update-role/{}", id);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}
