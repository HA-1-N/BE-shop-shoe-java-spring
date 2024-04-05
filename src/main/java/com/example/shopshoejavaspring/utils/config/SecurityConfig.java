package com.example.shopshoejavaspring.utils.config;

import com.example.shopshoejavaspring.utils.common.AuthoritiesConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors() // config cors
                .and()
                .csrf() // config csrf
                .disable() // disable csrf
                .authorizeHttpRequests() // config authorize request
                .requestMatchers(request -> request.getServletPath().startsWith("/api/auth"))
                .permitAll() // all request start with /api/auth is permit all
                .antMatchers(
                        "/api/banners/get-all",
                        "/api/product/filter",
                        "/api/product/get-by-id/**",
                        "/api/product/filter-website",
                        "/api/hot-category/**",
                        "/api/brand/get-all",
                        "/api/category/get-all",
                        "/api/color/get-all",
                        "/api/size/get-all",
                        "/api/cart/count-cart/**"
                )
                .permitAll()
                .antMatchers(
                        "/api/banners/create/",
                        "/api/brand/filter/**"
                ).hasAuthority(AuthoritiesConstants.ADMIN)
                .antMatchers(
                        "/api/cart/add-to-cart",
                        "/api/order/get-order-by-user-id/**"
                ).hasAuthority(AuthoritiesConstants.USER)
                .anyRequest()
                .authenticated() // all request must be authenticated
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic();
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("X-Total-Count", "X-Total-Pages", "X-Page-Number", "X-Page-Size"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
