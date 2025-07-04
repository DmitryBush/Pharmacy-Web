package com.bush.pharmacy_web_app.controllers.rest.icon;

import com.bush.pharmacy_web_app.service.IconService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/icons")
@RequiredArgsConstructor
public class IconRestController {
    private final IconService iconService;

    @GetMapping(value = "/{folder}/{name}")
    public Resource findIconByName(@PathVariable String folder, @PathVariable String name) {
        return iconService.findIconByName(name, folder);
    }
}
