package com.bush.pharmacy_web_app.controllers.rest.admin;

import com.bush.pharmacy_web_app.service.MedicineImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/product-image")
@RequiredArgsConstructor
public class ProductImageRestController {
    private final MedicineImageService imageService;

    @GetMapping(value = "/{id}/{filename:.+}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<Resource> findImage(@PathVariable Long id, @PathVariable String filename) {
        return ResponseEntity.ok(imageService.findProductImageByIdAndName(id, filename)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteImage(@PathVariable Long id) {
        if (!imageService.deleteImage(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
