package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.user.ListUserDTO;
import com.example.shopshoejavaspring.dto.user.UserDTO;
import com.example.shopshoejavaspring.entity.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User>{
    List<ListUserDTO> toDtoUser(List<User> users);

    ListUserDTO toDtoOptionalUser(User user);
}
