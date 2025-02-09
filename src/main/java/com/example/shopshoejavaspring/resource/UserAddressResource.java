package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.user.UserAddressDTO;
import com.example.shopshoejavaspring.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-address")
@Slf4j
@RequiredArgsConstructor
public class UserAddressResource {

    private final UserAddressService userAddressService;

    // get user address by user id
    @GetMapping("")
    public ResponseEntity<List<UserAddressDTO>> getUserAddressByUserId(@RequestParam(value = "userId") Long userId) {
        log.info("REST request to get user address by user id : {}", userId);
        List<UserAddressDTO> userAddresses = userAddressService.getUserAddressByUserId(userId);
        return ResponseEntity.ok().body(userAddresses);
    }
}
