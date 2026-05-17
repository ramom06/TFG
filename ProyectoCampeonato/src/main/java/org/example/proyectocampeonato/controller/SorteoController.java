package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Campeonato;
import org.example.proyectocampeonato.service.SorteoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/sorteos")
@CrossOrigin(origins = "https://www.campeonatolive.online")
public class SorteoController {

    private final SorteoService sorteoService;

    public SorteoController(SorteoService sorteoService) {
        this.sorteoService = sorteoService;
    }

    // POST /api/sorteos/{idCampeonato}/cerrar-inscripciones
    // Cierra inscripciones y genera el sorteo de primera ronda.
    // Solo permitido desde 3 días antes del inicio del campeonato.
    @PostMapping("/{idCampeonato}/cerrar-inscripciones")
    public ResponseEntity<Campeonato> cerrarInscripciones(@PathVariable Long idCampeonato) {
        log.info("Cerrando inscripciones y sorteando primera ronda del campeonato {}", idCampeonato);
        return ResponseEntity.ok(sorteoService.cerrarInscripcionesYSortear(idCampeonato));
    }

    // POST /api/sorteos/{idCampeonato}/desarrollar
    // Desarrolla el sorteo completo hasta el ganador.
    // Solo permitido a partir de la fecha de fin del campeonato.
    @PostMapping("/{idCampeonato}/desarrollar")
    public ResponseEntity<Campeonato> desarrollarSorteo(@PathVariable Long idCampeonato) {
        log.info("Desarrollando sorteo completo del campeonato {}", idCampeonato);
        return ResponseEntity.ok(sorteoService.desarrollarSorteo(idCampeonato));
    }

    // POST /api/sorteos/{idCampeonato}/forzar-primera-ronda
    // Versión sin validación de fechas (para el admin). Genera solo la primera ronda.
    @PostMapping("/{idCampeonato}/forzar-primera-ronda")
    public ResponseEntity<Campeonato> forzarPrimeraRonda(@PathVariable Long idCampeonato) {
        log.info("Forzando sorteo de primera ronda del campeonato {}", idCampeonato);
        return ResponseEntity.ok(sorteoService.forzarPrimeraRonda(idCampeonato));
    }

    // POST /api/sorteos/{idCampeonato}/forzar-completo
    // Versión sin validación de fechas (para el admin). Genera todo: primera ronda + desarrollo.
    @PostMapping("/{idCampeonato}/forzar-completo")
    public ResponseEntity<Campeonato> forzarSorteoCompleto(@PathVariable Long idCampeonato) {
        log.info("Forzando sorteo completo del campeonato {}", idCampeonato);
        return ResponseEntity.ok(sorteoService.forzarSorteoYDesarrollo(idCampeonato));
    }
}
