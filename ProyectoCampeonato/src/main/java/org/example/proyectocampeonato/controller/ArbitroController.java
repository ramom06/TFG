package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Arbitro;
import org.example.proyectocampeonato.repository.ArbitroRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/arbitros")
public class ArbitroController {

    private final ArbitroRepository repository;
    public ArbitroController(ArbitroRepository repository) { this.repository = repository; }

    @GetMapping
    public List<Arbitro> all() {
        return repository.findAll();
    }

    @PostMapping
    public Arbitro save(@RequestBody Arbitro arbitro) {
        return repository.save(arbitro);
    }
}
