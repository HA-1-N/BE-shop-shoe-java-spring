package com.example.shopshoejavaspring.utils.config;

import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // Kiểm tra và thêm quyền ADMIN nếu người dùng có vai trò "ADMIN"
        if (user.getRoles().stream().anyMatch(role -> role.getCode().equals("ADMIN"))) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        // Kiểm tra và thêm quyền USER nếu người dùng có vai trò "USER"
        if (user.getRoles().stream().anyMatch(role -> role.getCode().equals("USER"))) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // Thêm các quyền khác nếu cần
        // authorities.add(new SimpleGrantedAuthority("ROLE_OTHER"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

}
