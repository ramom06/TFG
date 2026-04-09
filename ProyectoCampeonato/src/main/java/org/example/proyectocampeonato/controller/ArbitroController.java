package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Arbitro;
import org.example.proyectocampeonato.service.ArbitroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/arbitros")
@CrossOrigin(origins = "https://www.campeonatolive.online")
public class ArbitroController {

    private final ArbitroService service;

    public ArbitroController(ArbitroService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Arbitro>> all() {
        log.info("Obteniendo todos los árbitros");
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arbitro> one(@PathVariable Long id) {
        log.info("Obteniendo árbitro con id: {}", id);
        return ResponseEntity.ok(service.one(id));
    }

    @GetMapping("/licencia/{licencia}")
    public ResponseEntity<Arbitro> byLicencia(@PathVariable String licencia) {
        log.info("Obteniendo árbitro con licencia: {}", licencia);
        return ResponseEntity.ok(service.findByLicencia(licencia));
    }

    @GetMapping("/categoria/{categoriaArbitral}")
    public ResponseEntity<List<Arbitro>> byCategoria(@PathVariable String categoriaArbitral) {
        log.info("Obteniendo árbitros con categoría arbitral: {}", categoriaArbitral);
        return ResponseEntity.ok(service.findByCategoria(categoriaArbitral));
    }

    @PostMapping
    public ResponseEntity<Arbitro> save(@RequestBody Arbitro arbitro) {
        log.info("Creando árbitro: {} {}", arbitro.getNombre(), arbitro.getApellidos());
        return new ResponseEntity<>(service.save(arbitro), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Arbitro> replace(@PathVariable Long id, @RequestBody Arbitro arbitro) {
        log.info("Actualizando árbitro con id: {}", id);
        return ResponseEntity.ok(service.replace(id, arbitro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando árbitro con id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}