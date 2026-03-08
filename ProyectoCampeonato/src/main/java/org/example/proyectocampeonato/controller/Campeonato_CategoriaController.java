package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.dto.CategoriaDTO;
import org.example.proyectocampeonato.service.CampeonatoCategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/campeonatos/{idCampeonato}/categorias")
public class Campeonato_CategoriaController {

    private final CampeonatoCategoriaService service;

    public Campeonato_CategoriaController(CampeonatoCategoriaService service) {
        this.service = service;
    }

    // GET /api/campeonatos/{idCampeonato}/categorias
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getCategorias(@PathVariable Long idCampeonato) {
        log.info("Obteniendo categorías del campeonato con id: {}", idCampeonato);
        return ResponseEntity.ok(service.getCategoriasPorCampeonato(idCampeonato));
    }

    // POST /api/campeonatos/{idCampeonato}/categorias/{idCategoria}
    @PostMapping("/{idCategoria}")
    public ResponseEntity<Void> asignar(
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria) {
        log.info("Asignando categoría {} al campeonato {}", idCategoria, idCampeonato);
        service.asignarCategoria(idCampeonato, idCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // DELETE /api/campeonatos/{idCampeonato}/categorias/{idCategoria}
    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria) {
        log.info("Eliminando categoría {} del campeonato {}", idCategoria, idCampeonato);
        service.eliminarCategoria(idCampeonato, idCategoria);
        return ResponseEntity.noContent().build();
    }
}