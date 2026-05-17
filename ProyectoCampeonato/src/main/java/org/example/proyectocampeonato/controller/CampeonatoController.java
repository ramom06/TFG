package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Campeonato;
import org.example.proyectocampeonato.service.CampeonatoService;
import org.example.proyectocampeonato.service.SorteoService;
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
    private final SorteoService sorteoService;

    public CampeonatoController(CampeonatoService service, SorteoService sorteoService) {
        this.service = service;
        this.sorteoService = sorteoService;
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

    // POST /api/campeonatos/{id}/cerrar-inscripciones
    // Cierra inscripciones y genera el sorteo de primera ronda.
    // Solo permitido desde 3 días antes del inicio del campeonato.
    @PostMapping("/{id}/cerrar-inscripciones")
    public ResponseEntity<Campeonato> cerrarInscripciones(@PathVariable Long id) {
        log.info("Cerrando inscripciones y sorteando primera ronda del campeonato {}", id);
        return ResponseEntity.ok(sorteoService.cerrarInscripcionesYSortear(id));
    }

    // POST /api/campeonatos/{id}/desarrollar-bracket
    // Desarrolla el bracket completo hasta el ganador.
    // Solo permitido a partir de la fecha de fin del campeonato.
    @PostMapping("/{id}/desarrollar-bracket")
    public ResponseEntity<Campeonato> desarrollarBracket(@PathVariable Long id) {
        log.info("Desarrollando bracket completo del campeonato {}", id);
        return ResponseEntity.ok(sorteoService.desarrollarBracket(id));
    }

    // POST /api/campeonatos/{id}/forzar-primera-ronda
    // Versión sin validación de fechas (para el admin). Genera solo la primera ronda.
    @PostMapping("/{id}/forzar-primera-ronda")
    public ResponseEntity<Campeonato> forzarPrimeraRonda(@PathVariable Long id) {
        log.info("Forzando sorteo de primera ronda del campeonato {}", id);
        return ResponseEntity.ok(sorteoService.forzarPrimeraRonda(id));
    }

    // POST /api/campeonatos/{id}/forzar-sorteo-completo
    // Versión sin validación de fechas (para el admin). Genera todo: primera ronda + bracket.
    @PostMapping("/{id}/forzar-sorteo-completo")
    public ResponseEntity<Campeonato> forzarSorteoCompleto(@PathVariable Long id) {
        log.info("Forzando sorteo completo del campeonato {}", id);
        return ResponseEntity.ok(sorteoService.forzarSorteoYDesarrollo(id));
    }
}