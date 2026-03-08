package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.dto.CategoriaDTO;
import org.example.proyectocampeonato.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    // GET /api/categorias
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> all() {
        log.info("Obteniendo todas las categorías");
        return ResponseEntity.ok(service.getAll());
    }

    // GET /api/categorias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> one(@PathVariable Long id) {
        log.info("Obteniendo categoría con id: {}", id);
        return ResponseEntity.ok(service.one(id));
    }

    // POST /api/categorias
    @PostMapping
    public ResponseEntity<CategoriaDTO> save(@RequestBody CategoriaDTO dto) {
        log.info("Creando categoría: {}", dto.getNombre());
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    // PUT /api/categorias/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> replace(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        log.info("Actualizando categoría con id: {}", id);
        return ResponseEntity.ok(service.replace(id, dto));
    }

    // DELETE /api/categorias/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando categoría con id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}