package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.dto.CampeonatoDTO;
import org.example.proyectocampeonato.service.CampeonatoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/campeonatos")
public class CampeonatoController {

    private final CampeonatoService service;

    public CampeonatoController(CampeonatoService service) {
        this.service = service;
    }

    // GET /api/campeonatos
    @GetMapping
    public ResponseEntity<List<CampeonatoDTO>> all() {
        log.info("Obteniendo todos los campeonatos");
        return ResponseEntity.ok(service.getAll());
    }

    // GET /api/campeonatos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CampeonatoDTO> one(@PathVariable Long id) {
        log.info("Obteniendo campeonato con id: {}", id);
        return ResponseEntity.ok(service.one(id));
    }

    // POST /api/campeonatos
    @PostMapping
    public ResponseEntity<CampeonatoDTO> save(@RequestBody CampeonatoDTO dto) {
        log.info("Creando campeonato: {}", dto.getNombre());
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    // PUT /api/campeonatos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CampeonatoDTO> replace(@PathVariable Long id, @RequestBody CampeonatoDTO dto) {
        log.info("Actualizando campeonato con id: {}", id);
        return ResponseEntity.ok(service.replace(id, dto));
    }

    // DELETE /api/campeonatos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando campeonato con id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}