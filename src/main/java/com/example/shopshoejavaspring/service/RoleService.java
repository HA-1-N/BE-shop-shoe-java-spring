package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.role.RoleDTO;
import com.example.shopshoejavaspring.mapper.RoleMapper;
import com.example.shopshoejavaspring.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public List<RoleDTO> getAllRole() {
        return roleMapper.toRoleDTOs(roleRepository.findAll());
    }
}
