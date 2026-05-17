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

    // Sortea solo la primera ronda del campeonato.
    @PostMapping("/{idCampeonato}/sortear-primera-ronda")
    public ResponseEntity<Campeonato> sortearPrimeraRonda(@PathVariable Long idCampeonato) {
        log.info("Sorteo de primera ronda del campeonato {}", idCampeonato);
        return ResponseEntity.ok(sorteoService.sortearPrimeraRonda(idCampeonato));
    }

    // Sortea el campeonato completo: primera ronda + desarrollo hasta el ganador.
    @PostMapping("/{idCampeonato}/sortear-completo")
    public ResponseEntity<Campeonato> sortearCompleto(@PathVariable Long idCampeonato) {
        log.info("Sorteo completo del campeonato {}", idCampeonato);
        return ResponseEntity.ok(sorteoService.sortearCompleto(idCampeonato));
    }
}
