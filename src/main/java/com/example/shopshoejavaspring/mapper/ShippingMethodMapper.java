package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.shippingMethod.ShippingMethodDTO;
import com.example.shopshoejavaspring.entity.ShippingMethod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingMethodMapper extends EntityMapper<ShippingMethodDTO, ShippingMethod> {
     // update shipping method entity

}
