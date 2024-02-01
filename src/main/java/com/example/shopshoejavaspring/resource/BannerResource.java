package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.banner.BannerDTO;
import com.example.shopshoejavaspring.dto.banner.CreateBannerDTO;
import com.example.shopshoejavaspring.dto.banner.FilterBannerDTO;
import com.example.shopshoejavaspring.dto.banner.UpdateBannerDTO;
import com.example.shopshoejavaspring.entity.Banner;
import com.example.shopshoejavaspring.service.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/banners")
@RestController
@Slf4j
@RequiredArgsConstructor
public class BannerResource {

    private final BannerService bannerService;

    // create banner
    @PostMapping("/create")
    private ResponseEntity<CreateBannerDTO> createBanner(@RequestParam(value = "file", required = false) MultipartFile file, @Valid @RequestPart("data") CreateBannerDTO createBannerDTO) {
        log.debug("BEGIN - /api/banners/create");
        Banner banner = bannerService.create(createBannerDTO, file);
        createBannerDTO.setId(banner.getId());
        log.debug("END - /api/banners/create");
        return ResponseEntity.status(HttpStatus.OK).body(createBannerDTO);
    }

    // update banner
    @PostMapping("/update")
    private ResponseEntity<UpdateBannerDTO> updateBanner(@RequestParam(value = "file", required = false) MultipartFile file, @Valid @RequestPart("data") UpdateBannerDTO createBannerDTO) {
        log.debug("BEGIN - /api/banners/update");
        Banner banner = bannerService.update(createBannerDTO, file);
        createBannerDTO.setId(banner.getId());
        log.debug("END - /api/banners/update");
        return ResponseEntity.status(HttpStatus.OK).body(createBannerDTO);
    }

    @GetMapping("/get-all")
    private ResponseEntity<List<BannerDTO>> getAllBanner(Pageable pageable) {
        log.debug("BEGIN - /api/banners/getAll");
        Page<BannerDTO> bannerDTO = bannerService.getAll(pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(bannerDTO.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(bannerDTO.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(bannerDTO.getNumber()));
        headers.add("X-Page-Size", String.valueOf(bannerDTO.getSize()));
        log.debug("END - /api/banners/getAll");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bannerDTO.getContent());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<BannerDTO> getById(@PathVariable Long id) {
        log.debug("BEGIN - /api/banners/getById");
        BannerDTO bannerDTO = bannerService.getById(id);
        log.debug("END - /api/banners/getById");
        return ResponseEntity.status(HttpStatus.OK).body(bannerDTO);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.debug("BEGIN - /api/banners/delete");
        bannerService.delete(id);
        log.debug("END - /api/banners/delete");
        return ResponseEntity.status(HttpStatus.OK).body("Delete Banner success");
    }

}
