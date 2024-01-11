package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.color.ColorDTO;
import com.example.shopshoejavaspring.entity.Color;
import com.example.shopshoejavaspring.service.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/color")
@Slf4j
@RequiredArgsConstructor
public class ColorResource {

    private final ColorService colorService;

    @PostMapping("/create")
    public ResponseEntity<ColorDTO> createSize(@Valid @RequestBody ColorDTO colorDTO) {
        log.debug("REST request to save Color : {}", colorDTO);
        Color color = colorService.createColor(colorDTO);
        colorDTO.setId(color.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(colorDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ColorDTO>> filterColor(@RequestBody ColorDTO colorDTO, Pageable pageable) {
        log.debug("REST request to save Color : {}", colorDTO);
        Page<ColorDTO> listColor = colorService.filterColor(colorDTO, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listColor.getContent());
    }

    @PostMapping("/update")
    public ResponseEntity<ColorDTO> updateColor(@Valid @RequestBody ColorDTO colorDTO) {
        log.debug("REST request to save Color : {}", colorDTO);
        ColorDTO color = colorService.updateColor(colorDTO);
        return ResponseEntity.status(HttpStatus.OK).body(color);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteColor(@Valid @RequestBody ColorDTO colorDTO) {
        log.debug("REST request to save Color : {}", colorDTO);
        colorService.deleteColor(colorDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Delete success");
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ColorDTO>> getAllColor() {
        log.debug("REST request to get all Color");
        List<ColorDTO> listColor = colorService.getAllColor();
        return ResponseEntity.status(HttpStatus.OK).body(listColor);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ColorDTO> getColorById(@PathVariable Long id) {
        log.debug("REST request to get Color : {}", id);
        ColorDTO color = colorService.getColorById(id);
        return ResponseEntity.status(HttpStatus.OK).body(color);
    }
}
