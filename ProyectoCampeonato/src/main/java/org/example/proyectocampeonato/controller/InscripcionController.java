package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Inscripcion;
import org.example.proyectocampeonato.repository.InscripcionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionRepository inscripcionRepository;

    public InscripcionController(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    // Todas las inscripciones de un competidor concreto
    @GetMapping("/competidor/{id}")
    public ResponseEntity<List<Inscripcion>> getByCompetidor(@PathVariable("id") Long id) {
        log.info("Obteniendo inscripciones del competidor con ID: {}", id);
        List<Inscripcion> inscripciones = inscripcionRepository.findByCompetidor(id);
        return ResponseEntity.ok(inscripciones);
    }

    // Todas las inscripciones de una categoría concreta dentro de un campeonato concreto
    @GetMapping("/campeonato/{idCampeonato}/categoria/{idCategoria}")
    public ResponseEntity<List<Inscripcion>> getByCampeonatoAndCategoria(
            @PathVariable("idCampeonato") Integer idCampeonato,
            @PathVariable("idCategoria") Long idCategoria) {
        log.info("Obteniendo inscripciones del campeonato {} en la categoría {}", idCampeonato, idCategoria);
        List<Inscripcion> inscripciones = inscripcionRepository.findByCampeonatoAndCategoria(idCampeonato, idCategoria);
        return ResponseEntity.ok(inscripciones);
    }
}