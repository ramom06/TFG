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
public class CampeonatoController {

    private final CampeonatoService service;

    private CampeonatoController(CampeonatoService service){this.service=service;}

    @GetMapping
    public ResponseEntity<List<Campeonato>> all() {
        log.info("Accediendo a todos los campeonatos");
        List<Campeonato> lista = this.service.getAll();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<Campeonato> newPelicula(@RequestBody Campeonato campeonato) {
        log.info("Creando nuevo campeonato: {}", campeonato.getNombre());
        Campeonato campeonatoGuardado = this.service.save(campeonato);

        return new ResponseEntity<>(campeonatoGuardado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campeonato> one(@PathVariable("id") Integer id) {
        // El servicio debería lanzar una excepción si no existe, capturada por un GlobalExceptionHandler
        Campeonato campeonato = this.service.one(id);
        return ResponseEntity.ok(campeonato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campeonato> replacePelicula(@PathVariable("id") Integer id, @RequestBody Campeonato campeonato) {
        log.info("Actualizando película con ID: {}", id);
        Campeonato actualizada = this.service.replace(id, campeonato);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Campeonato> deletePelicula(@PathVariable("id") Integer id) {
        log.info("Eliminando campeonato con ID: {}", id);
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

