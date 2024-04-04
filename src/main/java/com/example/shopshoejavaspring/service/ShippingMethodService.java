package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.shippingMethod.ShippingMethodDTO;
import com.example.shopshoejavaspring.entity.ShippingMethod;
import com.example.shopshoejavaspring.mapper.ShippingMethodMapper;
import com.example.shopshoejavaspring.repository.ShippingMethodRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShippingMethodService {

    private final ShippingMethodRepository shippingMethodRepository;

    private final ShippingMethodMapper shippingMethodMapper;

    public ShippingMethod createShippingMethod(ShippingMethodDTO shippingMethodDTO) {
        ShippingMethod shippingMethod = shippingMethodMapper.toEntity(shippingMethodDTO);
        shippingMethodRepository.save(shippingMethod);
        return shippingMethod;
    }


    public ShippingMethod updateShippingMethod(ShippingMethodDTO shippingMethodDTO) {
        shippingMethodRepository.findById(shippingMethodDTO.getId()).orElseThrow(() -> new RuntimeException("Shipping method not found"));
        ShippingMethod shippingMethod = shippingMethodMapper.toEntity(shippingMethodDTO);
        shippingMethodRepository.save(shippingMethod);
        return shippingMethod;
    }

    public Page<ShippingMethodDTO> filterShippingMethod(ShippingMethodDTO shippingMethodDTO, Pageable pageable) {
        Page<ShippingMethod> shippingMethod = shippingMethodRepository.findShippingMethodByMethodContaining(shippingMethodDTO.getMethod(), pageable);
        List<ShippingMethodDTO> shippingMethodDTOS = shippingMethodMapper.toDto(shippingMethod.getContent());
        return new PageImpl<>(shippingMethodDTOS, pageable, shippingMethod.getTotalElements());
    }

    public ShippingMethodDTO findShippingMethodById(Long id) {
        ShippingMethod shippingMethod = shippingMethodRepository.findById(id).orElseThrow(() -> new RuntimeException("Shipping method not found"));
        return shippingMethodMapper.toDto(shippingMethod);
    }
}
