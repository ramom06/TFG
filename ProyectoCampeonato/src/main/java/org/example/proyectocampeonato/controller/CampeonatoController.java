package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Campeonato;
import org.example.proyectocampeonato.service.CampeonatoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/campeonatos")
@CrossOrigin(origins = "https://www.campeonatolive.online")
public class CampeonatoController {

    private final CampeonatoService service;

    public CampeonatoController(CampeonatoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Campeonato>> all() {
        log.info("Obteniendo todos los campeonatos");
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campeonato> one(@PathVariable Long id) {
        log.info("Obteniendo campeonato con id: {}", id);
        return ResponseEntity.ok(service.one(id));
    }

    @PostMapping
    public ResponseEntity<Campeonato> save(@RequestBody Campeonato campeonato) {
        log.info("Creando campeonato: {}", campeonato.getNombre());
        return new ResponseEntity<>(service.save(campeonato), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campeonato> replace(@PathVariable Long id, @RequestBody Campeonato campeonato) {
        log.info("Actualizando campeonato con id: {}", id);
        return ResponseEntity.ok(service.replace(id, campeonato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando campeonato con id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}