package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.email.EmailDTO;
import com.example.shopshoejavaspring.dto.email.VerifyOtpEmailDTO;
import com.example.shopshoejavaspring.dto.refreshToken.RequestRefreshTokenDTO;
import com.example.shopshoejavaspring.dto.role.RoleDTO;
import com.example.shopshoejavaspring.dto.user.ChangePasswordDTO;
import com.example.shopshoejavaspring.dto.user.ResetPasswordDTO;
import com.example.shopshoejavaspring.dto.user.UserDTO;
import com.example.shopshoejavaspring.dto.user.UserLoginDTO;
import com.example.shopshoejavaspring.entity.ForgotPassword;
import com.example.shopshoejavaspring.entity.RefreshToken;
import com.example.shopshoejavaspring.entity.Role;
import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.repository.ForgotPasswordRepository;
import com.example.shopshoejavaspring.repository.RoleRepository;
import com.example.shopshoejavaspring.repository.UserRepository;
import com.example.shopshoejavaspring.utils.FileStorageService;
import com.example.shopshoejavaspring.utils.config.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final FileStorageService fileStorageService;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    private final JavaMailSender javaMailSender;

    private final ForgotPasswordRepository forgotPasswordRepository;

    public UserDTO login(UserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
        );
        User user = userRepository.findByEmail(userLoginDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
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
        userDTO.setRefreshToken(refreshToken.getToken());

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

        Optional<User> checkEmailUser = userRepository.findByEmail(userDTO.getEmail());
        if (checkEmailUser.isPresent()) {
            throw new RuntimeException("Email is exist");
        } else {
            user.setEmail(userDTO.getEmail());
        }

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Optional<User> checkPhoneUser = userRepository.findUserByPhoneContains(userDTO.getPhone());
        if (checkPhoneUser.isPresent()) {
            throw new RuntimeException("Phone is exist");
        } else {
            user.setPhone(userDTO.getPhone());
        }

        user.setGender(userDTO.getGender());
        user.setAge(userDTO.getAge());
        user.setPrefix(userDTO.getPrefix());
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

    public String verifyEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Please provide a valid email address"));

        Long otp = otpGenerator();
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setTo(email);
        emailDTO.setSubject("Verify Email");
        emailDTO.setBody("Your OTP is: " + otp);

        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setOtp(otp);
        forgotPassword.setExpiredTime(new Date(System.currentTimeMillis() + 70 * 1000));
        forgotPassword.setUser(user);
        forgotPasswordRepository.save(forgotPassword);

        sendEmail(emailDTO);

        return "OTP has been sent to your email" + otp.toString();
    }

    public void verifyOtpEmail(VerifyOtpEmailDTO verifyOtpEmailDTO) {

        User user = userRepository.findByEmail(verifyOtpEmailDTO.getEmail()).orElseThrow(() -> new RuntimeException("Please provide a valid email address"));

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(verifyOtpEmailDTO.getOtp(), user).orElseThrow(() -> new RuntimeException("Invalid OTP for email " + verifyOtpEmailDTO.getEmail()));
        if (forgotPassword.getExpiredTime().before(Date.from(Instant.now()))) {
            throw new RuntimeException("OTP is expired");
        }
        forgotPasswordRepository.delete(forgotPassword);
    }

    private Long otpGenerator() {
        return (long) (Math.random() * 1000000);
    }

    public void forgotPassword(String email) {
    }

    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = userRepository.findUserByEmailContains(resetPasswordDTO.getEmail());
        if (user == null) {
            return null;
        }
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        userRepository.save(user);
        return "Reset password success";
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findById(changePasswordDTO.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }


    public void sendEmail(EmailDTO emailDTO) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDTO.getTo());
        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getBody());

        javaMailSender.send(message);

//        MimeMessage message = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setTo(emailDTO.getTo());
//            helper.setSubject(emailDTO.getSubject());
//            helper.setText(emailDTO.getBody(), true);
//            javaMailSender.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

    }


}
