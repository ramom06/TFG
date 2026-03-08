package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.dto.ArbitroDTO;
import org.example.proyectocampeonato.service.ArbitroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/arbitros")
public class ArbitroController {

    private final ArbitroService service;

    public ArbitroController(ArbitroService service) {
        this.service = service;
    }

    // GET /api/arbitros
    @GetMapping
    public ResponseEntity<List<ArbitroDTO>> all() {
        log.info("Obteniendo todos los árbitros");
        return ResponseEntity.ok(service.getAll());
    }

    // GET /api/arbitros/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ArbitroDTO> one(@PathVariable Long id) {
        log.info("Obteniendo árbitro con id: {}", id);
        return ResponseEntity.ok(service.one(id));
    }

    // GET /api/arbitros/licencia/{licencia}
    @GetMapping("/licencia/{licencia}")
    public ResponseEntity<ArbitroDTO> byLicencia(@PathVariable String licencia) {
        log.info("Obteniendo árbitro con licencia: {}", licencia);
        return ResponseEntity.ok(service.findByLicencia(licencia));
    }

    // GET /api/arbitros/categoria/{categoriaArbitral}
    @GetMapping("/categoria/{categoriaArbitral}")
    public ResponseEntity<List<ArbitroDTO>> byCategoria(@PathVariable String categoriaArbitral) {
        log.info("Obteniendo árbitros con categoría arbitral: {}", categoriaArbitral);
        return ResponseEntity.ok(service.findByCategoria(categoriaArbitral));
    }

    // POST /api/arbitros
    @PostMapping
    public ResponseEntity<ArbitroDTO> save(@RequestBody ArbitroDTO dto) {
        log.info("Creando árbitro: {} {}", dto.getNombre(), dto.getApellidos());
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    // PUT /api/arbitros/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ArbitroDTO> replace(@PathVariable Long id, @RequestBody ArbitroDTO dto) {
        log.info("Actualizando árbitro con id: {}", id);
        return ResponseEntity.ok(service.replace(id, dto));
    }

    // DELETE /api/arbitros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando árbitro con id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}