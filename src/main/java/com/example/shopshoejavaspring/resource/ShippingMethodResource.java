package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.shippingMethod.ShippingMethodDTO;
import com.example.shopshoejavaspring.entity.ShippingMethod;
import com.example.shopshoejavaspring.service.ShippingMethodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/shipping-method")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ShippingMethodResource {

    private final ShippingMethodService shippingMethodService;

    @PostMapping("/create")
    public ResponseEntity<ShippingMethodDTO> createShippingMethod (@RequestBody ShippingMethodDTO shippingMethodDTO) {
        log.debug("Request to create shipping method: {}", shippingMethodDTO);
        ShippingMethod shippingMethod = shippingMethodService.createShippingMethod(shippingMethodDTO);
        shippingMethodDTO.setId(shippingMethod.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(shippingMethodDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<ShippingMethodDTO> updateShippingMethod (@RequestBody ShippingMethodDTO shippingMethodDTO) {
        log.debug("Request to update shipping method: {}", shippingMethodDTO);
        shippingMethodService.updateShippingMethod(shippingMethodDTO);
        return ResponseEntity.status(HttpStatus.OK).body(shippingMethodDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ShippingMethodDTO>> filterShippingMethod (@RequestBody ShippingMethodDTO shippingMethodDTO, Pageable pageable) {
        log.debug("Request to filter shipping method: {}", shippingMethodDTO);
        Page<ShippingMethodDTO> shippingMethodDTOS = shippingMethodService.filterShippingMethod(shippingMethodDTO, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(shippingMethodDTOS.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(shippingMethodDTOS.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(shippingMethodDTOS.getNumber()));
        headers.add("X-Page-Size", String.valueOf(shippingMethodDTOS.getSize()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(shippingMethodDTOS.getContent());
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ShippingMethodDTO> findShippingMethodById (@PathVariable Long id) {
        log.debug("Request to find shipping method by id: {}", id);
        ShippingMethodDTO shippingMethodDTO = shippingMethodService.findShippingMethodById(id);
        return ResponseEntity.status(HttpStatus.OK).body(shippingMethodDTO);
    }
}
