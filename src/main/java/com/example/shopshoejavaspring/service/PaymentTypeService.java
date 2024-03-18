package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.paymentType.PaymentTypeDTO;
import com.example.shopshoejavaspring.entity.PaymentType;
import com.example.shopshoejavaspring.mapper.PaymentTypeMapper;
import com.example.shopshoejavaspring.repository.PaymentTypeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional // This annotation is used to handle transactions in the database.
@Service
@RequiredArgsConstructor
public class PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;

    private final PaymentTypeMapper paymentTypeMapper;

    public PaymentType createPaymentType(PaymentTypeDTO paymentTypeDTO) {
        PaymentType paymentType = paymentTypeMapper.toEntity(paymentTypeDTO);
        paymentTypeRepository.save(paymentType);
        return paymentType;
    }

    public Page<PaymentTypeDTO> filterPaymentType(PaymentTypeDTO paymentTypeDTO, Pageable pageable) {
        Page<PaymentType> paymentType = paymentTypeRepository.findPaymentTypeByTypeContaining(paymentTypeDTO.getType(), pageable);
        List<PaymentTypeDTO> paymentTypeDTOS = paymentTypeMapper.toDto(paymentType.getContent());
        return new PageImpl<>(paymentTypeDTOS, pageable, paymentType.getTotalElements());
    }
}
