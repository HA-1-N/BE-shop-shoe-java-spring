package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.paymentType.PaymentTypeDTO;
import com.example.shopshoejavaspring.entity.PaymentType;
import com.example.shopshoejavaspring.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment-type")
@RequiredArgsConstructor
@Slf4j
public class PaymentTypeResource {

    private final PaymentTypeService paymentTypeService;

    @PostMapping("/create")
    public ResponseEntity<PaymentTypeDTO> createPaymentType(@RequestBody PaymentTypeDTO paymentTypeDTO) {
        log.debug("Request to create payment type: {}", paymentTypeDTO);
        PaymentType paymentType = paymentTypeService.createPaymentType(paymentTypeDTO);
        paymentTypeDTO.setId(paymentType.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentTypeDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<PaymentTypeDTO>> filterPaymentType(@RequestBody PaymentTypeDTO paymentTypeDTO, Pageable pageable) {
        log.debug("Request to filter payment type: {}", paymentTypeDTO);
        Page<PaymentTypeDTO> paymentTypeDTOS = paymentTypeService.filterPaymentType(paymentTypeDTO, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(paymentTypeDTOS.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(paymentTypeDTOS.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(paymentTypeDTOS.getNumber()));
        headers.add("X-Page-Size", String.valueOf(paymentTypeDTOS.getSize()));

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(paymentTypeDTOS.getContent());
    }

}
