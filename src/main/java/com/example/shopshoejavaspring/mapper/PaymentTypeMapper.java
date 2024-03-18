package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.paymentType.PaymentTypeDTO;
import com.example.shopshoejavaspring.entity.PaymentType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentTypeMapper extends EntityMapper<PaymentTypeDTO, PaymentType> {
}
