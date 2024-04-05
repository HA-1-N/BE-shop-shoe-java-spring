package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.refreshToken.RequestRefreshTokenDTO;
import com.example.shopshoejavaspring.entity.RefreshToken;
import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.repository.RefreshTokenRepository;
import com.example.shopshoejavaspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")))
                .token(UUID.randomUUID().toString())
                .expiredDate(Instant.now().plusMillis(86400000).toString())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> refreshToken(RequestRefreshTokenDTO requestRefreshTokenDTO) {

//        return refreshTokenRepository.findByToken(requestRefreshTokenDTO.getRefreshToken())
//                .map(refreshToken -> {
//                    refreshToken.setExpiredDate(Instant.now().plusMillis(86400000).toString());
//                    return refreshTokenRepository.save(refreshToken);
//                })
//                .orElseThrow(() -> new RuntimeException("Refresh token not found")

        return refreshTokenRepository.findByToken(requestRefreshTokenDTO.getRefreshToken());

    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
//        if (refreshToken.getExpiredDate().compareTo(Instant.now().toString()) < 0) {
//            refreshTokenRepository.delete(refreshToken);
//            throw new RuntimeException("Refresh token was expired. Please make a new login request");
//        }
//        return refreshToken;

        Instant expiredDate = Instant.parse(refreshToken.getExpiredDate());
        Instant now = Instant.now();

        if (expiredDate.isBefore(now)) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token was expired. Please make a new login request");
        }
        return refreshToken;
    }

    @Transactional
    public void deleteByToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
