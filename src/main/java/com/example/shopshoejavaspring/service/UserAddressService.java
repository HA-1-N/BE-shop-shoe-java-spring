package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.user.UserAddressDTO;
import com.example.shopshoejavaspring.entity.UserAddress;
import com.example.shopshoejavaspring.mapper.UserAddressMapper;
import com.example.shopshoejavaspring.repository.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;

    private final UserAddressMapper userAddressMapper;

    public List<UserAddressDTO> getUserAddressByUserId(Long userId) {
        List<UserAddress> userAddresses = userAddressRepository.findAllByUserId(userId);
        return userAddressMapper.toDto(userAddresses);
    }
}
