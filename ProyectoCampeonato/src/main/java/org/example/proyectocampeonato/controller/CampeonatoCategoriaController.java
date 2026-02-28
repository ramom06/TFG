package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Categoria;
import org.example.proyectocampeonato.service.CampeonatoCategoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/campeonato")
public class CampeonatoCategoriaController {
    private final CampeonatoCategoriaService service;

    public CampeonatoCategoriaController(CampeonatoCategoriaService service) {
        this.service = service;
    }

    // Ruta: GET /api/campeonato/{id}
    // Devuelve la lista de categorías que participan en ese campeonato
    @GetMapping("/{id}")
    public List<Categoria> getCategoriasByCampeonato(@PathVariable Long id) {
        return service.getCategoriasPorCampeonato(id);
    }
}
