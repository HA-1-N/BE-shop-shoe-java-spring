package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.size.SizeDTO;
import com.example.shopshoejavaspring.entity.Size;
import com.example.shopshoejavaspring.service.SizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/size")
@Slf4j
@RequiredArgsConstructor
public class SizeResource {

    private final SizeService sizeService;

    @PostMapping("/create")
    public ResponseEntity<SizeDTO> createSize(@Valid @RequestBody SizeDTO sizeDTO) {
        log.debug("REST request to save Size : {}", sizeDTO);
        Size size = sizeService.createSize(sizeDTO);
        sizeDTO.setId(size.getId());
        log.debug("Size : {}", size);
        return ResponseEntity.status(HttpStatus.CREATED).body(sizeDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<SizeDTO>> filterSize(@Valid @RequestBody SizeDTO sizeDTO, Pageable pageable) {
        log.debug("REST request to save Size : {}", sizeDTO);
        Page<SizeDTO> listSize = sizeService.filterSize(sizeDTO, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(listSize.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(listSize.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(listSize.getNumber()));
        headers.add("X-Page-Size", String.valueOf(listSize.getSize()));
        log.debug("Size : {}", listSize);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(listSize.getContent());
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<SizeDTO> findById(@PathVariable Long id) {
        log.debug("REST request to find Size by id : {}", id);
        SizeDTO sizeDTO = sizeService.findById(id);
        log.debug("Size : {}", sizeDTO);
        return ResponseEntity.status(HttpStatus.OK).body(sizeDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SizeDTO> updateSize(@PathVariable Long id, @Valid @RequestBody SizeDTO sizeDTO) {
        log.debug("REST request to update Size : {}", sizeDTO);
        SizeDTO sizeDTO1 = sizeService.updateSize(id, sizeDTO);
        log.debug("Size : {}", sizeDTO1);
        return ResponseEntity.status(HttpStatus.OK).body(sizeDTO1);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<SizeDTO>> getAllSize() {
        log.debug("REST request to get all Size");
        List<SizeDTO> listSize = sizeService.getAllSize();
        log.debug("Size : {}", listSize);
        return ResponseEntity.status(HttpStatus.OK).body(listSize);
    }
}
