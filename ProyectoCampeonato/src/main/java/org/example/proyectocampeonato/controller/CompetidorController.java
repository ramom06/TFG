package org.example.proyectocampeonato.controller;

import org.example.proyectocampeonato.modelo.Competidor;
import org.example.proyectocampeonato.service.CompetidorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/competidores")
public class CompetidorController {

    private final CompetidorService service;

    public CompetidorController(CompetidorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Competidor> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Competidor one(@PathVariable Long id) { return service.one(id); }

    @PostMapping
    public Competidor save(@RequestBody Competidor competidor) { return service.save(competidor); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}