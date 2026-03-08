package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.dto.CompetidorDTO;
import org.example.proyectocampeonato.service.CompetidorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/competidores")
public class CompetidorController {

    private final CompetidorService service;

    public CompetidorController(CompetidorService service) {
        this.service = service;
    }

    // GET /api/competidores
    @GetMapping
    public ResponseEntity<List<CompetidorDTO>> getAll() {
        log.info("Obteniendo todos los competidores");
        return ResponseEntity.ok(service.getAll());
    }

    // GET /api/competidores/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CompetidorDTO> one(@PathVariable Long id) {
        log.info("Obteniendo competidor con id: {}", id);
        return ResponseEntity.ok(service.one(id));
    }

    // POST /api/competidores
    @PostMapping
    public ResponseEntity<CompetidorDTO> save(@RequestBody CompetidorDTO dto) {
        log.info("Creando competidor: {} {}", dto.getNombre(), dto.getApellidos());
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    // PUT /api/competidores/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CompetidorDTO> replace(@PathVariable Long id, @RequestBody CompetidorDTO dto) {
        log.info("Actualizando competidor con id: {}", id);
        return ResponseEntity.ok(service.replace(id, dto));
    }

    // DELETE /api/competidores/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando competidor con id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}