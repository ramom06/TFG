package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Competidor;
import org.example.proyectocampeonato.service.CompetidorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/competidores")
@CrossOrigin(origins = "https://www.campeonatolive.online")
public class CompetidorController {

    private final CompetidorService service;

    public CompetidorController(CompetidorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Competidor>> getAll() {return ResponseEntity.ok(service.getAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Competidor> one(@PathVariable Long id) {return ResponseEntity.ok(service.one(id));}

    @PostMapping
    public ResponseEntity<Competidor> save(@RequestBody Competidor competidor) {return new ResponseEntity<>(service.save(competidor), HttpStatus.CREATED);}

    @PutMapping("/{id}")
    public ResponseEntity<Competidor> replace(@PathVariable Long id, @RequestBody Competidor competidor) {return ResponseEntity.ok(service.replace(id, competidor));}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}