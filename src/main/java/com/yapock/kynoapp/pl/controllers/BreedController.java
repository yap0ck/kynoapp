package com.yapock.kynoapp.pl.controllers;

import com.yapock.kynoapp.bll.BreedService;
import com.yapock.kynoapp.dal.models.breed.BreedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/breed")
@Slf4j
public class BreedController {
    private final BreedService breedService;

    @GetMapping
    public ResponseEntity<List<BreedDTO>> listBreeds(@RequestParam(required = false) String name, @RequestParam(required = false) String group) {
        return ResponseEntity.ok(breedService.listBreeds(name, group));
    }
}
