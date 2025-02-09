package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.user.UserAddressDTO;
import com.example.shopshoejavaspring.entity.UserAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAddressMapper extends EntityMapper<UserAddressDTO, UserAddress>{

}
