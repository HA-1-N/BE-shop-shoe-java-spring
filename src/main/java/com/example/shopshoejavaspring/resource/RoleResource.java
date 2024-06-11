package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.role.RoleDTO;
import com.example.shopshoejavaspring.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
@Slf4j
public class RoleResource {

    private final RoleService roleService;

    @GetMapping("/get-all")
    public ResponseEntity<List<RoleDTO>> getAllRole() {
        log.debug("BEGIN - /api/role/get-all");
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAllRole());
    }

//    @GetMapping("/get-by-id/{id}")
//    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") Long id) {
//        log.debug("BEGIN - /api/role/get-by-id/{}", id);
//        RoleDTO roleDTO = roleService.getRoleById(id);
//        log.debug("END - /api/role/get-by-id/{}", id);
//        return ResponseEntity.status(HttpStatus.OK).body(roleDTO);
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
//        log.debug("BEGIN - /api/role/create");
//        RoleDTO role = roleService.createRole(roleDTO);
//        log.debug("END - /api/role/create");
//        return ResponseEntity.status(HttpStatus.OK).body(role);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<RoleDTO> updateRole(@PathVariable("id") Long id, @Valid @RequestBody RoleDTO roleDTO) {
//        log.debug("BEGIN - /api/role/update/{}", id);
//        RoleDTO role = roleService.updateRole(id, roleDTO);
//        log.debug("END - /api/role/update/{}", id);
//        return ResponseEntity.status(HttpStatus.OK).body(role);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteRole(@PathVariable("id") Long id) {
//        log.debug("BEGIN - /api/role/delete/{}", id);
//        roleService.deleteRole(id);
//        log.debug("END - /api/role/delete/{}", id);
//        return ResponseEntity.status(HttpStatus.OK).body("Delete role success");
//    }
//
//    @PutMapping("/update-role/{id}")
//    public ResponseEntity<UserDTO> updateRole(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateRoleDTO userUpdateRoleDTO) {
//        log.debug("BEGIN - /api/role/update-role/{}", id);
//        UserDTO user = roleService.updateRole(id, userUpdateRoleDTO);
//        log.debug("END - /api/role/update-role/{}", id);
//        return ResponseEntity.status(HttpStatus.OK).body(user);
//    }

}
