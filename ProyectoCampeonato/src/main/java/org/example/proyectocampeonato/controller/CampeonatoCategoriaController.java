package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.dto.CategoriaDTO;
import org.example.proyectocampeonato.service.CampeonatoCategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categoriasCampeonato")
public class CampeonatoCategoriaController {

    private final CampeonatoCategoriaService service;

    public CampeonatoCategoriaController(CampeonatoCategoriaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public List<CategoriaDTO> getCategoriasByCampeonato(@PathVariable Long id) {
        log.info("Obteniendo categorías del campeonato {}", id);
        return service.getCategoriasPorCampeonato(id);
    }
}
