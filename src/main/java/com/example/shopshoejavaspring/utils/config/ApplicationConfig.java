package com.example.shopshoejavaspring.utils.config;

import com.example.shopshoejavaspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

//    private final UserRepository userRepository;
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userName ->
//        {
//            try {
//                return (UserDetails) userRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//            return null;
//        };
//    }
}
