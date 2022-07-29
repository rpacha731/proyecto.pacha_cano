package com.iua.iw3.proyecto.pacha_cano.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/")
@AllArgsConstructor
@ApiIgnore
public class HealthCheckController {

//    @GetMapping("")
//    public ResponseEntity<?> healthCheck() {
//        return ResponseEntity.ok().body("OK");
//    }

//    @GetMapping(value = "")
//    public String defaultPage() {
//        return "index.html";
//    }

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

}
